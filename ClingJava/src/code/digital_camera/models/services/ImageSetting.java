package code.digital_camera.models.services;

import org.fourthline.cling.binding.annotations.*;
import code.digital_camera.Constants;
import code.digital_camera.models.AudioMode;

import java.beans.PropertyChangeSupport;

@UpnpService(
        serviceId = @UpnpServiceId(Constants.IMAGE_SETTING),
        serviceType = @UpnpServiceType(value = Constants.IMAGE_SETTING, version = 1)
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
public class ImageSetting {

    private final PropertyChangeSupport propertyChangeSupport;

    @UpnpStateVariable(
            defaultValue = "50",
            allowedValueMinimum = Constants.LIGHT_MIN,
            allowedValueMaximum = Constants.LIGHT_MAX
    )
    private int light;

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


    public ImageSetting() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public int getLight() {
        return light;
    }

    @UpnpAction
    public void setLight(@UpnpInputArgument(name = Constants.IN) int light) {
        if (light >= Constants.LIGHT_MIN && light <= Constants.LIGHT_MAX) {
            this.light = light;
            getPropertyChangeSupport().firePropertyChange(Constants.LIGHT, null, null);
        }
    }

    @UpnpAction
    public void increaseLight() {
        if (light + 10 <= Constants.LIGHT_MAX) {
            light += 10;
            getPropertyChangeSupport().firePropertyChange(Constants.LIGHT, null, null);
        }
    }

    @UpnpAction
    public void decreaseLight() {
        if (light - 10 >= Constants.LIGHT_MIN) {
            light -= 10;
            getPropertyChangeSupport().firePropertyChange(Constants.LIGHT, null, null);
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
