package colocviu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Lydya0103
 */
public class Server {
    private static final int TCP_PORT = 9000;
    private static int ThreadNumber;    
    private ServerSocket ss; 
    private boolean CloseServer;
    
    public Server()
    {
        try {
            ss = new ServerSocket(TCP_PORT);
        } catch (IOException ex) {
        }
        CloseServer = false;
    }
    
    public static void main(String[] args) {
        System.out.println("Introduceti nr thread-uri pentru lucru:");
        Scanner sc = new Scanner(System.in);
        ThreadNumber = sc.nextInt();
        
        Server server = new Server();
        server.listen();
    }
    
    private void listen(){
        while(CloseServer == false)
        { 
            Socket s;
            try {
                s = ss.accept();
                ServerPeer sp = new ServerPeer(s, ThreadNumber, this);
                sp.start();
            } catch (IOException ex) {
                break;
            }            
        }
    }
    
    public void closeServer() {
        
        CloseServer = true;
    }
}
