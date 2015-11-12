package co.bwsc.fallingwordsfx.client;

import java.io.*;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class ConfigManager implements Serializable {

    public static final ConfigManager CFG = loadConfiguration();

    private static final String CONFIG_FILE = "./storage/localConfig.cfg";

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
        int serverPort = 11123;
        boolean firstLaunch = true;
        String userName = "Local Player";
        String dictionayFile = "./storage/dictionary.txt";
        ////////////////////////

        return new ConfigManager(applicationName, serverURL, serverPort, firstLaunch, userName, dictionayFile);
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
    private boolean firstLaunch;
    private String userName;
    private String dictionaryFile;

    private ConfigManager(String applicationName, String serverURL, int serverPort, boolean firstLaunch, String userName, String dictionaryFile) {
        this.applicationName = applicationName;
        this.serverURL = serverURL;
        this.serverPort = serverPort;
        this.firstLaunch = firstLaunch;
        this.userName = userName;
        this.dictionaryFile = dictionaryFile;
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

    public boolean isFirstLaunch() {
        return firstLaunch;
    }

    public String getUserName() {
        return userName;
    }

    public String getDictionaryFile() {
        return dictionaryFile;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        this.firstLaunch = firstLaunch;
    }

    public void setUserName(String userName) {
        System.out.println("Username configuration updated.");
        this.userName = userName;
        saveConfiguration();
    }

    public void setDictionaryFile(String dictionaryFile) {
        this.dictionaryFile = dictionaryFile;
    }

    public String toString() {
        return (
                "[Configuration Details]" + "\n" +
                        "Application Name: " + applicationName + "\n" +
                        "Server URL: " + serverURL + "\n" +
                        "Server Port: " + serverPort + "\n" +
                        "First Launch: " + firstLaunch + "\n" +
                        "Player Name: " + userName + "\n"
        );
    }

}
