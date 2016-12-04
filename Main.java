package seriaf.poo.structs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import seriaf.poo.server.config.ServerConfig;
import seriaf.poo.server.exceptions.InvalidFormatException;
import seriaf.poo.server.exceptions.MissingKeyException;
import seriaf.poo.server.exceptions.UnknownKeyException;

/**
 *
 * @author professor
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Message m1 = new Message("John", "Hi!");
        Message m2 = new Message("Mary", "Hello!");
        Message m3 = new Message("John", "Bye!");
        Message m4 = new Message("Mary", "Awww...");

        System.out.println(m1.toString());
        System.out.println(m2);
        System.out.printf("%s\n", m3);
        System.out.println(m4);

        PrivateMessage pm1 = new PrivateMessage("Mary", "John", "Hi!");
        PrivateMessage pm2 = new PrivateMessage("John", "Mary", "Leave me alone, I have a restraining order!");
        PrivateMessage pm3 = new PrivateMessage("Mary", "John", "Aww, come on, don't be like that!");

        System.out.println(pm1);
        System.out.println("To: " + pm1.getRecipient());
        System.out.println(pm2);
        System.out.println("To: " + pm2.getRecipient());
        System.out.println(pm3);
        System.out.println("To: " + pm3.getRecipient());

        try {
            ServerConfig config = new ServerConfig();
            System.out.println("Tcp port: " + config.getTcpPort());
            System.out.println("Max clients: " + config.getMaxClients());
        } catch (IOException ex) {
            System.err.println("Exceptie de tip IO: " + ex.getMessage());
        } catch (InvalidFormatException ex) {
            System.err.println("Format invalid: " + ex.getMessage());
        } catch (UnknownKeyException | MissingKeyException ex) {
            System.err.println(ex.getMessage());
        }

    }

}
