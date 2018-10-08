package code.digital_camera.models.services;

import org.fourthline.cling.binding.annotations.*;
import code.digital_camera.Constants;

import java.beans.PropertyChangeSupport;

@UpnpService(
        serviceId = @UpnpServiceId(Constants.PLAY_MUSIC),
        serviceType = @UpnpServiceType(value = Constants.PLAY_MUSIC, version = 1)
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
public class PlayMusic {

    private final PropertyChangeSupport propertyChangeSupport;

    @UpnpStateVariable(
            defaultValue = "0"
    )
    private boolean playStatus;

    @UpnpStateVariable(
            defaultValue = "0"
    )
    private boolean timerStatus;

    @UpnpStateVariable(
            defaultValue = "1",
            allowedValueMinimum = Constants.TRACK_MIN,
            allowedValueMaximum = Constants.TRACK_MAX
    )
    private int trackNo;

    @UpnpStateVariable(
            defaultValue = "60",
            allowedValueMinimum = Constants.TIMER_MIN,
            allowedValueMaximum = Constants.TIMER_MAX
    )
    private int timerValue;


    public PlayMusic() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public boolean getPlayStatus() {
        return playStatus;
    }

    @UpnpAction
    public void setPlayStatus(@UpnpInputArgument(name = Constants.IN) boolean playStatus) {
        if (playStatus != this.playStatus) {
            this.playStatus = playStatus;
            getPropertyChangeSupport().firePropertyChange(Constants.PLAY_STATUS, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public boolean getTimerStatus() {
        return timerStatus;
    }

    @UpnpAction
    public void setTimerStatus(@UpnpInputArgument(name = Constants.IN) boolean timerStatus) {
        if (timerStatus != this.timerStatus) {
            this.timerStatus = timerStatus;
            getPropertyChangeSupport().firePropertyChange(Constants.TIMER_STATUS, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public int getTrackNo() {
        return trackNo;
    }

    @UpnpAction
    public void setTrackNo(@UpnpInputArgument(name = Constants.IN) int trackNo) {
        if (trackNo >= Constants.TRACK_MIN && trackNo <= Constants.TRACK_MAX) {
            this.trackNo = trackNo;
            getPropertyChangeSupport().firePropertyChange(Constants.TRACK_NO, null, null);
        }
    }

    @UpnpAction
    public void nextTrack() {
        if (trackNo + 1 <= Constants.TRACK_MAX) {
            this.trackNo++;
            getPropertyChangeSupport().firePropertyChange(Constants.TRACK_NO, null, null);
        }
    }

    @UpnpAction
    public void prevTrack() {
        if (trackNo - 1 >= Constants.TRACK_MIN) {
            this.trackNo--;
            getPropertyChangeSupport().firePropertyChange(Constants.TRACK_NO, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public int getTimerValue() {
        return timerValue;
    }

    @UpnpAction
    public void setTimerValue(@UpnpInputArgument(name = Constants.IN) int timerValue) {
        if (timerValue > Constants.TIMER_MIN) {
            this.timerValue = timerValue;
            getPropertyChangeSupport().firePropertyChange(Constants.TIMER_VALUE, null, null);
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