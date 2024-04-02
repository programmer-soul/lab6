package command;
import main.Person;
import main.Commands;
import main.ResponseManager;
import main.ResponsePerson;

/**
 * Команда 'insert'. Добавить новый элемент с заданным ключом.
 * @author Matvei Baranov
 */
public class Insert extends Command{
    private final Commands commands;
    public Insert(Commands commands){
        super("insert {id} {element}","добавить новый элемент с заданным ключом");
        this.commands=commands;
    }
    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        return commands.insert(person.toPerson());
    }
}
