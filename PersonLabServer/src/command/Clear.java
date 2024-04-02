package command;
import main.Persons;
import main.ResponseManager;
import main.ResponsePerson;
/**
 * Команда 'clear'. Очищает коллекцию.
 * @author Matvei Baranov
 */

public class Clear extends Command{
    private final Persons persons;
    public Clear(Persons persons) {
        super("clear", "очистить коллекцию");
        this.persons = persons;
    }
    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        ResponseManager responsemanager= new ResponseManager();
        if (parametr.isEmpty()){
            persons.clear();
            responsemanager.addResponse("Коллекция очищена");
        }
        else
        {
            responsemanager.addResponse("У этой команды не должно быть параметров!");
        }
        return responsemanager;
    }
}
