package code.digital_camera.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import code.digital_camera.Constants;
import code.digital_camera.controllers.ControllerInterface;
import code.digital_camera.models.AudioMode;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class View implements Initializable, ViewInterface {
    @FXML
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
    Label song;
    @FXML
    Label nextSong;
    @FXML
    Button playButton ;


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



    @Override
    public void onVolumeChange(int newValue) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (newValue <= Constants.VOLUME_MAX && newValue >= Constants.VOLUME_MIN) {
                    mediaPlayer.setVolume((double) newValue / 100);
//                    volLVL.setText(String.format("%.1f", mediaPlayer.getVolume()));
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
    public void onModeChange(AudioMode newMode) {
        switch (newMode) {
            case NORMAL:
//
                audioMode = newMode;
                break;
            case POP:
//
                audioMode = newMode;
                break;
            case ROCK:
//
                audioMode = newMode;
                break;
            default:
                break;
        }
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
    }
}
