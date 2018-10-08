package code.digital_camera.controllers;

import code.digital_camera.models.AudioMode;

public interface ControllerInterface {
    boolean setPowerStatus(boolean status);
    boolean increaseLight();
    boolean decreaseLight();
    boolean setTrebleLevel(int value);
    boolean increaseTrebleLevel();
    boolean decreaseTrebleLevel();
    boolean setBassLevel(int value);
    boolean increaseBassLevel();
    boolean decreaseBassLevel();
    boolean setMode(AudioMode mode);
    boolean setPlayStatus(boolean status);
    boolean nextTrack();
    boolean prevTrack();
    boolean setTimerValue(int value);
    boolean setTimerStatus(boolean status);
}
