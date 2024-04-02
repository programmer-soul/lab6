package command;
import main.Person;
import main.Commands;
import main.ResponseManager;
import main.ResponsePerson;
/**
 * Команда 'update'. Обновить значение элемента коллекции, id которого равен заданному.
 * @author Matvei Baranov
 */
public class Update extends Command{
    private final Commands commands;
    public Update(Commands commands){
        super("update {id} {element}","обновить значение элемента коллекции, id которого равен заданному");
        this.commands=commands;
    }
    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        return commands.update(person.toPerson());
    }
}
