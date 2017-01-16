package colocviu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Lydya0103
 */
public class ServerPeer extends Thread{
    private final Socket socket;
    private final int ThreadNumbers;
    private final Server server;
    
    private ObjectInputStream input;
    private ObjectOutputStream outToClient;   
    
    public ServerPeer(Socket _socket, int _ThreadNumbers, Server _server)
    {
        ThreadNumbers = _ThreadNumbers;
        socket = _socket;
        server = _server;
    }
    
    @Override
    public void run()
    {
        int result = 0;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            Vectors vectors = (Vectors) input.readObject();
            if(vectors.getV1() == null && vectors.getV2() == null)
                server.closeServer();
            else
            {
                ScalarProduct scalarProduct = new ScalarProduct(vectors.getV1(), vectors.getV2());
                result = scalarProduct.getScalarProduct(ThreadNumbers);  
            }
        } catch (IOException | ClassNotFoundException ex) {
        }
        
        try {
            outToClient  = new ObjectOutputStream(socket.getOutputStream());
            outToClient.writeObject(result);
        } catch (IOException ex) {
        }
    }
}
