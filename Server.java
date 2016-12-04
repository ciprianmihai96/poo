package seriaf.poo.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import seriaf.poo.server.config.ServerConfig;
import seriaf.poo.structs.Message;
import seriaf.poo.structs.PrivateMessage;

/**
 *
 * @author professor
 */
public class Server {

    public ArrayList<ServerPeer> clientList = new ArrayList<>();
    private ServerSocket serverSocket;
    private final int MAX_CLIENTS;

    public Server(int TCP_PORT, int MAX_CLIENTS) { // constructor care instantiaza obiectul serversocket
        try {
            this.serverSocket = new ServerSocket(TCP_PORT);
        } catch(IOException e) {

        }
        this.MAX_CLIENTS = MAX_CLIENTS;
    }

    public static void main(String[] args) {
        try {
            ServerConfig config = new ServerConfig();
            Server server = new Server(config.getTcpPort(), config.getMaxClients());
            server.listen(server);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void listen(Server server) throws IOException {
        while(true) { // atata timp cat traieste serveru primeste conexiuni noi
            if(clientList.size() <= this.MAX_CLIENTS) { // atata timp cat mai e loc de conexiuni
                ServerPeer peer = new ServerPeer(server, serverSocket.accept());
                clientList.add(peer); // tinem minte fiecare client conectat la server ( socketul lui )
                (new Thread(peer)).start(); // pornim thread pt serverpeer in loc sa-i dam numai run
            }
        }
    }

    public synchronized void dispatch(Message mesaj) {
        for(ServerPeer peer : clientList) {
            if(mesaj instanceof PrivateMessage) {
                if(peer.getUsername().equals(((PrivateMessage) mesaj).getRecipient())) {
                    try {
                        ObjectOutputStream objectStream = peer.getObjectStream();
                        objectStream.writeObject(mesaj);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    ObjectOutputStream objectStream = peer.getObjectStream();
                    objectStream.writeObject(mesaj);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public synchronized void removeClient(ServerPeer serverPeer) {
        clientList.remove(serverPeer);
    }
}
