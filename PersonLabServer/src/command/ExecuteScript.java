package command;
import main.Commands;
import main.ResponseManager;
import main.ResponsePerson;
/**
 * Команда 'execute_script'. Считать и исполнить скрипт из указанного файла.
 * @author Matvei Baranov
 */
public class ExecuteScript extends Command {
    private final Commands commands;
    public ExecuteScript(Commands commands){
        super("execute_script file_name","считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.commands=commands;
    }
    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        if (!parametr.isEmpty()){
            if (script){
                ResponseManager responsemanager= new ResponseManager();
                responsemanager.addResponse("Скрипт уже исполняется");
                return responsemanager;
            }
            else{
                return commands.ExecuteScript(parametr);
            }
        }
        else{
            ResponseManager responsemanager= new ResponseManager();
            responsemanager.addResponse("У этой команды обязательный параметр имя файла!");
            return responsemanager;
        }
    }
}
