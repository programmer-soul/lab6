package command;
import main.ResponseManager;
import main.ResponsePerson;
/**
 * Команда 'exit'. Завершает выполнение.
 * @author Matvei Baranov
 */
public class Exit extends Command{
    public Exit(){
        super("exit","завершить программу (без сохранения в файл)");
    }
    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        ResponseManager responsemanager= new ResponseManager();
        if (parametr.isEmpty()){
            responsemanager.addResponse("Команда выполнена");
            responsemanager.setExit();
        }
        else
        {
            responsemanager.addResponse("У этой команды не должно быть параметров!");
        }
        return responsemanager;
    }
}
