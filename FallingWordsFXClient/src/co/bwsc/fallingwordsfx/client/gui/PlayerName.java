package co.bwsc.fallingwordsfx.client.gui;

import co.bwsc.fallingwordsfx.client.ConfigManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class PlayerName extends Application {

    @FXML
    private Parent root;

    @FXML
    private TextField nameInput;

    public static void invoke(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PlayerName.fxml"));
        primaryStage.setTitle(ConfigManager.CFG.getApplicationName());
        primaryStage.setScene(new Scene(root, 300, 75));
        primaryStage.show();

//        primaryStage.close();
        primaryStage.setOnCloseRequest(event -> System.out.println("Test"));
    }

    @FXML
    private void handleNameSubmission(ActionEvent actionEvent) {
        System.out.println("Test");
        ConfigManager.CFG.setUserName(nameInput.getText());
        try {
            System.out.println("Performing stage change to " + Game.class.getName() + "...");
            Stage rootWindow = (Stage) root.getScene().getWindow();
            rootWindow.hide();
            Application gameStage = new Game();
            gameStage.start(rootWindow);
        } catch (Exception e) {
            System.out.println("JavaFX stage change error!");
            e.printStackTrace();
        }
    }
}
