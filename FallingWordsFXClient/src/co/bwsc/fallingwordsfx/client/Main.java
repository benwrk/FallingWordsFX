package co.bwsc.fallingwordsfx.client;

import co.bwsc.fallingwordsfx.client.gui.Login;

import java.util.logging.Logger;

/**
 * @author Benjapol Worakan
 * @version 2015.11.07
 */
public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Login.invoke(args);
    }
}
