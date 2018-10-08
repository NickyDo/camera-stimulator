package code.camera_server;

import code.digital_camera.Constants;
import code.digital_camera.models.AudioMode;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.*;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CameraClient implements Runnable {

    private static final int VIBRATING_PERIOD = 10;
    private static final int INITIAL_DELAY = 100;
    private Device device;

    public static void main(String[] args) throws Exception {
        // Start a user thread that runs the UPnP stack
        Thread clientThread = new Thread(new CameraClient());
        clientThread.setDaemon(false);
        clientThread.start();

    }

    public void run() {
        try {

            UpnpService upnpService = new UpnpServiceImpl();

            // Add a listener for device registration events
            upnpService.getRegistry().addListener(createRegistryListener(upnpService));

            // Broadcast a search message for all devices
            upnpService.getControlPoint().search(new STAllHeader());

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            System.exit(1);
        }
    }

    RegistryListener createRegistryListener(final UpnpService upnpService) {
        return new DefaultRegistryListener() {
            @Override
            public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice) {
                if (remoteDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                    device = remoteDevice;
                    System.out.println("Audio system detected.");
                    upnpService.getControlPoint().execute(createSubscriptionCallBack(getServiceById(device, "SwitchPower")));
                    upnpService.getControlPoint().execute(createSubscriptionCallBack(getServiceById(device, "SetFlash")));
                }
            }

            @Override
            public void remoteDeviceRemoved(Registry registry, RemoteDevice remoteDevice) {
                if (remoteDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                    System.out.println("Audio system removed.");
                    device = null;
                }
            }

            @Override
            public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {

            }


        };
    }

    private SubscriptionCallback createSubscriptionCallBack(Service service) {
        return new SubscriptionCallback(service, Integer.MAX_VALUE) {
            @Override
            protected void failed(GENASubscription genaSubscription, UpnpResponse upnpResponse, Exception e, String s) {

            }

            @Override
            protected void established(GENASubscription genaSubscription) {
                System.out.println("Power switch subscription created.");
//                setPowerStatus(Constants.POWER_STATUS_DEFAULT);
            }

            @Override
            protected void ended(GENASubscription genaSubscription, CancelReason cancelReason, UpnpResponse upnpResponse) {

            }

            @Override
            public void eventReceived(GENASubscription sub) {
                System.out.println("Event: " + sub.getCurrentSequence().getValue());
                Map<String, StateVariableValue> values = sub.getCurrentValues();
                for (String key : values.keySet()) {
                    System.out.println(key + " changed.");
                }
                if (values.containsKey(Constants.STATUS)) {
                    boolean value = (boolean) values.get(Constants.STATUS).getValue();
                    System.out.println("New value: " + value);
                }
            }

            @Override
            public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
                System.out.println("Missed events: " + numberOfMissedEvents);
            }
        };
    }

    class SetTargetActionInvocation extends ActionInvocation {

        SetTargetActionInvocation(Service service, boolean value) {
            super(service.getAction("SetTarget"));
            try {

                // Throws InvalidValueException if the value is of wrong type
                setInput("NewTargetValue", value);

            } catch (InvalidValueException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }
    }

    class GetTargetActionInvocation extends ActionInvocation {

        GetTargetActionInvocation(Service service) {
            super(service.getAction("GetTarget"));
            try {

                // Throws InvalidValueException if the value is of wrong type
                getOutput("RetTargetValue");

            } catch (InvalidValueException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }
    }

    private Service getServiceById(Device device, String serviceId) {
        if (device == null) {
            return null;
        }
        return device.findService(new UDAServiceId(serviceId));
    }
}
