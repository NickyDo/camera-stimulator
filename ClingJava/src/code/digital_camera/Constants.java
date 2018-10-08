package code.digital_camera;

import code.digital_camera.models.AudioMode;

public class Constants {
    // device
    public static final String DEVICE_NAME = "SmartCamera";
    public static final String MANUFACTURER_DETAILS = "1918";
    public static final String MODEL_DETAILS = "SC2018";
    public static final String MODEL_DESCRIPTION = "Smart Camera System";
    public static final String MODEL_NUMBER = "v1";

    // switch power service
    public static final String SWITCH_POWER = "SwitchPower";
    public static final String STATUS = "Status";
    public static final String GET_STATUS = "GetStatus";
    public static final String TARGET = "Target";
    public static final String SET_TARGET = "SetTarget";
    public static final String GET_TARGET = "GetTarget";
    public static final String NEW_TARGET_VALUE = "NewTargetValue";
    public static final String RET_TARGET_VALUE = "RetTargetValue";
    public static final String RESULT_STATUS = "ResultStatus";

    // audio control service
    public static final String IMAGE_SETTING = "ImageSetting";
    public static final String LIGHT = "Light";
    public static final String GET_LIGHT = "GetLight";
    public static final String SET_LIGHT = "SetLight";
    public static final String INCREASE_LIGHT = "IncreaseLight";
    public static final String DECREASE_LIGHT = "DecreaseLight";
    public static final String CONTRAST_LEVEL = "ContrastLevel";
    public static final String GET_CONTRAST_LEVEL = "GetContrastLevel";
    public static final String SET_CONTRAST_LEVEL = "SetContrastLevel";
    public static final String INCREASE_CONTRAST_LEVEL = "IncreaseContrastLevel";
    public static final String DECREASE_CONTRAST_LEVEL = "DecreaseContrastLevel";
    public static final String ZOOM_LEVEL = "ZoomLevel";
    public static final String GET_ZOOM_LEVEL = "GetZoomLevel";
    public static final String SET_ZOOM_LEVEL = "SetZoomLevel";
    public static final String INCREASE_ZOOM_LEVEL = "IncreaseZoomLevel";
    public static final String DECREASE_ZOOM_LEVEL = "DecreaseZoomLevel";
    public static final String AUDIO_MODE = "AudioMode";
    public static final String GET_AUDIO_MODE = "GetAudioMode";
    public static final String SET_AUDIO_MODE = "SetAudioMode";

    // play music service
    public static final String PLAY_MUSIC = "PlayMusic";
    public static final String CAPTURE_STATUS = "CaptureStatus";
    public static final String GET_CAPTURE_STATUS = "GetCaptureStatus";
    public static final String SET_CAPTURE_STATUS = "SetCaptureStatus";
    public static final String TRACK_NO = "TrackNo";
    public static final String GET_TRACK_NO = "GetTrackNo";
    public static final String SET_TRACK_NO = "SetTrackNo";
    public static final String NEXT_TRACK = "NextTrack";
    public static final String PREV_TRACK = "PrevTrack";
    public static final String TIMER_VALUE = "TimerValue";
    public static final String GET_TIMER_VALUE = "GetTimerValue";
    public static final String SET_TIMER_VALUE = "SetTimerValue";
    public static final String TIMER_STATUS = "TimerStatus";
    public static final String GET_TIMER_STATUS = "GetTimerStatus";
    public static final String SET_TIMER_STATUS = "SetTimerStatus";

    // input argument
    public static final String IN = "In";
    public static final String OUT = "Out";

    // resources
    public static final String CAMERA_SYSTEM_IMAGE = "/resources/camera.png";

    // min-max values
    public static final int VOLUME_DEFAULT = 100;
    public static final int LIGHT_MAX = 100;
    public static final int LIGHT_MIN = 0;
    public static final int CONTRAST_MAX = 100;
    public static final int CONTRAST_MIN = 0;
    public static final int ZOOM_MAX = 3;
    public static final int ZOOM_MIN = 0;
    public static final int TIMER_MIN = 0;
    public static final int TIMER_MAX = Integer.MAX_VALUE;
    public static final int TRACK_MIN = 0;
    public static final int TRACK_MAX = Integer.MAX_VALUE;
    public static final boolean TIMER_STATUS_DEFAULT = false;
    public static final boolean CAPTURE_STATUS_DEFAULT = true;
    public static final boolean POWER_STATUS_DEFAULT = true;
    public static final AudioMode AUDIO_MODE_DEFAULT = AudioMode.NORMAL;
    public static final int DEFAULT_TIMER_VALUE = 10;
}
