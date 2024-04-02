package command;
import main.Commands;
import main.Person;
import main.Persons;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
/**
 * Команда 'load'. Загружает коллекцию из файла.
 * @author Matvei Baranov
 */
public class Load extends Command{
    private final Commands commands;

    public Load(Commands commands){
        super("load","загрузить коллекцию из файла (xml)");
        this.commands=commands;
    }

    @Override
    public boolean execute(String commandName,String parametr,boolean script) {
        commands.sendCommandAndReceiveResponse(commandName,parametr);
        return false;
    }
}
