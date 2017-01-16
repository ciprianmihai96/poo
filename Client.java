package colocviu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lydya0103
 */
public class Client{
    private static final int TCP_PORT = 9000;
    private static final String PATH = "Vectors.txt";
    private static Socket socket;
    private int[] v1;
    private int[] v2;
    private int result;
    
    public Client(Socket _socket)
    {
        socket = _socket;
    }
    
    public static void main(String[] args)
    {
        Socket socket = null;
        try {
            socket = new Socket("localhost",TCP_PORT);
        } catch (IOException ex) {
        }
        Client client = new Client(socket);
        client.readVectors();
        client.toServer();
        System.out.println(client.getResult());                
    }
    
    private void readVectors() 
    {
        try {
            BufferedReader  br = new BufferedReader(new FileReader(PATH));
            if(br.ready() == true)
            {
                String[] temp;
                temp  = br.readLine().split(" ");
                v1 = new int[temp.length];
                v2 = new int[temp.length];

                for(int i = 0; i < temp.length; i++)                    
                    v1[i] = Integer.parseInt(temp[i]);

                temp  = br.readLine().split(" ");
                for(int i = 0; i < temp.length; i++)                    
                    v2[i] = Integer.parseInt(temp[i]);
            }     
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private void toServer()
    {
        Vectors vectors = new Vectors(v1,v2);
        
        ObjectOutputStream outToServer;        
        try {
            outToServer  = new ObjectOutputStream(socket.getOutputStream());
            outToServer.writeObject(vectors);
        } catch (IOException ex) {
        }
        
        ObjectInputStream input;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            result = (int) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
        }
    }
    
    public int getResult()
    {
        return result;
    }
}
