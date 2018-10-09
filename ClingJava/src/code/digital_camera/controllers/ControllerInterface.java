package code.digital_camera.controllers;

import code.digital_camera.models.CaptureMode;

public interface ControllerInterface {
    boolean setPowerStatus(boolean status);
    boolean increaseLight();
    boolean decreaseLight();
    boolean setZoomLevel(int value);
    boolean increaseZoomLevel();
    boolean decreaseZoomLevel();
    boolean setContrastLevel(int value);
    boolean increaseContrastLevel();
    boolean decreaseContrastLevel();
    boolean setMode(CaptureMode mode);
    boolean setCaptureStatus(boolean status);
    boolean nextTrack();
    boolean prevTrack();
    boolean setTimerValue(int value);
    boolean setTimerStatus(boolean status);
}
