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

public class Game extends Application {

    public static void invoke(String[] args) {
        launch(args);
    }

    @FXML
    private Parent root;

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
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @FXML
    public void handleInputAction(ActionEvent actionEvent) {

    }
}
