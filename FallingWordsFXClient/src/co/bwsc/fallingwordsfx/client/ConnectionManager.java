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
    private static ConnectionType connectionType;
    private static String serverIP;

    public static ConnectionType getConnectionType() {
        return connectionType;
    }

    public static String getServerIP() {
        return serverIP;
    }

    public static void initiateConnectionToServer() {
        System.out.println("Initiating connection to server...");
        try {
            Socket socket = new Socket(ConfigManager.CFG.getServerAddress(), 11123);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


            while (!socket.isClosed()) {
                Object o = ois.readObject();
                if (o == null) {
                    break;
                }
                String response = "";

                if (o != null && o instanceof String) {
                    response = (String) o;
                }

                if (response.startsWith("/")) {
                    serverIP = response.substring(1);
                    System.out.println("Server IP set to " + serverIP + ".");
                } else {
                    if (response.equals("Client")) {
                        System.out.println("ConnectionType set to " + CLIENT + " mode.");
                        connectionType = CLIENT;
                    } else if (response.equals("Server")) {
                        System.out.println("ConnectionType set to " + SERVER + " mode.");
                        connectionType = SERVER;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private enum ConnectionType {
        SERVER, CLIENT
    }
}
