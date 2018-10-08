package code.audio_system.controllers;

import code.audio_system.models.AudioMode;

public interface ControllerInterface {
    boolean setPowerStatus(boolean status);
    boolean increaseVolume();
    boolean decreaseVolume();
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
