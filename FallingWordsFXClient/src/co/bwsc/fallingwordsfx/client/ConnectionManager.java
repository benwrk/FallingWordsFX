package co.bwsc.fallingwordsfx.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class ConnectionManager {
    public static final ConnectionType SERVER = ConnectionType.SERVER;
    public static final ConnectionType CLIENT = ConnectionType.CLIENT;

    public static void initiateConnectionToServer() {
        try {
            Socket socket = new Socket(ConfigManager.CFG.getServerAddress(), 11123);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum ConnectionType {
        SERVER, CLIENT
    }
}
