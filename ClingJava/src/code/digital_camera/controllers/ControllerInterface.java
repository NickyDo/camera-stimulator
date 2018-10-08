package code.digital_camera.controllers;

import code.digital_camera.models.AudioMode;

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
    boolean setMode(AudioMode mode);
    boolean setPlayStatus(boolean status);
    boolean nextTrack();
    boolean prevTrack();
    boolean setTimerValue(int value);
    boolean setTimerStatus(boolean status);
}
