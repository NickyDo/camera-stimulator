package code.digital_camera.views;

import code.digital_camera.models.AudioMode;

public interface ViewInterface {
    void onVolumeChange(int newValue);
    void onPowerStatusChange(boolean status);
    void onModeChange(AudioMode newMode);
    void onTrackChange(int trackNo);
    void onTimerValueChange(int newValue);
}
