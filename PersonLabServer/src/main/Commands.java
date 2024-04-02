package main;
import org.apache.commons.lang3.ObjectUtils;

import java.io.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс оперирует коллекцией команд, находит и исполняет команду
 * исполняет скрипт, хранит и показывает историю команд
 * @author Matvei Baranov
 */
public class Commands {
    //** Класс Люди */
    Persons persons;
    //** Коллекция команд */
    private final HashMap<String,command.Command> commandCollection;
    //** Массив строк для истории */
    private final String[] historystr=new String[14];
    //** Текущий размер истории */
    private int historysize=0;
    //** Индекс в истории */
    private int historyindex=0;
    //** Открытый буфер для чтения файла скрипта */
    private BufferedReader scriptreader;
    //** Текущая строка скрипта */
    private String line;
    //** Массив строк для параметров добавления */
    public final String[] xmlstr =new String[11];
    //** Индекс в Массиве строк */
    private int xmlindex;
    /**
     * Конструктор класса
     */
    public Commands() {
        persons = new Persons();
        commandCollection= new HashMap<>();
        commandCollection.put("help",new command.Help(this));
        commandCollection.put("info",new command.Info(persons));
        commandCollection.put("show",new command.Show(persons));
        commandCollection.put("history",new command.History(this));
        commandCollection.put("insert",new command.Insert(this));
        commandCollection.put("update",new command.Update(this));
        commandCollection.put("remove_key",new command.RemoveKey(persons));
        commandCollection.put("clear",new command.Clear(persons));
        commandCollection.put("save",new command.Save(persons));
        commandCollection.put("load",new command.Load(this,persons));
        commandCollection.put("execute_script",new command.ExecuteScript(this));
        commandCollection.put("exit",new command.Exit());
        commandCollection.put("remove_greater_key",new command.RemoveGreaterKey(persons));
        commandCollection.put("replace_if_lower",new command.ReplaceIfLower(this,persons));
        commandCollection.put("sum_of_height",new command.SumOfHeight(persons));
        commandCollection.put("average_of_weight",new command.AverageOfWeight(persons));
        commandCollection.put("max_by_id",new command.MaxByID(persons));

    }
    /**
     * Возвращает словарь команд
     * @return Словарь команд.
     */
    public Map<String,command.Command> getCommands() {
        return commandCollection;
    }
    /**
     * Добавляет команду в историю
     * @param S команда.
     */
    private void addToHistory(String S){
        if (historysize<14){
            historysize++;
        }
        historystr[historyindex]=S;
        historyindex++;
        if (historyindex==14){
            historyindex=0;
        }
    }
    /**
     * Выдаёт на экран последние 14 выполненных команд
     */
    public ResponseManager history(){
        ResponseManager responsemanager= new ResponseManager();
        if (historysize<14){
            for(int i=0;i<historysize;i++){
                responsemanager.addResponse(historystr[i]);
            }
        }
        else{
            for(int i=historyindex;i<14;i++){
                responsemanager.addResponse(historystr[i]);
            }
            for(int i=0;i<historyindex;i++){
                responsemanager.addResponse(historystr[i]);
            }
        }
        if (responsemanager.size()==0){
            responsemanager.addResponse("Нет истории команд");
        }
        return responsemanager;
    }
    /**
     * Cчитать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме
     * @param filename файл скрипта.
     * @return возращает Истина если была команда exit и надо выйти.
     */
    public ResponseManager ExecuteScript(String filename){
        ResponseManager responsemanager= new ResponseManager();
        try{
            FileReader scriptfile = new FileReader(filename);
            scriptreader = new BufferedReader(scriptfile);
            line=scriptreader.readLine();
            while (line !=null)
            {
                responsemanager.addResponse(line);
                String comstr=line;
                String parametr="";
                int i=line.indexOf(' ');
                if (i>=0){
                    parametr=line.substring(i+1);
                    comstr=line.substring(0,i);
                }
                ResponseManager lineresponselist=execute(comstr,parametr,null,true);
                responsemanager.addList(lineresponselist);
                if (lineresponselist.isExit()){
                    scriptfile.close();
                    responsemanager.setExit();
                    return responsemanager;
                }
                line=scriptreader.readLine();
            }
            scriptreader.close();
            scriptfile.close();
            if(responsemanager.size()==0){
                responsemanager.addResponse("Нет команд в скрипте");
            }
            return responsemanager;
        }
        catch(IOException ex) {
            responsemanager.addResponse(ex.getMessage());
            return responsemanager;
        }
    }
    /**
     * @param usexml для загрузки XML файла.
     * @return следующая строка скрипта обработана
     */
    public boolean getScriptLine(boolean usexml){
        if (usexml){
            if (xmlindex<10){
                line= xmlstr[xmlindex++];
                return true;
            }
            else{
                line="";
                return false;
            }
        }
        else{
            try{
                if (line !=null){
                    line=scriptreader.readLine();
                    System.out.println(line);
                    return true;
                }
                else{
                    return false;
                }
            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }
    }
    /**
     * @param per - класс Person
     * @return возвращает признак, был ли добавлен элемент
     */
    public ResponseManager insert(Person per){
        if (per.getID()==0){
            per.setID(persons.getMaxID()+1);
        }
        return persons.insert(per);
    }
    /**
     * @param per - класс Person
     * @return возвращает признак, был ли добавлен элемент
     */
    public ResponseManager update(Person per){
        return persons.update(per);
    }
    /**
     * @param id - уникальный номер (ключ) в коллекции
     * @param script - вызывается из скрипта
     * @param update - это обновление элемента
     * @param lower - replace_if_lower - заменить значение по ключу, если значение меньше заданного id
     * @param usexml - вызывается для загрузки XML
     * @return возвращает признак, был ли добавлен/изменён элемент с таким id
     */
    public boolean loadxml(Integer id,boolean script,boolean update,boolean lower,boolean usexml){
        String name;
        long height;
        Integer weight;
        String passportID;
        Color eyeColor;
        double X;
        int Y;
        double locationX;
        float locationY;
        double locationZ;
        xmlindex=0;
        //if (script || usexml){
            if (getScriptLine(usexml) && Person.validateName(line)){
                name=line;
            }
            else{
                System.out.println("Ошибка поля Имени");
                return false;
            }
            if (getScriptLine(usexml) && Coordinates.validateX(line)){
                X=Double.parseDouble(line);
            }
            else{
                System.out.println("Ошибка поля Координата X");
                return false;
            }
            if (getScriptLine(usexml) && Coordinates.validateY(line)){
                Y=Integer.parseInt(line);
            }
            else{
                System.out.println("Ошибка поля Координата Y");
                return false;
            }
            if (getScriptLine(usexml) && Person.validateHeight(line)){
                height=Long.parseLong(line);
            }
            else{
                System.out.println("Ошибка поля рост");
                return false;
            }
            if (getScriptLine(usexml) && Person.validateWeight(line)){
                weight=Integer.parseInt(line);
            }
            else{
                System.out.println("Ошибка поля вес");
                return false;
            }
            if (getScriptLine(usexml) && Person.validatePassportID(line)){
                passportID=line;
            }
            else{
                System.out.println("Ошибка поля Имени");
                return false;
            }
            if (getScriptLine(usexml) && Color.validateColor(line)){
                eyeColor = Color.valueOf(line);
            }
            else{
                System.out.println("Ошибка поля цвет глаз");
                return false;
            }
            if (getScriptLine(usexml) && Location.validateX(line)){
                locationX=Double.parseDouble(line);
            }
            else{
                System.out.println("Ошибка поля локация X");
                return false;
            }

            if (getScriptLine(usexml) && Location.validateY(line)){
                locationY=Float.parseFloat(line);
            }
            else{
                System.out.println("Ошибка поля локация X");
                return false;
            }
            if (getScriptLine(usexml) && Location.validateZ(line)){
                locationZ=Double.parseDouble(line);
            }
            else{
                System.out.println("Ошибка поля локация X");
                return false;
            }
        /*
        }
        else{
            name=Input.inputString("Введите имя:",false);
            X=Input.inputDouble("Введите координату X:",true);
            Y=Input.inputInt("Введите координату Y:",true,true);
            height=Input.inputLong("Введите рост:",false,false);
            weight=Input.inputInt("Введите вес:",false,false);
            passportID=Input.inputString("Введите серия-номер паспорта:",false);
            eyeColor=Input.inputColor("Введите цвет глаз (BLACK,BLUE,ORANGE,WHITE):");
            locationX=Input.inputDouble("Введите локацию X:",true);
            locationY=Input.inputFloat("Введите локацию Y:",true);
            locationZ=Input.inputDouble("Введите локацию Z:",true);
        }*/
        Coordinates coordinates=new Coordinates(X,Y);
        Location location=new Location(locationX,locationY,locationZ);
        ZonedDateTime creationDate = ZonedDateTime.now(ZoneOffset.systemDefault());
        if (usexml){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            creationDate = ZonedDateTime.parse(xmlstr[10],formatter);
        }
        if (!lower){
            Person per= new Person(id,name,coordinates,creationDate,height,weight,passportID,eyeColor,location);
            persons.insert(per);
            if (update){
                System.out.print("Обновлена запись ");
            }else{
                System.out.print("Добавлена запись ");
            }
            //per.show();
        }
        else{
            persons.replace_if_lower(id,name,coordinates,creationDate,height,weight,passportID,eyeColor,location);
        }
        return true;
    }
    /**
     * @param comstr команда для выполнения
     * @param parametr параметр команды
     * @param script команда из скрипта
     * @return Результат исполнения.
     */
    public ResponseManager execute(String comstr,String parametr,ResponsePerson person,boolean script){
        addToHistory(comstr);
        command.Command com = commandCollection.get(comstr);
        if (com == null) {
            ResponseManager responsemanager = new ResponseManager();
            responsemanager.addResponse("Неверная команда");
            return responsemanager;
        }
        return com.execute(comstr,parametr,person,script);
    }
    public command.Save getSaveCommmand(){
        return (command.Save)commandCollection.get("save");
    }
}
