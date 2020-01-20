package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.media.*;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private MediaView mediaV;


    @FXML
    private ListView<String> menuList = new ListView<String>();

    @FXML
    private ListView<String> playlistList = new ListView<String>();

    @FXML
    private ListView<String> clipsInPlaylist = new ListView<String>();



    private MediaPlayer mp;
    private Media me;
    ObservableList<String> videoLocations = FXCollections.observableArrayList();
    ObservableList<String> playlistNames = FXCollections.observableArrayList();
    ObservableList<String> clipNamesFromPlaylist = FXCollections.observableArrayList();

    /**
     *
     *
     * @param location
     * @param resources
     *
     */
    public void initialize(URL location, ResourceBundle resources){
        //this part of initialize method displayes all the clips in a list
        String data = "";
        int counter = 1;
        while (!data.equals("|ND|")) {
            DB.selectSQL("SELECT fldTitle FROM tblClips WHERE fldClipID=" + counter + ";");
            data = DB.getData();


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
        //this part of initialize displayes all the playlists in a list
        String playlistData = "";
        int playlistCounter = 1;
        while (!playlistData.equals("|ND|")) {
            DB.selectSQL("select fldPlaylistName from tblPlaylists where fldPlaylistID=" + playlistCounter + ";");
            playlistData = DB.getData();
            System.out.println(data);

            if (!playlistData.equals("|ND|")) {

               playlistNames.add(playlistData);
            }
            playlistCounter++;
        }
        playlistList.setItems(playlistNames);

        playlistList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                displayClipNamesFromPlaylist();
            }
        });

    }

    /**
     *  here is the program select the clips from the playlist
     */
    //this method displayes the clip names in a list from the chosen playlist
    private void displayClipNamesFromPlaylist(){
        String data = "";
        int counter = 1;
        DB.selectSQL("SELECT \n" +
                "    fldTitle\n" +
                "FROM\n" +
                "    tblClips\n" +
                "WHERE\n" +
                "    fldClipID IN (SELECT \n" +
                "            fldClipID\n" +
                "        FROM\n" +
                "            tblIDs\n" +
                "        WHERE\n" +
                "            fldPlaylistID =1);");

        while (!data.equals("|ND|")) {

            data = DB.getData();


            if (!data.equals("|ND|")) {
                System.out.println(data);
                clipNamesFromPlaylist.add(data);
            }
            counter++;
        }
        clipsInPlaylist.setItems(clipNamesFromPlaylist);

        clipsInPlaylist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                GetFileLocation(newValue);
            }
        });

    }

    /**
     *
     * @param title
     */
    // this method retrieves the file location from the database
    private void GetFileLocation(String title) {
        DB.selectSQL("select fldStorage from tblClips Where fldTitle='" + title + "';");
        String filePath = DB.getData();
        System.out.println(filePath);
        LoadUpVideo(filePath);
    }

    /**
     *
     * @param filepath
     */
    //this method loads and displayes the media content
    private void LoadUpVideo(String filepath){
        String path = new File(filepath).getAbsolutePath();


        me = new Media(new File(path).toURI().toString());

        mp = new MediaPlayer(me);

        mediaV.setMediaPlayer(mp);

        mp.setAutoPlay(false);
    }

    @FXML
    /**
     * Handler for the play button
     */
    private void handlePlay()
    {

        mp.play();
    }
    @FXML
    /**
     * Handler for pause button
     */
    private void handlePause()
    {
        mp.pause();
    }
    @FXML
    /**
     * Handler for stop button
     */
    private void handleStop()
    {
        mp.stop();
    }

}
