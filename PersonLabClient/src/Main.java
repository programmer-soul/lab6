import main.UDPDatagramClient;
import main.Commands;
import java.util.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Программа Lab6 Client
 * @author Matvei Baranov
 * @version 3.0
 */
public class Main {
    public static Logger logger = Logger.getLogger("ClientLog");
    public static void main(String[] args) {
        System.out.println("Программа Lab6. Введите help для вывода справки!");
        Scanner scanner = new Scanner(System.in);
        Commands commands= new Commands();
        String comstr="";
        boolean exit=false;
        while (!exit) {
            try {
                UDPDatagramClient client = new UDPDatagramClient(new InetSocketAddress(InetAddress.getLocalHost(), 25555),logger);
                comstr = scanner.nextLine();
                exit=commands.execute(client,comstr,false);
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Невозможно подключиться к серверу.", e);
                System.out.println("Невозможно подключиться к серверу!");
            }
        }
    }
}