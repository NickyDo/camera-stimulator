package code.camera_server;

import code.camera_server.service.SwitchPower;
import code.camera_server.service.SetFlash;
import code.digital_camera.Constants;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

import java.io.IOException;

public class CameraServer implements Runnable {

    public static void main(String[] args) throws Exception {
        // Start a user thread that runs the UPnP stack
        Thread serverThread = new Thread(new CameraServer());
        serverThread.setDaemon(false);
        serverThread.start();
    }

    public void run() {
        try {

            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(createDevice());

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    LocalDevice createDevice() throws ValidationException, LocalServiceBindingException, IOException {

        DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("Demo Camera"));

        DeviceType type = new UDADeviceType("Camera", 1);

        DeviceDetails details = new DeviceDetails("Simple Camera",
                new ManufacturerDetails("1918"),
                new ModelDetails(Constants.MODEL_DETAILS, "A simple camera with on/off switch.", "v1"));

        Icon icon = new Icon("image/jpg", 48, 48, 8, getClass().getResource("/resources/camera.png"));

        LocalService<SwitchPower> switchPowerService = new AnnotationLocalServiceBinder().read(SwitchPower.class);
        switchPowerService.setManager(new DefaultServiceManager(switchPowerService, SwitchPower.class));

        LocalService<SetFlash> setFlashService = new AnnotationLocalServiceBinder().read(SetFlash.class);
        setFlashService.setManager(new DefaultServiceManager(setFlashService, SetFlash.class));

//        return new LocalDevice(identity, type, details, icon, setFlashService);

        return new LocalDevice(identity, type, details, icon, new LocalService[]{switchPowerService, setFlashService});

    }

}