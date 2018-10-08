package code.digital_camera;

import code.digital_camera.models.AudioMode;

public class Constants {
    // device
    public static final String DEVICE_NAME = "AudioSystem";
    public static final String MANUFACTURER_DETAILS = "1918";
    public static final String MODEL_DETAILS = "AS2018";
    public static final String MODEL_DESCRIPTION = "Simple Audio System";
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
    public static final String AUDIO_CONTROL = "AudioControl";
    public static final String VOLUME = "Volume";
    public static final String GET_VOLUME = "GetVolume";
    public static final String SET_VOLUME = "SetVolume";
    public static final String INCREASE_VOLUME = "IncreaseVolume";
    public static final String DECREASE_VOLUME = "DecreaseVolume";
    public static final String BASS_LEVEL = "BassLevel";
    public static final String GET_BASS_LEVEL = "GetBassLevel";
    public static final String SET_BASS_LEVEL = "SetBassLevel";
    public static final String INCREASE_BASS_LEVEL = "IncreaseBassLevel";
    public static final String DECREASE_BASS_LEVEL = "DecreaseBassLevel";
    public static final String TREBLE_LEVEL = "TrebleLevel";
    public static final String GET_TREBLE_LEVEL = "GetTrebleLevel";
    public static final String SET_TREBLE_LEVEL = "SetTrebleLevel";
    public static final String INCREASE_TREBLE_LEVEL = "IncreaseTrebleLevel";
    public static final String DECREASE_TREBLE_LEVEL = "DecreaseTrebleLevel";
    public static final String AUDIO_MODE = "AudioMode";
    public static final String GET_AUDIO_MODE = "GetAudioMode";
    public static final String SET_AUDIO_MODE = "SetAudioMode";

    // play music service
    public static final String PLAY_MUSIC = "PlayMusic";
    public static final String PLAY_STATUS = "PlayStatus";
    public static final String GET_PLAY_STATUS = "GetPlayStatus";
    public static final String SET_PLAY_STATUS = "SetPlayStatus";
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
    public static final String AUDIO_SYSTEM_IMAGE = "/resources/audio_system.png";

    // min-max values
    public static final int VOLUME_DEFAULT = 100;
    public static final int VOLUME_MAX = 100;
    public static final int VOLUME_MIN = 0;
    public static final int BASS_MAX = 120;
    public static final int BASS_MIN = 0;
    public static final int BASS_DEFAULT = 0;
    public static final int TREBLE_MAX = 120;
    public static final int TREBLE_MIN = 0;
    public static final int TREBLE_DEFAULT = 0;
    public static final int TIMER_MIN = 0;
    public static final int TIMER_MAX = Integer.MAX_VALUE;
    public static final int TRACK_MIN = 0;
    public static final int TRACK_MAX = Integer.MAX_VALUE;
    public static final boolean TIMER_STATUS_DEFAULT = false;
    public static final boolean PLAY_STATUS_DEFAULT = true;
    public static final boolean POWER_STATUS_DEFAULT = true;
    public static final AudioMode AUDIO_MODE_DEFAULT = AudioMode.NORMAL;
    public static final int DEFAULT_TIMER_VALUE = 10;
}
