package seriaf.poo.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author professor
 */
public class TextClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9000;

    public static void main(String[] args) {
        Scanner keyboardScanner = new Scanner(System.in);

        try {
            Socket communicationSocket = new Socket(HOST, PORT);

            String sender = keyboardScanner.nextLine();
            ClientPeer peer = new ClientPeer(sender, communicationSocket);
            (new Thread(peer)).start(); // am facut un nou thread pt peer caruia i-am dat start
            while (true) {
                String command = keyboardScanner.nextLine().trim();

                if (command.equals("/q")) {
                    communicationSocket.close();
                    break;
                } else if (command.matches("/w\\s+\\w+\\s+.+")) {
                    String[] messageParts = command.split("\\s+", 3);
                    peer.sendMessage(messageParts[1], messageParts[2]);
                } else {
                    peer.sendMessage(command);
                }
            }
        } catch (IOException ex) {

        }
    }
}
