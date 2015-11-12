package co.bwsc.fallingwordsfx.client;

import java.io.*;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class ConfigManager implements Serializable {

    private static final String CONFIG_FILE = "./storage/dynamic/localConfig.cfg";
    public static final ConfigManager CFG = loadConfiguration();
    private String applicationName;
    private String serverAddress;
    private int serverPort;
    private boolean firstLaunch;
    private String userName;
    private String dictionaryFile;
    private String positiveSoundFile;
    private int localPort;

    private ConfigManager(String applicationName, String serverAddress, int serverPort, boolean firstLaunch, String userName, String dictionaryFile, String positiveSoundFile, int localPort) {
        this.applicationName = applicationName;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.firstLaunch = firstLaunch;
        this.userName = userName;
        this.dictionaryFile = dictionaryFile;
        this.positiveSoundFile = positiveSoundFile;
        this.localPort = localPort;
    }

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
        String serverAddress = "127.0.0.1";
        int serverPort = 11678;
        boolean firstLaunch = true;
        String userName = "Local Player";
        String dictionaryFile = "./storage/dictionary.txt";
        String positiveSoundFile = "./storage/positive.wav";
        int localPort = 11123;
        ////////////////////////

        return new ConfigManager(applicationName, serverAddress, serverPort, firstLaunch, userName, dictionaryFile, positiveSoundFile, localPort);
    }

    private static void saveConfigManager(ConfigManager cfg) {
        System.out.println("Saving configuration to file...");
        System.out.println(cfg.toString());
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

    public String getApplicationName() {
        return applicationName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public boolean isFirstLaunch() {
        return firstLaunch;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        this.firstLaunch = firstLaunch;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        System.out.println("Username configuration updated.");
        this.userName = userName;
        saveConfiguration();
    }

    public String getDictionaryFile() {
        return dictionaryFile;
    }

    public void setDictionaryFile(String dictionaryFile) {
        this.dictionaryFile = dictionaryFile;
    }

    public String getPositiveSoundFile() {
        return positiveSoundFile;
    }

    public int getLocalPort() {
        return localPort;
    }

    public String toString() {
        return (
                "[Configuration Details]" + "\n" +
                        " Application Name: " + applicationName + "\n" +
                        " Server URL: " + serverAddress + "\n" +
                        " Server Port: " + serverPort + "\n" +
                        " First Launch: " + firstLaunch + "\n" +
                        " Username: " + userName + "\n" +
                        " Dictionary File: " + dictionaryFile + "\n" +
                        " Positive Sound File: " + positiveSoundFile + "\n" +
                        " Local Port: " + localPort + "\n" +
                        "[End of Configuration]" + "\n"
        );
    }

}
