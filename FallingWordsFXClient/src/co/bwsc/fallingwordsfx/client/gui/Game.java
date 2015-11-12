package co.bwsc.fallingwordsfx.client.gui;

import co.bwsc.fallingwordsfx.client.ConfigManager;
import co.bwsc.fallingwordsfx.client.gui.driver.GameDriver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class Game extends Application {
    @FXML
    private HBox canvasBar;
    @FXML
    private Pane topPane;
    @FXML
    private HBox topBar;
    @FXML
    private HBox bottomBar;
    @FXML
    private Pane bottomPane;
    /**
     * A VBox which is the root of this window.
     */
    @FXML
    private VBox root;
    /**
     * A Pane for falling words.
     */
    @FXML
    private Pane canvasPane;
    /**
     * A Label to display local player's score.
     */
    @FXML
    private Label localScore;
    /**
     * A Label to display remote player's score.
     */
    @FXML
    private Label remoteScore;
    /**
     * A Label to display the game timer.
     */
    @FXML
    private Label timer;
    /**
     * A TextField to receive keyboard inputs.
     */
    @FXML
    private TextField input;

    public static void invoke(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        primaryStage.setTitle(ConfigManager.CFG.getApplicationName());
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();
    }

    /**
     * Handles the input TextField 's action.
     *
     * @param actionEvent - not used
     */
    @FXML
    private void handleInputAction(ActionEvent actionEvent) {

    }

    /**
     * Handles the Single Player Ready button.
     *
     * @param actionEvent - not used
     */
    @FXML
    private void handleSinglePlayer(ActionEvent actionEvent) {
        GameDriver singlePlayerDriver = new GameDriver(this, GameDriver.SINGLEPLAYER);
        singlePlayerDriver.initiate();
    }

    public Pane getCanvasPane() {
        return canvasPane;
    }

    public Label getLocalScore() {
        return localScore;
    }

    public Label getRemoteScore() {
        return remoteScore;
    }

    public TextField getInput() {
        return input;
    }

    public Label getTimer() {
        return timer;
    }

    /**
     * Handles the Multi Player Ready button.
     *
     * @param actionEvent - not used
     */
    @FXML
    private void handleMultiPlayer(ActionEvent actionEvent) {
        GameDriver multiPlayerDriver = new GameDriver(this, GameDriver.MULTIPLAYER);
        multiPlayerDriver.initiate();
    }
}
