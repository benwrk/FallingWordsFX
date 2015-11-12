package co.bwsc.fallingwordsfx.server;

import java.io.*;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class ConfigManager implements Serializable {

    public static final ConfigManager CFG = loadConfiguration();

    private static final String CONFIG_FILE = "./storage/dynamic/serverConfig.cfg";

    public static void initialize() {
        saveConfiguration();
    }

    public static ConfigManager loadConfiguration() {
        System.out.println("Loading configuration file...");
        ConfigManager configManager;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_FILE));
            configManager = (ConfigManager) ois.readObject();
        } catch (IOException e) {
            System.out.println("Configuration file not found!");
            e.printStackTrace();
            configManager = createDefaultConfiguration();
        } catch (Exception e) {
            System.out.println("Configuration file could not be loaded");
            e.printStackTrace();
            configManager = createDefaultConfiguration();
        }
        return configManager;
    }

    public static ConfigManager createDefaultConfiguration() {
        System.out.println("Creating a new one with default settings...");

        // Default settings /////
        String applicationName = "FallingWords FX";
        String serverURL = "127.0.0.1";
        int serverPort = 11678;
        ////////////////////////

        return new ConfigManager(applicationName, serverURL, serverPort);
    }

    private static void saveConfigManager(ConfigManager cfg) {
        System.out.println("Saving configuration to file...");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE));
            oos.writeObject(cfg);
            System.out.println("Saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file!");
            e.printStackTrace();
            File file = new File(CONFIG_FILE);
            file.getParentFile().mkdirs();

            System.out.println("Trying again...");
            saveConfiguration();
        }
    }

    public static void saveConfiguration() {
        saveConfigManager(CFG);
    }

    private String applicationName;
    private String serverURL;
    private int serverPort;

    private ConfigManager(String applicationName, String serverURL, int serverPort) {
        this.applicationName = applicationName;
        this.serverURL = serverURL;
        this.serverPort = serverPort;
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

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String toString() {
        return (
                "[Configuration Details]" + "\n" +
                        "Application Name: " + applicationName + "\n" +
                        "Server URL: " + serverURL + "\n" +
                        "Server Port: " + serverPort + "\n"
        );
    }

}
