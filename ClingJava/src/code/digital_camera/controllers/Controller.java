package code.digital_camera.controllers;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.UDADeviceTypeHeader;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import code.digital_camera.Constants;
import code.digital_camera.models.AudioMode;
import code.digital_camera.models.services.ImageSetting;
import code.digital_camera.models.services.PlayMusic;
import code.digital_camera.models.services.SwitchPower;
import code.digital_camera.views.ViewInterface;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Controller implements ControllerInterface {

    private ViewInterface view;
    private Device device;
    private UpnpService upnpService;
    private ActionExecutor actionExecutor;
    private ScheduledFuture scheduledFuture;
    private RegistryListener registryListener = new DefaultRegistryListener() {

        @Override
        public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice) {
            System.out.println("Remote device detected.");
            if (remoteDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                System.out.println("Audio system detected.");
                device = remoteDevice;
                upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, Constants.SWITCH_POWER)));
                upnpService.getControlPoint().execute(createAudioControlSubscriptionCallBack(getServiceById(device, Constants.IMAGE_SETTING)));
                upnpService.getControlPoint().execute(createPlayMusicSubscriptionCallBack(getServiceById(device, Constants.PLAY_MUSIC)));
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
        public void localDeviceAdded(Registry registry, LocalDevice localDevice) {
            System.out.println("Local device detected.");
            if (localDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                System.out.println("Audio system detected.");
                device = localDevice;
                upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, Constants.SWITCH_POWER)));
                upnpService.getControlPoint().execute(createAudioControlSubscriptionCallBack(getServiceById(device, Constants.IMAGE_SETTING)));
                upnpService.getControlPoint().execute(createPlayMusicSubscriptionCallBack(getServiceById(device, Constants.PLAY_MUSIC)));                Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                    @Override
                    public void run() {
                        setPowerStatus(Constants.POWER_STATUS_DEFAULT);
                        setVolume(Constants.VOLUME_DEFAULT);
                        setPlayStatus(Constants.PLAY_STATUS_DEFAULT);
                        setMode(AudioMode.NORMAL);
                    }
                }, 500, TimeUnit.MILLISECONDS);
            }
        }

        @Override
        public void localDeviceRemoved(Registry registry, LocalDevice localDevice) {
            if (localDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                System.out.println("Audio system removed.");
                device = null;
            }
        }
    };

    public Controller(ViewInterface view) {
        this.view = view;
        init();
    }

    public void init() {
        actionExecutor = new ActionExecutor(this);
        upnpService = new UpnpServiceImpl();
        upnpService.getRegistry().addListener(registryListener);
        try {
            upnpService.getRegistry().addDevice(createDevice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        UDADeviceTypeHeader header = new UDADeviceTypeHeader(new UDADeviceType(Constants.DEVICE_NAME));
        upnpService.getControlPoint().search(header);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                upnpService.shutdown();
            }
        });
    }

    public LocalDevice createDevice() throws ValidationException, LocalServiceBindingException, IOException {

        DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier(Constants.DEVICE_NAME));

        DeviceType type = new UDADeviceType(Constants.DEVICE_NAME, 1);

        DeviceDetails details = new DeviceDetails(Constants.DEVICE_NAME,
                new ManufacturerDetails(Constants.MANUFACTURER_DETAILS),
                new ModelDetails(Constants.MODEL_DETAILS, Constants.MODEL_DESCRIPTION, Constants.MODEL_NUMBER));

        Icon icon = new Icon("image/png", 48, 48, 8, getClass().getResource(Constants.CAMERA_SYSTEM_IMAGE));

        LocalService<SwitchPower> switchPowerService = new AnnotationLocalServiceBinder().read(SwitchPower.class);
        switchPowerService.setManager(new DefaultServiceManager(switchPowerService, SwitchPower.class));
        LocalService<ImageSetting> imageSettingService = new AnnotationLocalServiceBinder().read(ImageSetting.class);
        imageSettingService.setManager(new DefaultServiceManager(imageSettingService, ImageSetting.class));
        LocalService<PlayMusic> playMusicService = new AnnotationLocalServiceBinder().read(PlayMusic.class);
        playMusicService.setManager(new DefaultServiceManager(playMusicService, PlayMusic.class));

        return new LocalDevice(
                identity, type, details, icon,
                new LocalService[]{
                        switchPowerService,
                        imageSettingService,
                        playMusicService
                }
        );
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
                    view.onPowerStatusChange(value);
//                    if (!value) {
//                        view.onPlayStatusChange(false);
//                    }
                    view.onPlayStatusChange(value);
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
//                setLight(Constants.VOLUME_DEFAULT);
//                setMode(AudioMode.NORMAL);
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
                if (values.containsKey(Constants.LIGHT)) {
                    int value = (int) values.get(Constants.LIGHT).getValue();
                    view.onVolumeChange(value);
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.BASS_LEVEL)) {
                    int value = (int) values.get(Constants.BASS_LEVEL).getValue();
                    view.onBassLevelChange(value);
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.TREBLE_LEVEL)) {
                    int value = (int) values.get(Constants.TREBLE_LEVEL).getValue();
                    view.onTrebleLevelChange(value);
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.AUDIO_MODE)) {
                    String value = (String) values.get(Constants.AUDIO_MODE).getValue();
                    view.onModeChange(AudioMode.valueOf(value));
                    System.out.println("New value: " + value);
                }
            }

            @Override
            public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
                System.out.println("Missed events: " + numberOfMissedEvents);
            }
        };
    }

    private SubscriptionCallback createPlayMusicSubscriptionCallBack(Service service) {
        return new SubscriptionCallback(service, Integer.MAX_VALUE) {
            @Override
            protected void failed(GENASubscription genaSubscription, UpnpResponse upnpResponse, Exception e, String s) {

            }

            @Override
            protected void established(GENASubscription genaSubscription) {
                System.out.println("Play music subscription created.");
//                setPlayStatus(Constants.PLAY_STATUS_DEFAULT);
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
                if (values.containsKey(Constants.PLAY_STATUS)) {
                    boolean value = (boolean) values.get(Constants.PLAY_STATUS).getValue();
                    view.onPlayStatusChange(value);
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.TIMER_STATUS)) {
                    boolean value = (boolean) values.get(Constants.TIMER_STATUS).getValue();
                    view.onTimerStatusChange(value);
                    if (value) {
                        scheduleStop(service);
                    } else {
                        cancelScheduleStop();
                    }
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.TRACK_NO)) {
                    int value = (int) values.get(Constants.TRACK_NO).getValue();
                    view.onTrackChange(value);
                    view.onPlayStatusChange(true);
                    System.out.println("New value: " + value);
                } else if (values.containsKey(Constants.TIMER_VALUE)) {
                    int value = (int) values.get(Constants.TIMER_VALUE).getValue();
                    view.onTimerValueChange(value);
                    System.out.println("New value: " + value);
                }
            }

            @Override
            public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
                System.out.println("Missed events: " + numberOfMissedEvents);
            }
        };
    }

    private void scheduleStop(Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_TIMER_VALUE));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        int timerValue = (int) invocation.getOutput()[0].getValue();
                        if (timerValue > 0) {
                            scheduledFuture = Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    actionExecutor.setTimerStatus(upnpService, service, false);
                                    actionExecutor.setPlayStatus(upnpService, service, false);
                                }
                            }, timerValue, TimeUnit.MINUTES);
                                                    }
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    private void cancelScheduleStop() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
    }

    @Override
    public boolean setPowerStatus(boolean status) {
        Service service = getServiceById(device, Constants.SWITCH_POWER);
        if (service != null) {
            actionExecutor.setPowerStatus(upnpService, service, status);
            if (!status) {
                Service playMusicService = getServiceById(device, Constants.PLAY_MUSIC);
                if (playMusicService != null) {
                    actionExecutor.setPlayStatus(upnpService, playMusicService, false);
                }
            }
        }
        return true;
    }

    public boolean setVolume(int value) {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.setVolume(upnpService, service, value);
        }
        return true;
    }

    @Override
    public boolean increaseLight() {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.increaseLight(upnpService, service);
        }
        return true;
    }

    @Override
    public boolean decreaseLight() {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.decreaseLight(upnpService, service);
        }
        return true;
    }

    @Override
    public boolean setTrebleLevel(int value) {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.setTrebleLevel(upnpService, service, value);
        }
        return true;
    }

    @Override
    public boolean increaseTrebleLevel() {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.increaseTrebleLevel(upnpService, service);
        }
        return true;
    }

    @Override
    public boolean decreaseTrebleLevel() {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.decreaseTrebleLevel(upnpService, service);
        }
        return true;
    }

    @Override
    public boolean setBassLevel(int value) {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.setBassLevel(upnpService, service, value);
        }
        return true;
    }

    @Override
    public boolean increaseBassLevel() {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.increaseBassLevel(upnpService, service);
        }
        return true;
    }

    @Override
    public boolean decreaseBassLevel() {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.decreaseBassLevel(upnpService, service);
        }
        return true;
    }

    @Override
    public boolean setMode(AudioMode mode) {
        Service service = getServiceById(device, Constants.IMAGE_SETTING);
        if (service != null) {
            actionExecutor.setAudioMode(upnpService, service, mode);
        }
        return true;
    }

    @Override
    public boolean setPlayStatus(boolean status) {
        Service service = getServiceById(device, Constants.SWITCH_POWER);
        if (service != null) {
            ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_TARGET));
            getTargetInvocation.getOutput(Constants.RET_TARGET_VALUE);
            upnpService.getControlPoint().execute(
                    new ActionCallback(getTargetInvocation) {
                        @Override
                        public void success(ActionInvocation invocation) {
                            assert invocation.getOutput().length == 0;
                            boolean powerStatus = (boolean) invocation.getOutput()[0].getValue();
                            if (powerStatus) {
                                Service service = getServiceById(device, Constants.PLAY_MUSIC);
                                if (service != null) {
                                    actionExecutor.setPlayStatus(upnpService, service, status);
                                }
                            }
                        }

                        @Override
                        public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                            System.err.println(defaultMsg);
                        }
                    }
            );
        }
        return true;
    }

    @Override
    public boolean nextTrack() {
        Service service = getServiceById(device, Constants.PLAY_MUSIC);
        if (service != null) {
            actionExecutor.nextTrack(upnpService, service);
            actionExecutor.setPlayStatus(upnpService, service, true);
        }
        return true;
    }

    @Override
    public boolean prevTrack() {
        Service service = getServiceById(device, Constants.PLAY_MUSIC);
        if (service != null) {
            actionExecutor.prevTrack(upnpService, service);
            actionExecutor.setPlayStatus(upnpService, service, true);
        }
        return true;
    }

    @Override
    public boolean setTimerValue(int value) {
        Service service = getServiceById(device, Constants.PLAY_MUSIC);
        if (service != null) {
            actionExecutor.setTimerValue(upnpService, service, value);
        }
        return true;
    }

    @Override
    public boolean setTimerStatus(boolean status) {
        Service service = getServiceById(device, Constants.PLAY_MUSIC);
        if (service != null) {
            actionExecutor.setTimerStatus(upnpService, service, status);
            if (status) {
                scheduleStop(service);
            } else {
                cancelScheduleStop();
            }
        }
        return true;
    }

    private Service getServiceById(Device device, String serviceId) {
        if (device == null) {
            return null;
        }
        return device.findService(new UDAServiceId(serviceId));
    }

}
