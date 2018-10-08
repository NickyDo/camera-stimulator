package code.digitalcamera.camera_system.models.services;

import code.digitalcamera.camera_system.Constants;
import code.digitalcamera.camera_system.models.AudioMode;
import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

@UpnpService(
        serviceId = @UpnpServiceId(Constants.AUDIO_CONTROL),
        serviceType = @UpnpServiceType(value = Constants.AUDIO_CONTROL, version = 1)
)
//@UpnpStateVariables(
//        {
//                @UpnpStateVariable(
//                        name = "Target",
//                        defaultValue = "0",
//                        sendEvents = false
//                ),
//                @UpnpStateVariable(
//                        name = "Status",
//                        defaultValue = "0"
//                )
//        }
//)
public class AudioControl {

    private final PropertyChangeSupport propertyChangeSupport;

    @UpnpStateVariable(
            defaultValue = "100",
            allowedValueMinimum = Constants.VOLUME_MIN,
            allowedValueMaximum = Constants.VOLUME_MAX
    )
    private int volume;

    @UpnpStateVariable(
            defaultValue = "0",
            allowedValueMinimum = Constants.BASS_MIN,
            allowedValueMaximum = Constants.BASS_MAX
    )
    private int bassLevel;

    @UpnpStateVariable(
            defaultValue = "0",
            allowedValueMinimum = Constants.TREBLE_MIN,
            allowedValueMaximum = Constants.TREBLE_MAX
    )
    private int trebleLevel;

    @UpnpStateVariable(
            defaultValue = "NORMAL",
            allowedValues = {"NORMAL", "POP", "ROCK"}
    )
    private AudioMode audioMode;


    public AudioControl() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public int getVolume() {
        return volume;
    }

    @UpnpAction
    public void setVolume(@UpnpInputArgument(name = Constants.IN) int volume) {
        if (volume >= Constants.VOLUME_MIN && volume <= Constants.VOLUME_MAX) {
            this.volume = volume;
            getPropertyChangeSupport().firePropertyChange(Constants.VOLUME, null, null);
        }
    }

    @UpnpAction
    public void increaseVolume() {
        if (volume + 10 <= Constants.VOLUME_MAX) {
            volume += 10;
            getPropertyChangeSupport().firePropertyChange(Constants.VOLUME, null, null);
        }
    }

    @UpnpAction
    public void decreaseVolume() {
        if (volume - 10 >= Constants.VOLUME_MIN) {
            volume -= 10;
            getPropertyChangeSupport().firePropertyChange(Constants.VOLUME, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public int getBassLevel() {
        return bassLevel;
    }

    @UpnpAction
    public void setBassLevel(@UpnpInputArgument(name = Constants.IN) int bassLevel) {
        if (bassLevel >= Constants.BASS_MIN && bassLevel <= Constants.BASS_MAX) {
            this.bassLevel = bassLevel;
            getPropertyChangeSupport().firePropertyChange(Constants.BASS_LEVEL, null, null);
        }
    }

    @UpnpAction
    public void increaseBassLevel() {
        if (bassLevel + 1 <= Constants.BASS_MAX) {
            bassLevel++;
            getPropertyChangeSupport().firePropertyChange(Constants.BASS_LEVEL, null, null);
        }
    }

    @UpnpAction
    public void decreaseBassLevel() {
        if (bassLevel - 1 >= Constants.BASS_MIN) {
            bassLevel--;
            getPropertyChangeSupport().firePropertyChange(Constants.BASS_LEVEL, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public int getTrebleLevel() {
        return trebleLevel;
    }

    @UpnpAction
    public void setTrebleLevel(@UpnpInputArgument(name = Constants.IN) int trebleLevel) {
        if (trebleLevel >= Constants.TREBLE_MIN && trebleLevel <= Constants.TREBLE_MAX) {
            this.trebleLevel = trebleLevel;
            getPropertyChangeSupport().firePropertyChange(Constants.TREBLE_LEVEL, null, null);
        }
    }

    @UpnpAction
    public void increaseTrebleLevel() {
        if (trebleLevel + 1 <= Constants.TREBLE_MAX) {
            trebleLevel++;
            getPropertyChangeSupport().firePropertyChange(Constants.TREBLE_LEVEL, null, null);
        }
    }

    @UpnpAction
    public void decreaseTrebleLevel() {
        if (trebleLevel - 1 >= Constants.TREBLE_MIN) {
            trebleLevel--;
            getPropertyChangeSupport().firePropertyChange(Constants.TREBLE_LEVEL, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public AudioMode getAudioMode() {
        return audioMode;
    }

    @UpnpAction
    public void setAudioMode(@UpnpInputArgument(name = Constants.IN) String audioMode) {
        if (this.audioMode != AudioMode.valueOf(audioMode)) {
            this.audioMode = AudioMode.valueOf(audioMode);
            getPropertyChangeSupport().firePropertyChange(Constants.AUDIO_MODE, null, null);
        }
    }

//    @UpnpAction
//    public void setTarget(@UpnpInputArgument(name = Constants.IN) int status) {
//    }
//
//    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
//    public int getTarget() {
//        return 0;
//    }
//
//    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
//    public int getStatus() {
//        return 0;
//    }
}
