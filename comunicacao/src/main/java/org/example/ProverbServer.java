package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProverbServer implements Runnable {
    public static final int PORT = 4444;
    public static final String HOSTNAME = "127.0.0.1";

    private final Socket clientSocket;

    public ProverbServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        ProverbProtocol protocol = new ProverbProtocol();

        try (clientSocket) { // will auto-close the clientSocket
            String hostname = clientSocket.getInetAddress().getHostName();
            int port = clientSocket.getPort();
            System.out.printf("[%s:%d] connected!\n", hostname, port);

            // clientInput -> stream of data FROM the client
            // serverOutput -> stream of data TO the client

            try (BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter serverOutput = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String userInput = null;
                String serverResponse;
                while ((serverResponse = protocol.processInput(userInput)) != null) {
                    serverOutput.println(serverResponse); //send to a client socket
                    if (ProverbProtocol.isEndGame(serverResponse))
                        break;
                    userInput = clientInput.readLine(); // wait for response
                    System.out.printf("# Receive message from client [%s:%d]: %s\n", hostname, port, userInput);
                }
            }
        } catch (IOException e) {
            System.out.printf("Server IO Error: %s\n", e.getMessage());
        }
    }
}
