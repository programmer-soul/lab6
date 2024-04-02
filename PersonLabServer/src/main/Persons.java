package main;

import java.util.*;
import java.time.*;
import javax.xml.stream.*;
/**
 * Класс оперирует коллекцией людей
 * @author Matvei Baranov
 */
public class Persons {
    //** Коллекция */
    private final LinkedHashMap<Integer,Person> personCollection;
    //** Дата/время инициализации */
    private final ZonedDateTime initDateTime;
    //** Дата/время последнего сохранения */
    private ZonedDateTime lastSaveDateTime;
    //** Дата/время последней загрузки */
    private ZonedDateTime lastLoadDateTime;
    /**
     * Конструктор класса
     */
    Persons(){
        personCollection=new LinkedHashMap<>();
        initDateTime = ZonedDateTime.now(ZoneOffset.systemDefault());
    }
    /**
     * вывести в стандартный поток вывода все элементы коллекции в строковом представлении
     */
    public ResponseManager show(){
        ResponseManager responsemanager= new ResponseManager();
        Set<Map.Entry<Integer,Person>> set = personCollection.entrySet();// Получаем набор элементов
        for (Map.Entry<Integer, Person> me : set) {
            Person per = me.getValue();
            responsemanager.addResponsePerson(per);
        }
        if(responsemanager.fullsize()==0){
            responsemanager.addResponse("В коллекции нет элементов!");
        }
        else{
            responsemanager.sort();
            responsemanager.addResponse(responsemanager.sizePerson(),"В коллекции "+responsemanager.sizePerson()+" элемента(ов)");
        }
        return responsemanager;
    }
    /**
     * вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    public ResponseManager info(){
        ResponseManager responsemanager= new ResponseManager();
        responsemanager.addResponse("Сведения о коллекции:");
        responsemanager.addResponse("  Тип коллекции: " + personCollection.getClass().getName());
        responsemanager.addResponse("  Количество элементов: " + personCollection.size());
        responsemanager.addResponse("  Дата/время последнего сохранения: " + lastSaveDateTime);
        responsemanager.addResponse("  Дата/время последней загрузки: " + lastLoadDateTime);
        responsemanager.addResponse("  Дата/время инициализации: " + initDateTime);
        return responsemanager;
    }
    /**
     * Сохраняет коллекцию в открытый стрим в формате XML
     * @param out открытый стрим для записи
     */
    public boolean saveXML(XMLStreamWriter out){
        try {
            lastSaveDateTime = ZonedDateTime.now(ZoneOffset.systemDefault());
            Set<Map.Entry<Integer, Person>> set = personCollection.entrySet();// Получаем набор элементов
            for (Map.Entry<Integer, Person> me : set) {
                Person per = me.getValue();
                if (!per.saveXML(out)) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * вывести сумму значения поля height для всех элементов коллекции
     */
    public long SumOfHeight(){
        Set<Map.Entry<Integer, Person>> set = personCollection.entrySet();// Получаем набор элементов
        Iterator<Map.Entry<Integer, Person>> iter = set.iterator();// Получаем итератор
        long totalheight=0;
        while(iter.hasNext()) {
            Map.Entry<Integer, Person> me = iter.next();
            Person per=me.getValue();
            totalheight+=per.getHeight();
        }
        return totalheight;
    }
    /**
     * вернуть среднее значение поля weight для всех элементов коллекции
     */
    public long AverageOfWeight(){
        Set<Map.Entry<Integer,Person>> set = personCollection.entrySet();// Получаем набор элементов
        Iterator<Map.Entry<Integer,Person>> iter = set.iterator();// Получаем итератор
        long totalweight=0;
        while(iter.hasNext()) {
            Map.Entry<Integer,Person> me = iter.next();
            Person per=me.getValue();
            totalweight+=per.getWeight();
        }
        if (!personCollection.isEmpty()) {
            totalweight /= personCollection.size();
        }
        return totalweight;
    }
    /**
     * @return максимальный id в коллекции
     */
    public Integer getMaxID(){
        Integer id=0;
        if (!personCollection.isEmpty()) {
            Set<Integer> setkeys = personCollection.keySet();
            Integer[] keys = setkeys.toArray(new Integer[0]);
            id = Arrays.stream(keys).max(Integer::compare).get();
        }
        return id;
    }
    /**
     * Добавить элемент коллекции
     */
    public ResponseManager insert(Person per){
        ResponseManager responsemanager= new ResponseManager();
        int bsize=personCollection.size();
        personCollection.put(per.getID(),per);
        Person newperson=personCollection.get(per.getID());
        responsemanager.addResponsePerson(newperson);
        if (personCollection.size()!=bsize){
            responsemanager.addResponse(1,"Успешно добавлен элемент "+per.getID());
        }
        else{
            responsemanager.addResponse(1,"Элемент обновлен "+per.getID());
        }
        return responsemanager;
    }
    /**
     * Обновить элемент коллекции
     */
    public ResponseManager update(Person per){
        ResponseManager responsemanager= new ResponseManager();
        int bsize=personCollection.size();
        personCollection.put(per.getID(),per);
        Person newperson=personCollection.get(per.getID());
        responsemanager.addResponsePerson(newperson);
        if (personCollection.size()!=bsize){
            responsemanager.addResponse(1,"Успешно добавлен элемент "+per.getID());
        }
        else{
            responsemanager.addResponse(1,"Элемент обновлен "+per.getID());
        }
        return responsemanager;
    }
    /**
     * Заменить значение по ключу, если значение меньше заданного id
     */
    public ResponseManager replace_if_lower(int id,String name,Coordinates coordinates,ZonedDateTime creationDate,long height,Integer weight,String passportID,Color eyeColor,Location location){
        ResponseManager responsemanager= new ResponseManager();
        if (!personCollection.isEmpty()) {
            Set<Integer> setkeys = personCollection.keySet();
            Integer[] keys = setkeys.toArray(new Integer[0]);
            for (Integer key : keys) {
                if (key < id) {
                    Person per = new Person(key, name, coordinates, creationDate, height, weight, passportID, eyeColor, location);
                    personCollection.put(per.getID(), per);
                    responsemanager.addResponsePerson(per);
                }
            }
        }
        if(responsemanager.sizePerson()==0){
            responsemanager.addResponse("Нет элементов для обновления!");
        }
        else{
            responsemanager.sort();
            responsemanager.addResponse(responsemanager.sizePerson(),"Обновлено "+responsemanager.sizePerson()+" элемента(ов)");
        }
        return responsemanager;
    }
    /**
     * Очищает коллекцию
     */
    public void clear(){
        personCollection.clear();
    }
    /**
     * Фиксируем время начала загрузки XML
     */
    public void startLoad() {
        lastLoadDateTime = ZonedDateTime.now(ZoneOffset.systemDefault());
    }
    /**
     * Удалить из коллекции все элементы, ключ которых превышаюет заданный
     */
    public ResponseManager RemoveGreaterKey(Integer id){
        ResponseManager responsemanager= new ResponseManager();
        if (!personCollection.isEmpty()) {
            Set<Integer> setkeys = personCollection.keySet();
            Integer[] keys = setkeys.toArray(new Integer[0]);
            for (Integer key : keys) {
                if (key > id) {
                    responsemanager.addResponsePerson(personCollection.get(key));
                    personCollection.remove(key);
                }
            }
        }
        if(responsemanager.sizePerson()==0){
            responsemanager.addResponse("Нет элементов для удаления!");
        }
        else{
            responsemanager.sort();
            responsemanager.addResponse(responsemanager.sizePerson(),"Удалено "+responsemanager.sizePerson()+" элемента(ов)");
        }

        return responsemanager;
    }
    /**
     * Вывести объект из коллекции с максимальным id
     */
    public ResponseManager MaxByID(){
        ResponseManager responsemanager= new ResponseManager();
        Integer maxID=getMaxID();
        if (maxID>0){
            Person per=personCollection.get(maxID);
            responsemanager.addResponsePerson(per);
        }
        if(responsemanager.fullsize()==0){
            responsemanager.addResponse("В коллекции нет элементов!");
        }
        else{
            responsemanager.sort();
            responsemanager.addResponse(responsemanager.sizePerson(),"Максимальным id "+maxID);
        }
        return responsemanager;
    }
    /**
     * Удалить объект из коллекции с заданным id
     */
    public boolean remove(int id){
        int bsize=personCollection.size();
        personCollection.remove(id);
        if (bsize!=personCollection.size()){
            return true;
        }
        else{
            return false;
        }
    }
}
