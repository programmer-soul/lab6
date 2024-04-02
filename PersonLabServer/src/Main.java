import main.Commands;
import main.UDPDatagramServer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Программа Lab6 Server
 * @author Matvei Baranov
 * @version 3.0
 */
public class Main {
    public static Logger logger = Logger.getLogger("ServerLog");
    public static void main(String[] args) {
        try {
            Commands commands = new Commands();
            commands.execute("load","",null,false);
            var server = new UDPDatagramServer(new InetSocketAddress(InetAddress.getLocalHost(), 25555), commands,logger);
            System.out.println("Программа PersonLab Server. Ожидаем соединение.");
            server.setHook(commands.getSaveCommmand()::saveXML);//Автосохранение после каждой команды
            server.run();
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост");
            logger.log(Level.SEVERE,"Неизвестный хост", e);
        } catch (SocketException e) {
            System.out.println("Ошибка сокета");
            logger.log(Level.SEVERE,"Ошибка сокета", e);
        }
    }
}