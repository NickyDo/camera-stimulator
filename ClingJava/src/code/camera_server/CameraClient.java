package code.camera_server;

import code.digital_camera.Constants;
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
                    upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, "SwitchPower")));
                    upnpService.getControlPoint().execute(createAudioControlSubscriptionCallBack(getServiceById(device, "SetFlash")));
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

    private SubscriptionCallback createSubscriptionCallBack(UpnpService upnpService, Service service) {
        return new SubscriptionCallback(service, 600) {
            @Override
            protected void failed(GENASubscription genaSubscription, UpnpResponse upnpResponse, Exception e, String s) {

            }

            @Override
            protected void established(GENASubscription genaSubscription) {

            }

            @Override
            protected void ended(GENASubscription genaSubscription, CancelReason cancelReason, UpnpResponse upnpResponse) {

            }

            @Override
            public void eventReceived(GENASubscription sub) {

                System.out.println("Event: " + sub.getCurrentSequence().getValue());

                Map<String, StateVariableValue> values = sub.getCurrentValues();
                StateVariableValue status = values.get("Status");

                System.out.println("Status is: " + status.toString());

            }

            @Override
            public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
                System.out.println("Missed events: " + numberOfMissedEvents);
            }
        };
    }

    private SubscriptionCallback createPowerSwitchSubscriptionCallBack(Service service) {
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

    private SubscriptionCallback createAudioControlSubscriptionCallBack(Service service) {
        return new SubscriptionCallback(service, Integer.MAX_VALUE) {
            @Override
            protected void failed(GENASubscription genaSubscription, UpnpResponse upnpResponse, Exception e, String s) {

            }

            @Override
            protected void established(GENASubscription genaSubscription) {
                System.out.println("Audio control subscription created.");
//                setVolume(Constants.VOLUME_DEFAULT);
//                setMode(CaptureMode.NORMAL);
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
                if (values.containsKey(Constants.CONTRAST_LEVEL)) {
                    int value = (int) values.get(Constants.CONTRAST_LEVEL).getValue();
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.CONTRAST_LEVEL)) {
                    int value = (int) values.get(Constants.CONTRAST_LEVEL).getValue();
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.ZOOM_LEVEL)) {
                    int value = (int) values.get(Constants.ZOOM_LEVEL).getValue();
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.AUDIO_MODE)) {
                    String value = (String) values.get(Constants.AUDIO_MODE).getValue();
                    System.out.println("New value: " + value);
                }
            }

            @Override
            public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
                System.out.println("Missed events: " + numberOfMissedEvents);
            }
        };
    }


    void executeAction(UpnpService upnpService, Service switchPowerService) {

        ActionInvocation getTargetInvocation = new GetTargetActionInvocation(switchPowerService);

        // Executes asynchronous in the background
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called get services!");
                        ActionInvocation setTargetInvocation = new SetTargetActionInvocation(switchPowerService, !((boolean) invocation.getOutput()[0].getValue()));
                        upnpService.getControlPoint().execute(
                                new ActionCallback(setTargetInvocation) {

                                    @Override
                                    public void success(ActionInvocation actionInvocation) {

                                        assert invocation.getOutput().length == 0;
                                        System.out.println("Successfully called set services!");
                                    }

                                    @Override
                                    public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String s) {
                                        System.err.println("Error when calling set");
                                    }
                                }
                        );
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
//        new ActionCallback.Default(getTargetInvocation, upnpService.getControlPoint()).run();

    }

    void executeGetAction(UpnpService upnpService, Service switchPowerService) {

        ActionInvocation getTargetInvocation = new GetTargetActionInvocation(switchPowerService);

        // Executes asynchronous in the background
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Current vibrating status: " + (boolean) invocation.getOutput()[0].getValue());
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );

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
