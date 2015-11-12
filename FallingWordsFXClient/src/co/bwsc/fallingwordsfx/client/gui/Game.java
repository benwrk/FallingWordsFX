package co.bwsc.fallingwordsfx.client.gui;

import co.bwsc.fallingwordsfx.client.ConfigManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class Game extends Application {

    public static void invoke(String[] args) {
        launch(args);
    }

    @FXML
    private Label localScore;

    @FXML
    private Label remoteScore;

    @FXML
    private Label time;

    @FXML
    private TextField input;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        primaryStage.setTitle(ConfigManager.CFG.getApplicationName());
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();
    }

    @FXML
    private void handleInputAction(ActionEvent actionEvent) {

    }
}
