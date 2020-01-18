package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.media.*;
import javafx.scene.control.Button;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private MediaView mediaV;

    @FXML
    private Button play;

    @FXML
    private ListView<String> menuList = new ListView<String>();

    @FXML
    private ListView<String> playlist = new ListView<String>();


    private MediaPlayer mp;
    private Media me;
    ObservableList<String> videoLocations = FXCollections.observableArrayList();

    /**
     * This method is invoked automatically in the beginning. Used for initializing, loading data etc.
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources){
        // Build the path to the location of the media file!

        String data = "";
        int counter = 1;
        while (!data.equals("|ND|")) {
            DB.selectSQL("SELECT fldTitle FROM tblClips WHERE fldClipID=" + counter + ";");
            data = DB.getData();
            //System.out.println(data);

            if (!data.equals("|ND|")) {
                System.out.println(data);
                videoLocations.add(data);
            }
            counter++;
        }
        menuList.setItems(videoLocations);

        menuList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                GetFileLocation(newValue);
            }
        });

    }

    private void GetFileLocation(String title) {
        DB.selectSQL("select fldStorage from tblClips Where fldTitle='" + title + "';");
        String filePath = DB.getData();
        System.out.println(filePath);
        LoadUpVideo(filePath);
    }

    private void LoadUpVideo(String filepath){
        String path = new File(filepath).getAbsolutePath();

        // Create new Media object (the actual media content)
        me = new Media(new File(path).toURI().toString());
        // Create new MediaPlayer and attach the media to be played
        mp = new MediaPlayer(me);
        //
        mediaV.setMediaPlayer(mp);
        // mp.setAutoPlay(true);
        // If autoplay is turned of the method play(), stop(), pause() etc controls how/when medias are played
        mp.setAutoPlay(false);
    }

    @FXML
    /**
     * Handler for the play button
     */
    private void handlePlay()
    {
        // Play the mediaPlayer with the attached media
        mp.play();
    }
    @FXML
    /**
     * Handler for pause
     */
    private void handlePause()
    {
        mp.pause();
    }
    @FXML
    /**
     * Handler for pause
     */
    private void handleStop()
    {
        mp.stop();
    }

}
