package seriaf.poo.server;

import seriaf.poo.structs.Message;
import seriaf.poo.structs.PrivateMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author professor
 */
public class ServerPeer implements Runnable {

    private final Socket mSocket;
    private String username;
    private Server server;
    private ObjectOutputStream objectStream;

    public ServerPeer(Server server, Socket communicationSocket) {
        this.mSocket = communicationSocket;
        this.server = server;
        try {
            this.objectStream = new ObjectOutputStream(mSocket.getOutputStream());
        } catch(IOException e) {

        }
    }

    public void run() {
        Message mesaj;
        try {
            ObjectInputStream stream = new ObjectInputStream(mSocket.getInputStream());

            while (true) {
                mesaj = (Message) stream.readObject();
                username = mesaj.getSender(); // de fiecare data cand primesti mesaj se updateaza usernameul
                System.out.println(mesaj.toString().trim());
                if(mesaj instanceof PrivateMessage) {
                    sendMessage(mesaj);
                    server.dispatch(mesaj); // trimite mesaju la toata lumea
                } else {
                    server.dispatch(mesaj);
                }
            }
        } catch (EOFException ex) {
            // client disconnected gracefully so do nothing
            server.removeClient(this); // scoate clientu din lista
        } catch (IOException ex) {
            server.removeClient(this);
            System.err.println("Client connection reset: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("Unknown object received.");
        }
    }

    private void sendMessage(Message mesaj) {
        try {
            objectStream.writeObject(mesaj); // serializeaza mesajul si il trimite catre socket
        } catch(IOException e) {

        }
    }

    public Socket getmSocket() {
        return this.mSocket;
    }

    public String getUsername() { //this is pretty obv as well
        return this.username;
    }

    public ObjectOutputStream getObjectStream() {
        return this.objectStream;
    }
}