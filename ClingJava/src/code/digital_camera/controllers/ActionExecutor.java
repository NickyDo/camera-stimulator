package code.digital_camera.controllers;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Service;
import code.digital_camera.Constants;
import code.digital_camera.models.AudioMode;

public class ActionExecutor {

    private ControllerInterface controller;

    public ActionExecutor(ControllerInterface controller) {
        this.controller = controller;
    }

    public void setPowerStatus(UpnpService upnpService, Service service, boolean value) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.SET_TARGET));
        getTargetInvocation.setInput(Constants.NEW_TARGET_VALUE, value);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void setVolume(UpnpService upnpService, Service service, int value) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.SET_VOLUME));
        getTargetInvocation.setInput(Constants.IN, value);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void increaseVolume(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.INCREASE_VOLUME));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void decreaseVolume(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.DECREASE_VOLUME));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void setBassLevel(UpnpService upnpService, Service service, int value) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.SET_BASS_LEVEL));
        getTargetInvocation.setInput(Constants.IN, value);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void increaseBassLevel(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.INCREASE_BASS_LEVEL));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void decreaseBassLevel(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.DECREASE_BASS_LEVEL));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void setTrebleLevel(UpnpService upnpService, Service service, int value) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.SET_TREBLE_LEVEL));
        getTargetInvocation.setInput(Constants.IN, value);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void increaseTrebleLevel(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.INCREASE_TREBLE_LEVEL));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void decreaseTrebleLevel(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.DECREASE_TREBLE_LEVEL));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void setAudioMode(UpnpService upnpService, Service service, AudioMode value) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.SET_AUDIO_MODE));
        getTargetInvocation.setInput(Constants.IN, value.toString());
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void setPlayStatus(UpnpService upnpService, Service service, boolean value) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.SET_PLAY_STATUS));
        getTargetInvocation.setInput(Constants.IN, value);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void setTimerStatus(UpnpService upnpService, Service service, boolean value) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.SET_TIMER_STATUS));
        getTargetInvocation.setInput(Constants.IN, value);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void nextTrack(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.NEXT_TRACK));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void prevTrack(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.PREV_TRACK));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void setTimerValue(UpnpService upnpService, Service service, int value) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.SET_TIMER_VALUE));
        getTargetInvocation.setInput(Constants.IN, value);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called set action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getPowerStatus(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_TARGET));
        getTargetInvocation.getOutput(Constants.RET_TARGET_VALUE);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        boolean powerStatus = (boolean) invocation.getOutput()[0].getValue();
                        System.out.println("Current powerStatus: " + powerStatus);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getVolume(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_VOLUME));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        int volume = (int) invocation.getOutput()[0].getValue();
                        System.out.println("Current volume: " + volume);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getBassLevel(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_BASS_LEVEL));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        int bassLevel = (int) invocation.getOutput()[0].getValue();
                        System.out.println("Current bassLevel: " + bassLevel);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getTrebleLevel(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_TREBLE_LEVEL));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        int trebleLevel = (int) invocation.getOutput()[0].getValue();
                        System.out.println("Current trebleLevel: " + trebleLevel);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getAudioMode(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_AUDIO_MODE));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        AudioMode audioMode = (AudioMode) invocation.getOutput()[0].getValue();
                        System.out.println("Current audioMode: " + audioMode);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getPlayStatus(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_PLAY_STATUS));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        boolean playStatus = (boolean) invocation.getOutput()[0].getValue();
                        System.out.println("Current playStatus: " + playStatus);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getTimerStatus(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_TIMER_STATUS));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        boolean timerStatus = (boolean) invocation.getOutput()[0].getValue();
                        System.out.println("Current timerStatus: " + timerStatus);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getTimerValue(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_TIMER_VALUE));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        int timerValue = (int) invocation.getOutput()[0].getValue();
                        System.out.println("Current timerValue: " + timerValue);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    public void getTrackNo(UpnpService upnpService, Service service) {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constants.GET_TRACK_NO));
        getTargetInvocation.getOutput(Constants.OUT);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        int trackNo = (int) invocation.getOutput()[0].getValue();
                        System.out.println("Current trackNo: " + trackNo);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }
}
