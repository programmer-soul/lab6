package command;
import main.Persons;
import main.ResponseManager;
import main.ResponsePerson;
/**
 * Команда 'info'. Выводит информацию о коллекции.
 * @author Matvei Baranov
 */
public class Info extends Command{
    private final Persons persons;
    public Info(Persons persons) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.persons = persons;
    }
    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        if (parametr.isEmpty()){
            return persons.info();
        }
        else
        {
            ResponseManager responsemanager= new ResponseManager();
            responsemanager.addResponse("У этой команды не должно быть параметров!");
            return responsemanager;
        }
    }
}
