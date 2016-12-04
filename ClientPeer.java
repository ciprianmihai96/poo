package seriaf.poo.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import seriaf.poo.structs.Message;
import seriaf.poo.structs.PrivateMessage;

/**
 *
 * @author professor
 */
public class ClientPeer implements Runnable {

    private final ObjectOutputStream mObjectStream;
    private final String mSender;
    private final Socket socket; // am adaugat socket aici ca sa-l pot folosi mai departe


    public ClientPeer(String sender, Socket communicationSocket) throws IOException {
        mSender = sender;
        mObjectStream = new ObjectOutputStream(communicationSocket.getOutputStream());
        this.socket = communicationSocket; // aici trimit socketu sa-l am
    }

    public void sendMessage(String message) throws IOException {
        mObjectStream.writeObject(new Message(mSender, message));
    }

    public void sendMessage(String recipient, String message) throws IOException {
        mObjectStream.writeObject(new PrivateMessage(recipient, mSender, message));
    }

    public void run() { // metoda pentru thread
        try {
            ObjectInputStream stream = new ObjectInputStream(this.socket.getInputStream());
            while (true) {
                System.out.println(stream.readObject().toString().trim());
            }
        } catch(IOException | ClassNotFoundException e) {

        }
    }
}