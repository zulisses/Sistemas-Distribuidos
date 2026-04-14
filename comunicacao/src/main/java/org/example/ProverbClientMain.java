package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProverbClientMain {
    public static void main(String[] args) {
        try (Socket echoSocket = new Socket(ProverbServer.HOSTNAME, ProverbServer.PORT)) {
            try (PrintWriter serverOutput = new PrintWriter(echoSocket.getOutputStream(), true); // to server
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()))) { // from server
                String serverResponse;
                // the while is necessary to empty the input buffer (from server)
                while ((serverResponse = serverInput.readLine()) != null) {
                    if (ProverbProtocol.isEndGame(serverResponse)) {
                        JOptionPane.showMessageDialog(null, serverResponse, "CLIENT", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                    String userInput = JOptionPane.showInputDialog(null, serverResponse, "CLIENT", JOptionPane.INFORMATION_MESSAGE);
                    serverOutput.println(userInput); // send to server
                }
            }
        } catch (IOException ex) {
            System.out.printf("IO Error: %s\n", ex.getMessage());
        }

        System.exit(0);
    }
}
