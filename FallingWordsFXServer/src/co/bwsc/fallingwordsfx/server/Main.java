package co.bwsc.fallingwordsfx.server;

/**
 * @author Benjapol Worakan
 * @version 2015.11.13
 */
public class Main {
    public static void main(String[] args) {
        ConfigManager.initialize();
        System.out.println(ConfigManager.CFG.toString());
        Server server = new Server(ConfigManager.CFG.getServerPort());
        Thread serverThread = new Thread(server);
        serverThread.run();
    }
}
