package code.digitalcamera.camera_system.views;

import code.digitalcamera.camera_system.Constants;
import code.digitalcamera.camera_system.controllers.ControllerInterface;
import code.digitalcamera.camera_system.models.AudioMode;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioEqualizer;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class View implements Initializable, ViewInterface {
    private static MediaPlayer mediaPlayer;
    private static Boolean isDeviceOn = true;
    private ControllerInterface controller;
    private static AudioMode audioMode = AudioMode.NORMAL;
    private static ArrayList<String> songs = null;
    private static int curSongIndx = 0;
    private static boolean isTimerOn = false;

    @FXML
    ImageView powerBtn;
    @FXML
    ImageView playBtn;
    @FXML
    ImageView pauseBtn;
    @FXML
    ImageView upBtn;
    @FXML
    ImageView downBtn;
    @FXML
    ImageView status;
    @FXML
    ImageView backBtn;
    @FXML
    ImageView nextBtn;
    @FXML
    ImageView muteBtn;
    @FXML
    Label playMode;
    @FXML
    Label volLVL;
    @FXML
    Label muted;
    @FXML
    Slider bassSlider;
    @FXML
    TextField bassNumber;
    @FXML
    Slider trebleSlider;
    @FXML
    TextField trebleNumber;
    @FXML
    Circle normalMode;
    @FXML
    Circle popMode;
    @FXML
    Circle rockMode;
    @FXML
    Label song;
    @FXML
    Label nextSong;
    @FXML
    Button timerBtn;
    @FXML
    TextField timerNumber;
    @FXML
    Circle isTimerSet;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DrawButton drawButton = new DrawButton();
        drawButton.draw(powerBtn, "power.png", 50, 50);
        drawButton.draw(playBtn, "play.png", 50, 50);
        drawButton.draw(pauseBtn, "pause.png", 50, 50);
        drawButton.draw(upBtn, "inc_vol.png", 50, 50);
        drawButton.draw(downBtn, "dec_vol.png", 50, 50);
        drawButton.draw(backBtn, "back.png", 50, 50);
        drawButton.draw(nextBtn, "next.png", 50, 50);
        drawButton.draw(muteBtn, "mute.png", 50, 50);


        songs = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            songs.add(getClass().getResource("/resources/musics/file" + i + ".mp3").toString());
        }

        String path = songs.get(curSongIndx);
        Media media = new Media(path);
        newMediaPlayer(media);
        song.setText("Digital camera Setting");

        bassSlider.valueProperty().addListener(observable -> {
            AudioEqualizer audioEqualizer = mediaPlayer.getAudioEqualizer();
            audioEqualizer.setEnabled(true);
            ObservableList<EqualizerBand> list = audioEqualizer.getBands();

            double scale = bassSlider.getValue() / 120;
            for (int i = 0; i < 3; i++) {
                list.get(i).setGain(scale * (11 - i));
            }
            list.get(3).setGain(scale * 5);
            list.get(5).setGain(scale * -5);
            for (int i = 6; i < 10; i++) {
                list.get(i).setGain(scale * (i - 22));
            }
            if (!bassSlider.isValueChanging() || bassSlider.getValue() == 120 || bassSlider.getValue() == 0) {
                controller.setBassLevel((int) bassSlider.getValue());
            }
            bassNumber.setText(String.format("%.0f", bassSlider.getValue()));
        });

        trebleSlider.valueProperty().addListener(observable -> {
            AudioEqualizer audioEqualizer = mediaPlayer.getAudioEqualizer();
            audioEqualizer.setEnabled(true);
            ObservableList<EqualizerBand> list = audioEqualizer.getBands();

            double scale = trebleSlider.getValue() / 120;
            for (int i = 0; i < 3; i++) {
                list.get(i).setGain(scale * (i - 11));
            }
            list.get(3).setGain(scale * 5);
            list.get(5).setGain(scale * -5);
            for (int i = 6; i < 10; i++) {
                list.get(i).setGain(scale * (3 + i));
            }

            if (!trebleSlider.isValueChanging() || trebleSlider.getValue() == 120 || trebleSlider.getValue() == 0) {
                controller.setTrebleLevel((int) trebleSlider.getValue());
            }
            trebleNumber.setText(String.format("%.0f", trebleSlider.getValue()));
        });

    }

    public void setController(ControllerInterface controller) {
        this.controller = controller;
    }

    @FXML
    public void pauseCont(MouseEvent event) {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
            mediaPlayer.play();
            System.out.println("Resumed");
            playMode.setText("PLAY");
            controller.setPlayStatus(true);
        }
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.pause();
            System.out.println("Paused");
            playMode.setText("PAUSE");
            controller.setPlayStatus(false);
        }
    }

    @FXML
    public void handlePlay(MouseEvent event) {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.READY)) {
            mediaPlayer.play();
            System.out.println("Playing");
        }
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED)) {
            mediaPlayer.play();
            System.out.println("Playing after stopped");
        }
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
            mediaPlayer.play();
            System.out.println("Playing after paused");
        }
        playMode.setText("PLAY");
        controller.setPlayStatus(true);
    }

    @FXML
    public void upVolume(MouseEvent event) {
        double volume = mediaPlayer.getVolume();
        if (volume <= 0.9) {
            volume += 0.1;
            System.out.println("Current volume: " + volume);
            mediaPlayer.setVolume(volume);
            controller.increaseVolume();
        } else {
            System.out.println("At maximum value");
        }
        volLVL.setText(String.format("%.1f", volume));
    }

    @FXML
    public void downVolume(MouseEvent event) {
        double volume = mediaPlayer.getVolume();
        if (volume >= 0.1) {
            volume -= 0.1;
            System.out.println("Current volume: " + volume);
            mediaPlayer.setVolume(volume);
            controller.decreaseVolume();
        } else {
            System.out.println("At minimum value");
        }
        volLVL.setText(String.format("%.1f", volume));
    }

    @FXML
    public void handleMute(MouseEvent event) {
        if (!mediaPlayer.isMute()) {
            mediaPlayer.setMute(true);
            System.out.println("Muted");
            muted.setText("YES");
        } else {
            mediaPlayer.setMute(false);
            System.out.println("Un-muted");
            muted.setText("NO");
        }
    }

    @FXML
    public void handleNormalMode(MouseEvent event) {
        audioMode = AudioMode.NORMAL;
        normalMode.setFill(Color.DODGERBLUE);
        popMode.setFill(Color.WHITE);
        rockMode.setFill(Color.WHITE);
        controller.setMode(audioMode);
    }

    @FXML
    public void handlePopMode(MouseEvent event) {
        audioMode = AudioMode.POP;
        normalMode.setFill(Color.WHITE);
        popMode.setFill(Color.DODGERBLUE);
        rockMode.setFill(Color.WHITE);
        controller.setMode(audioMode);
    }

    @FXML
    public void handleRockMode(MouseEvent event) {
        audioMode = AudioMode.ROCK;
        normalMode.setFill(Color.WHITE);
        popMode.setFill(Color.WHITE);
        rockMode.setFill(Color.DODGERBLUE);
        controller.setMode(audioMode);
    }

    @FXML
    public void poweroff(MouseEvent event) {
        DrawButton drawButton = new DrawButton();
        if (isDeviceOn) {
            drawButton.draw(powerBtn, "power_off.png", 50, 50);
            controller.setPowerStatus(false);
            isDeviceOn = false;
        } else {
            drawButton.draw(powerBtn, "power.png", 50, 50);
            isDeviceOn = true;
            controller.setPowerStatus(true);
        }
    }

    @FXML
    public void handleNext(MouseEvent event){
        if (curSongIndx <= 4 && curSongIndx >= 0){
            controller.nextTrack();
        }
    }

    @FXML
    public void handleBack(MouseEvent event){
        if (curSongIndx <= 5 && curSongIndx >= 1){
            controller.prevTrack();
        }
    }

    @FXML
    public void handleTimer(ActionEvent event){
        if (isTimerOn){
            isTimerSet.setFill(Color.WHITE);
            isTimerOn = false;
            controller.setTimerStatus(false);
        } else {
            isTimerSet.setFill(Color.LIGHTGREEN);
            isTimerOn = true;
            try {
                controller.setTimerValue(Integer.parseInt(timerNumber.getText()));
                controller.setTimerStatus(true);
            } catch (NumberFormatException e) {
                controller.setTimerValue(Constants.DEFAULT_TIMER_VALUE);
                controller.setTimerStatus(true);
            }
        }
    }

    @Override
    public void onVolumeChange(int newValue) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (newValue <= Constants.VOLUME_MAX && newValue >= Constants.VOLUME_MIN) {
                    mediaPlayer.setVolume((double) newValue / 100);
                    volLVL.setText(String.format("%.1f", mediaPlayer.getVolume()));
                }
            }
        });
    }

    @Override
    public void onPowerStatusChange(boolean status) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                DrawButton drawButton = new DrawButton();
                if (status) {
                    drawButton.draw(powerBtn, "power.png", 50, 50);
                    isDeviceOn = true;
                    disablePlayer(false);
                } else {
                    drawButton.draw(powerBtn, "power_off.png", 50, 50);
                    isDeviceOn = false;
                    disablePlayer(true);
                }
            }
        });
    }

    @Override
    public void onBassLevelChange(int newValue) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (newValue >= Constants.BASS_MIN && newValue <= Constants.BASS_MAX) {
                    bassSlider.setValue(newValue);
                    bassNumber.setText(String.format("%.0f", bassSlider.getValue()));
                }
            }
        });
    }

    @Override
    public void onTrebleLevelChange(int newValue) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (newValue >= Constants.TREBLE_MIN && newValue <= Constants.TREBLE_MAX) {
                    trebleSlider.setValue(newValue);
                    trebleNumber.setText(String.format("%.0f", trebleSlider.getValue()));
                }
            }
        });
    }

    @Override
    public void onModeChange(AudioMode newMode) {
        switch (newMode) {
            case NORMAL:
                normalMode.setFill(Color.DODGERBLUE);
                popMode.setFill(Color.WHITE);
                rockMode.setFill(Color.WHITE);
                audioMode = newMode;
                break;
            case POP:
                normalMode.setFill(Color.WHITE);
                popMode.setFill(Color.DODGERBLUE);
                rockMode.setFill(Color.WHITE);
                audioMode = newMode;
                break;
            case ROCK:
                normalMode.setFill(Color.WHITE);
                popMode.setFill(Color.WHITE);
                rockMode.setFill(Color.DODGERBLUE);
                audioMode = newMode;
                break;
            default:
                break;
        }
    }

    @Override
    public void onPlayStatusChange(boolean newStatus) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (newStatus) {
                    mediaPlayer.play();
                    playMode.setText("PLAY");
                } else {
                    mediaPlayer.pause();
                    playMode.setText("PAUSE");
                }
            }
        });
    }

    @Override
    public void onTrackChange(int trackNo) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (trackNo <= 5 && trackNo >= 0){
                    curSongIndx = trackNo;
                    mediaPlayer.stop();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Media media = new Media(songs.get(curSongIndx));
                    newMediaPlayer(media);
                    mediaPlayer.play();
                    song.setText("SONG " + curSongIndx);
                    if (trackNo == 5){
                        nextSong.setText("UNKNOWN");
                    } else {
                        nextSong.setText("SONG " + (curSongIndx + 1));
                    }
                }
            }
        });
    }

    @Override
    public void onTimerValueChange(int newValue) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timerNumber.setText(String.format("%d", newValue));
            }
        });
    }

    @Override
    public void onTimerStatusChange(boolean status) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                isTimerOn = status;
                if (status){
                    isTimerSet.setFill(Color.LIGHTGREEN);
                } else {
                    isTimerSet.setFill(Color.WHITE);
                }
            }
        });
    }

    private void newMediaPlayer(Media media){
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                handleNext(null);
            }
        });
    }

    private void disablePlayer(boolean status){
        if (status){
            mediaPlayer.stop();
        }

        playBtn.setDisable(status);
        pauseBtn.setDisable(status);
        backBtn.setDisable(status);
        nextBtn.setDisable(status);
        upBtn.setDisable(status);
        downBtn.setDisable(status);
        muteBtn.setDisable(status);
        timerBtn.setDisable(status);
        bassSlider.setDisable(status);
        trebleSlider.setDisable(status);
        normalMode.setDisable(status);
        popMode.setDisable(status);
        rockMode.setDisable(status);
        timerNumber.setDisable(status);
    }
}
