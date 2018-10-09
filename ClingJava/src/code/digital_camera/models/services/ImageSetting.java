package code.digital_camera.models.services;

import org.fourthline.cling.binding.annotations.*;
import code.digital_camera.Constants;
import code.digital_camera.models.CaptureMode;

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
            defaultValue = "50",
            allowedValueMinimum = Constants.CONTRAST_MIN,
            allowedValueMaximum = Constants.CONTRAST_MAX
    )
    private int contrastLevel;

    @UpnpStateVariable(
            defaultValue = "0",
            allowedValueMinimum = Constants.ZOOM_MIN,
            allowedValueMaximum = Constants.ZOOM_MAX
    )
    private int zoomLevel;

    @UpnpStateVariable(
            defaultValue = "NORMAL",
            allowedValues = {"NORMAL", "POP", "ROCK"}
    )
    private CaptureMode captureMode;


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
    public int getContrastLevel() {
        return contrastLevel;
    }

    @UpnpAction
    public void setContrastLevel(@UpnpInputArgument(name = Constants.IN) int contrastLevel) {
        if (contrastLevel >= Constants.CONTRAST_MIN && contrastLevel <= Constants.CONTRAST_MAX) {
            this.contrastLevel = contrastLevel;
            getPropertyChangeSupport().firePropertyChange(Constants.CONTRAST_LEVEL, null, null);
        }
    }

    @UpnpAction
    public void increaseContrastLevel() {
        if (contrastLevel + 1 <= Constants.CONTRAST_MAX) {
            contrastLevel++;
            getPropertyChangeSupport().firePropertyChange(Constants.CONTRAST_LEVEL, null, null);
        }
    }

    @UpnpAction
    public void decreaseContrastLevel() {
        if (contrastLevel - 1 >= Constants.CONTRAST_MIN) {
            contrastLevel--;
            getPropertyChangeSupport().firePropertyChange(Constants.CONTRAST_LEVEL, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public int getZoomLevel() {
        return zoomLevel;
    }

    @UpnpAction
    public void setZoomLevel(@UpnpInputArgument(name = Constants.IN) int zoomLevel) {
        if (zoomLevel >= Constants.ZOOM_MIN && zoomLevel <= Constants.ZOOM_MAX) {
            this.zoomLevel = zoomLevel;
            getPropertyChangeSupport().firePropertyChange(Constants.ZOOM_LEVEL, null, null);
        }
    }

    @UpnpAction
    public void increaseZoomLevel() {
        if (zoomLevel + 1 <= Constants.ZOOM_MAX) {
            zoomLevel++;
            getPropertyChangeSupport().firePropertyChange(Constants.ZOOM_LEVEL, null, null);
        }
    }

    @UpnpAction
    public void decreaseZoomLevel() {
        if (zoomLevel - 1 >= Constants.ZOOM_MIN) {
            zoomLevel--;
            getPropertyChangeSupport().firePropertyChange(Constants.ZOOM_LEVEL, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public CaptureMode getCaptureMode() {
        return captureMode;
    }

    @UpnpAction
    public void setCaptureMode(@UpnpInputArgument(name = Constants.IN) String captureMode) {
        if (this.captureMode != CaptureMode.valueOf(captureMode)) {
            this.captureMode = CaptureMode.valueOf(captureMode);
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
