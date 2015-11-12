import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by WIT-PC on 11/11/2558.
 */
public class ServerP2P {
    /**
     * The instance of ServerP2P
     */
    private static ServerP2P serverP2P;
    /**
     * A Map of Client which connect to this server.
     */
//    private Map<String, ClientThread> clientList = new HashMap<String, ClientThread>();
    private ArrayList<ClientThread> socketList = new ArrayList<ClientThread>();
    /**
     * Server port.
     */
    private int portNumber = 12345;
    /**
     * Host name // not used
     */
    private String hostName = "";
    /**
     * The Socket which server is running
     */
    private ServerSocket serverSocket;
    /**
     * A thread that handle client
     */
    private Thread clientHandler;

    /**
     * Constructor to init the socket
     */
    private ServerP2P() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientHandler = new ClientHandler(serverSocket);

    }

    /**
     * singleton method to get the instance of the server.
     * I do this because it can have only one server with this port.
     *
     * @return a instanct of ServerP2P
     */
    public static ServerP2P getInstance() {
        if (serverP2P == null) {
            serverP2P = new ServerP2P();
        }
        return serverP2P;
    }

    /**
     * Main method
     *
     * @param arga input from console
     */
    public static void main(String[] arga) {
        ServerP2P serverP2P = new ServerP2P();
        serverP2P.start();
    }

    /**
     * Call to start the Handle client thread
     */
    public void start() {
        clientHandler.start();
    }

    /**
     * A Thread to handle a client.
     * When client is connected it will connect to this thread and then assign to a new thread.
     */
    private class ClientHandler extends Thread {
        /**
         * A SocketServer
         */
        private ServerSocket serverSocket;

        /**
         * Constructor to initialize the server socket.
         *
         * @param serverSocket The Socket of this server.
         */
        ClientHandler(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        /**
         * run
         */
        public void run() {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ClientThread clientThread = new ClientThread(socket);
                    clientThread.start();
//                    clientList.put(String.valueOf(socket.getLocalPort()),clientThread);
                    socketList.add(clientThread);
                    System.out.print("create Socket" + clientThread.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * A Thread that handle each of the client.
     */
    private class ClientThread extends Thread {
        /**
         * Socket of a client.
         */
        private Socket socket;
        /**
         * Use to send a message to a client
         */
        private PrintWriter out;
        /**
         * Use to relieve a message from a client
         */
        private BufferedReader in;
        /**
         * message from client
         */
        private String input;
        /**
         * Message to client
         */
        private String output = "";
        /**
         * Client name
         */
        private String name;

        /**
         * Constructor for initialize socket
         *
         * @param socket client socket
         */
        ClientThread(Socket socket) {
            this.socket = socket;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * Handle a message from client
         * [connect] if there is an available client waiting the server will exchange client id.
         * if not server will send error message and client need to wait
         *
         * @param s message from client
         * @return a out put to a client
         */
        private void processInput(String s) {
            String toClient = "";
            System.out.println(s);
            if (s.equals("[connect]")) {
                if (socketList.size() <= 1) {
                    out.println("[error]no client");
                    System.out.println("error");
                } else {
                    ClientThread clientThread = socketList.get(0);
                    out.println("[connected][ip=" + clientThread.socket.getInetAddress() + "][client]");
                    clientThread.out.print("[connected][ip=" + socket.getInetAddress() + "][server]");
                    socketList.remove(this);
                    socketList.remove(clientThread);
                    try {
                        clientThread.socket.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("in connect");
                }
            }
        }

        /**
         * run
         */
        public void run() {
            try {
                while ((input = in.readLine()) != null) {
                    processInput(input);
                    if (output.equals("<end>"))
                        socket.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Get the name of the client
         *
         * @return the client name
         */
        public String getClientName() {
            if (name == null) {
                out.println("[get]name");
            }
            return name;
        }
    }
}
