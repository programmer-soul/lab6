package command;
import main.Commands;
import main.ResponseManager;
import main.ResponsePerson;
/**
 * Команда 'help'. Выводит справку по доступным командам.
 * @author Matvei Baranov
 */
public class Help extends Command{
    private final Commands commands;
    public Help(Commands commands) {
        super("help", "вывести справку по доступным командам");
        this.commands = commands;
    }
    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        ResponseManager responsemanager= new ResponseManager();
        if (parametr.isEmpty()){
            commands.getCommands().values().forEach(command -> {
                responsemanager.addResponse(command.toString());
            });
        }
        else
        {
            responsemanager.addResponse("У этой команды не должно быть параметров!");
        }
        return responsemanager;
    }
}
