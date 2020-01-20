package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     *  screen size width 1024px, height 700px
     *  screen title "VideoPlayer"
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VideoPlayer");
        primaryStage.setScene(new Scene(root, 1024, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }
}
