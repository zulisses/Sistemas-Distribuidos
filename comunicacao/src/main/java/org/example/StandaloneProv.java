package org.example;

import javax.swing.*;

public class StandaloneProv {
    public static void main(String[] args) {
        ProverbProtocol protocol = new ProverbProtocol();
        String userInput = null;

        while (true) {
            String protocolResponse = protocol.processInput(userInput);
            if(ProverbProtocol.isEndGame(protocolResponse)) {
                JOptionPane.showMessageDialog(null, protocolResponse);
                break;
            }
            userInput = JOptionPane.showInputDialog(protocolResponse);
        }

        System.exit(0);
    }
}
