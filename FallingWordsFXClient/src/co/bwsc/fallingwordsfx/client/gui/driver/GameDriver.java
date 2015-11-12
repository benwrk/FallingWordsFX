package co.bwsc.fallingwordsfx.client.gui.driver;

import co.bwsc.fallingwordsfx.client.DictionaryManager;
import co.bwsc.fallingwordsfx.client.gui.elements.Word;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class GameDriver {
    private Application game;
    private GameMode gameMode;

    public GameDriver(Application game, GameMode gameMode) {
        this.game = game;
        this.gameMode = gameMode;
    }
    public static void initiateSinglePlayer(Application application) {
        LinkedList<Word> wordsList = new LinkedList<>();

        ArrayList<String> dictionary = DictionaryManager.getInstance().getDictionary();
        for (int i = 0; i < 100; i++) {
            Word word = new Word(dictionary.get((int) Math.random() * DictionaryManager.getInstance().getSize()));
            wordsList.add(word);
        }
    }
}
