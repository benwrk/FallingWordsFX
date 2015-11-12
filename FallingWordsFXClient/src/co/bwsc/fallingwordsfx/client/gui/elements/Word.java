package co.bwsc.fallingwordsfx.client.gui.elements;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class Word extends Label {
    private String word;
    private double xPosition;
    private double yPosition;

    public Word(String word) {
        setFont(new Font("System Bold", 12));
        this.yPosition = -30;
        this.word = word;
    }
}
