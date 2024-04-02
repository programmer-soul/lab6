package command;

import main.ResponseManager;
import main.ResponsePerson;

/**
 * Интерфейс для всех выполняемых команд
 * @author Matvei Baranov
 */
public interface Executable {
    /**
     * Получить название
     * @return название
     */
    String getName();
    /**
     * Получить описание
     * @return описание
     */
    String getDescription();
    /**
     * Выполнить
     * @param commandName команда для выполнения
     * @param parametr параметр команды
     * @param person структура для insert/update
     * @param script команда из скрипта*
     * @return результат выполнения
     */
    ResponseManager execute(String commandName, String parametr, ResponsePerson person, boolean script);
}
