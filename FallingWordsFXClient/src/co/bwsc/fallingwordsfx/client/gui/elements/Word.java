package co.bwsc.fallingwordsfx.client.gui.elements;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class Word extends Label {

    public Word(String word) {
        setFont(new Font("System Bold", 15));
        setTextFill(Color.WHITE);
        setText(word);
        setLayoutY(-20);
//        this.xPosition = Math.random() *
        setLayoutX(Math.random() * 800.0);
    }

    public String toString() {
        return "[Word]: " + getText() + " at (" + getLayoutX() + ", " + getLayoutY() + ").";
    }
}
