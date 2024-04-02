package command;
import main.Commands;
import main.Persons;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 * @author Matvei Baranov
 */

public class Save extends Command{
    private final Commands commands;
    public Save(Commands commands){
        super("save","сохранить коллекцию в файл (xml)");
        this.commands=commands;
    }

    @Override
    public boolean execute(String commandName,String parametr,boolean script) {
        commands.sendCommandAndReceiveResponse(commandName,parametr);
        return false;
    }
}
