package co.bwsc.fallingwordsfx.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Benjapol Worakan
 * @version 2015.11.13
 */
public class Server implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private Queue<Socket> servers;
    private Queue<Socket> clients;

    public Server(int port) {
        if (port >= 0 && port < 65536) {
            this.port = port;
        } else {
            throw new IllegalArgumentException("Invalid port range. (Valid only 0-65535)");
        }
        servers = new PriorityQueue<>();
        clients = new PriorityQueue<>();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started listening on port " + port + ".");
        } catch (IOException e) {
            System.out.println("Unable to start listening on port " + port + "!");
            e.printStackTrace();
        }
        Thread dispatcher = new Thread(() -> {
            System.out.println("Dispatcher running.");
            while (true) {
                if (!servers.isEmpty() && !clients.isEmpty()) {
                    Socket server = servers.poll();
                    while (server.isClosed() && servers.isEmpty()) {
                        server = servers.poll();
                    }
                    Socket client = clients.poll();
                    while (client.isClosed() && clients.isEmpty()) {
                        client = clients.poll();
                    }
                    if (server == null || client == null) {
                        continue;
                    }
                    String serverAddress = server.getInetAddress().toString();
                    String clientAddress = client.getInetAddress().toString();
                    ObjectOutputStream serverOutputStream;
                    ObjectOutputStream clientOutputStream;
                    try {
                        serverOutputStream = new ObjectOutputStream(server.getOutputStream());
                        clientOutputStream = new ObjectOutputStream(client.getOutputStream());
                        serverOutputStream.writeObject(clientAddress);
                        clientOutputStream.writeObject(serverAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        });
        dispatcher.start();
        System.out.println("Connection handlers running.");
        while (true) {
            try {
                Socket socketConnection = serverSocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(socketConnection.getOutputStream());

                if (servers.size() <= clients.size()) {
                    System.out.println("Connection from " + socketConnection.getInetAddress() + ", classified as Server.");
                    oos.writeObject("Server");
                    servers.add(socketConnection);
                } else {
                    System.out.println("Connection from " + socketConnection.getInetAddress() + ", classified as Client.");
                    oos.writeObject("Client");
                    clients.add(socketConnection);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
