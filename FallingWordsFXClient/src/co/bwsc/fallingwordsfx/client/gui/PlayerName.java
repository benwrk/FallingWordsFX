package co.bwsc.fallingwordsfx.client.gui;

import co.bwsc.fallingwordsfx.client.ConfigManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayerName extends Application {

    private Stage primaryStage;

    private Parent root;

    @FXML
    private TextField nameInput;

    public static void invoke(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("PlayerName.fxml"));
        primaryStage.setTitle(ConfigManager.CFG.getApplicationName());
        primaryStage.setScene(new Scene(root, 300, 75));
        primaryStage.show();

//        primaryStage.close();
        primaryStage.setOnCloseRequest(event -> System.out.println("Test"));
    }

    @FXML
    private void handleNameSubmission() {
        ConfigManager.CFG.setUserName(nameInput.getText());
        primaryStage.hide();
        
    }
}
