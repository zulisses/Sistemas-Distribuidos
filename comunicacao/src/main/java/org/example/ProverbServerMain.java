package org.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ProverbServerMain {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName(ProverbServer.HOSTNAME);
            try (ServerSocket serverSocket = new ServerSocket(ProverbServer.PORT, 50, address)) {
                while (true) {
                    System.out.printf("Waiting for connection on port %d\n", ProverbServer.PORT);
                    new ProverbServer(serverSocket.accept()).run();
                }
            }
        } catch (UnknownHostException e) {
            System.out.printf("Invalid Address: %s\n", ProverbServer.HOSTNAME);
        } catch (IOException ex) {
            System.out.printf("IO Error: %s\n", ex.getMessage());
        }

        System.exit(0);
    }
}
