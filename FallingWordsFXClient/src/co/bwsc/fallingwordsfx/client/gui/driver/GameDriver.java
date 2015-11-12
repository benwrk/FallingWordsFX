package co.bwsc.fallingwordsfx.client.gui.driver;

import co.bwsc.fallingwordsfx.client.ConfigManager;
import co.bwsc.fallingwordsfx.client.ConnectionManager;
import co.bwsc.fallingwordsfx.client.DictionaryManager;
import co.bwsc.fallingwordsfx.client.gui.Game;
import co.bwsc.fallingwordsfx.client.gui.elements.Word;
import javafx.application.Platform;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class GameDriver {
    public static final GameMode SINGLEPLAYER = GameMode.SINGLEPLAYER, MULTIPLAYER = GameMode.MULTIPLAYER;
    private static Timer timer = new Timer();
    private static String opponentName = "";
    private Thread streamFromRemote;
    private TimerTask scorer;
    private TimerTask updater;
    private TimerTask timeCounter;
    boolean gameOver;
    private Game game;
    private GameMode gameMode;
    private ArrayList<Word> wordsList;
    private double fallSpeed;
    private int fallInterval;
    private int gameTime;
    private int wordProcessed;
    private int interval;
    private double localScore;

    public GameDriver(Game game, GameMode gameMode) {
        this.game = game;
        this.gameMode = gameMode;
        this.fallSpeed = 1.0;
        this.fallInterval = 90;
    }

    public void initiate() {
        wordsList = new ArrayList<>();

        ArrayList<String> dictionary = DictionaryManager.getInstance().getDictionary();
        for (int i = 0; i < 100; i++) {
            Word word = new Word(dictionary.get((int) (Math.random() * DictionaryManager.getInstance().getSize())));
            wordsList.add(word);
        }

        System.out.println(wordsList.toString());

        game.getInput().setDisable(false);

        game.getCanvasPane().getChildren().addAll(wordsList);
        game.getCanvasPane().requestLayout();

        switch (gameMode) {
            case SINGLEPLAYER:
                initiateSinglePlayer();
                break;
            case MULTIPLAYER:
                initiateMultiPlayer();
                break;
        }
    }

    private void initiateMultiPlayer() {
        ConnectionManager.initiateConnectionToServer();
        streamFromRemote = new Thread(() -> {

        });
    }

    private void initiateSinglePlayer() {
        game.getRemoteScore().setText("");

        launchSinglePlayerThreads();
    }

    private void setInputField(String text) {
        Platform.runLater(() -> {
            game.getInput().setText(text);
        });
    }

    private void setLocalScore(String score) {
        Platform.runLater(() -> {
            game.getLocalScore().setText(ConfigManager.CFG.getUserName() + ": " + score);
        });
    }

    private void removeWord(Word word) {
        Platform.runLater(() -> {
            game.getCanvasPane().getChildren().remove(word);
        });
    }

    private void setTimeLabel(int time) {
        Platform.runLater(() -> {
            String minute = String.format("%02d", time / 60);
            String second = String.format("%02d", time % 60);
            game.getTimer().setText(minute + " : " + second);
        });
    }

    private void setRemoteScore(String score) {
        Platform.runLater(() -> {
            game.getLocalScore().setText(opponentName + ": " + score);
        });
    }

    private void launchSinglePlayerThreads() {
        gameOver = false;
        wordProcessed = 0;
        interval = fallInterval;
        updater = new TimerTask() {
            @Override
            public void run() {
                if (interval <= 0) {
                    wordProcessed++;
                    interval = fallInterval;
                } else {
                    interval--;
                }
                for (int i = 0; i < wordProcessed && i < wordsList.size(); i++) {
                    wordsList.get(i).setLayoutY(wordsList.get(i).getLayoutY() + fallSpeed);
                }
            }
        };

        localScore = 0.0;
        scorer = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < wordsList.size(); i++) {
                    Word word = wordsList.get(i);
                    if (word.getText().equalsIgnoreCase(game.getInput().getText())) {
                        removeWord(word);
                        GameSound.POSITIVE.play();
                        wordsList.remove(i);
                        setLocalScore(String.format("%.0f", ++localScore));
                        setInputField("");
                        continue;
                    }
                }
            }
        };

        gameTime = 30;
        timeCounter = new TimerTask() {
            @Override
            public void run() {
                gameTime--;
                setTimeLabel(gameTime);
                if (gameTime <= 0) {
                    this.cancel();
                    gameOver();
                }
            }
        };

        timer.scheduleAtFixedRate(updater, 0, 20);
        timer.scheduleAtFixedRate(scorer, 0, 20);
        timer.scheduleAtFixedRate(timeCounter, 0, 1000);
    }

    private void gameOver() {
        updater.cancel();
        scorer.cancel();
        //TODO: SHOW POPUP
        System.out.println("Game Over.");
    }


    //    try {
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s));
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.start();
//        } catch (Exception ex) {
//            System.out.println("Error with playing sound.");
//            ex.printStackTrace();
//        }
    private enum GameSound {
        POSITIVE(ConfigManager.CFG.getPositiveSoundFile());
        private String soundFile;
        private Clip soundClip;

        GameSound(String soundFile) {
            this.soundFile = soundFile;
            loadSound();
        }

        private void loadSound() {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundFile));
                soundClip = AudioSystem.getClip();
                soundClip.open(ais);
            } catch (UnsupportedAudioFileException e) {
                System.out.println("Sound track unsupported!");
                e.printStackTrace();
            } catch (IOException | LineUnavailableException e) {
                System.out.println("Error reading sound file!");
                e.printStackTrace();
            }
        }

        public void play() {
            soundClip.stop();
            soundClip.setFramePosition(0);
            soundClip.start();
        }
    }

    private enum GameMode {
        SINGLEPLAYER, MULTIPLAYER;
    }
}
