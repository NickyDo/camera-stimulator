package code.digital_camera.views;

import code.digital_camera.models.CaptureMode;

public interface ViewInterface {
    void onVolumeChange(int newValue);
    void onPowerStatusChange(boolean status);
    void onZoomLevelChange(int newValue);
    void onContrastLevelChange(int newValue);
    void onModeChange(CaptureMode newMode);
    void onCaptureStatusChange(boolean newStatus);
    void onTrackChange(int trackNo);
    void onTimerValueChange(int newValue);
    void onTimerStatusChange(boolean status);
}
