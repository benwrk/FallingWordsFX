package co.bwsc.fallingwordsfx.client;

import java.io.*;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class ConfigManager {

    public static final ConfigManager CFG = loadConfiguration();

    public static ConfigManager loadConfiguration() {
        ConfigManager configManager = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./localConfig.dat"));
            configManager = (ConfigManager) ois.readObject();
        } catch (IOException e) {
            System.out.println("Configuration file not found!");
            e.printStackTrace();
            System.out.println("Creating a new one with default settings...");

            // Default settings /////
            String applicationName = "FallingWords FX";
            String serverURL = "127.0.0.1";
            int serverPort = 11123;
            boolean firstLaunch = true;
            String playerName = "Local Player";
            ////////////////////////


            configManager = new ConfigManager(applicationName, serverURL, serverPort, firstLaunch, playerName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return configManager;
    }

    public static void saveConfiguration() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./localConfig.cfg"));
            oos.writeObject(CFG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String applicationName;
    private String serverURL;
    private int serverPort;
    private boolean firstLaunch;
    private String playerName;

    private ConfigManager(String applicationName, String serverURL, int serverPort, boolean firstLaunch, String playerName) {
        this.applicationName = applicationName;
        this.serverURL = serverURL;
        this.serverPort = serverPort;
        this.firstLaunch = firstLaunch;
        this.playerName = playerName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getServerURL() {
        return serverURL;
    }

    public int getServerPort() {
        return serverPort;
    }

    public boolean getFirstLaunch() {
        return firstLaunch;
    }

    public String getPlayerName() {
        return playerName;
    }
}