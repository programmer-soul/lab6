package command;
import main.Person;
import main.Commands;
import main.ResponseManager;
import main.ResponsePerson;
import main.Persons;
/**
 * Команда 'replace_if_lower'. Заменить значение по ключу, если значение меньше заданного.
 * @author Matvei Baranov
 */
public class ReplaceIfLower extends Command{
    private final Commands commands;
    private final Persons persons;
    public ReplaceIfLower(Commands commands,Persons persons){
        super("replace_if_lower {id} {element}","заменить значение по ключу, если значение меньше заданного");
        this.commands = commands;
        this.persons = persons;
    }
    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        return persons.replace_if_lower(person.id,person.name,person.coordinates,person.creationDate,person.height,person.weight,person.passportID,person.eyeColor,person.location);
    }
}
