package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller{
    public void play(ActionEvent actionEvent) {
    }

    public void pause(ActionEvent actionEvent) {
    }

    public void stop(ActionEvent actionEvent) {
    }
    /**
     *
     */

    @FXML
    private Button play_btn, stop_btn, pause_btn;


    @Override
    public void initalize(URL location, ResourceBundle resouces){
       try {


           play_btn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/letöltés.png"))));
       }
       catch (Exception e){
           e.printStackTrace();

        }

    }





}

