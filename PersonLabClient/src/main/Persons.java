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
    public void show(){
        Set<Map.Entry<Integer,Person>> set = personCollection.entrySet();// Получаем набор элементов
        for (Map.Entry<Integer, Person> me : set) {
            Person per = me.getValue();
            per.show(); //Отображаем элементы
        }
    }
    /**
     * вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    public void info(){
        System.out.println("Сведения о коллекции:");
        System.out.println("  Тип коллекции: " + personCollection.getClass().getName());
        System.out.println("  Количество элементов: " + personCollection.size());
        System.out.println("  Дата/время последнего сохранения: " + lastSaveDateTime);
        System.out.println("  Дата/время последней загрузки: " + lastLoadDateTime);
        System.out.println("  Дата/время инициализации: " + initDateTime);
    }
    /**
     * Сохраняет коллекцию в открытый стрим в формате XML
     * @param out открытый стрим для записи
     */
    public void saveXML(XMLStreamWriter out){
        try {
            lastSaveDateTime = ZonedDateTime.now(ZoneOffset.systemDefault());
            Set<Map.Entry<Integer, Person>> set = personCollection.entrySet();// Получаем набор элементов
            for (Map.Entry<Integer, Person> me : set) {
                Person per = me.getValue();
                per.saveXML(out);
            }
        } catch (Exception e) {
            System.out.println("Ошибка сохранения! "+e);
        }
    }
    /**
     * вывести сумму значения поля height для всех элементов коллекции
     */
    public void SumOfHeight(){
        Set<Map.Entry<Integer, Person>> set = personCollection.entrySet();// Получаем набор элементов
        Iterator<Map.Entry<Integer, Person>> iter = set.iterator();// Получаем итератор
        long totalheight=0;
        while(iter.hasNext()) {
            Map.Entry<Integer, Person> me = iter.next();
            Person per=me.getValue();
            totalheight+=per.getHeight();
        }
        System.out.println("Сумма роста всех людей "+totalheight);
    }
    /**
     * вывести среднее значение поля weight для всех элементов коллекции
     */
    public void AverageOfWeight(){
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
        System.out.println("Средний вес "+totalweight);
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
     * Дабавить или обновить элемент коллекции
     */
    public void insert(Person per){
        personCollection.put(per.getID(),per);
    }
    /**
     * Заменить значение по ключу, если значение меньше заданного id
     */
    public void replace_if_lower(int id,String name,Coordinates coordinates,ZonedDateTime creationDate,long height,Integer weight,String passportID,Color eyeColor,Location location){
        if (!personCollection.isEmpty()) {
            Set<Integer> setkeys = personCollection.keySet();
            Integer[] keys = setkeys.toArray(new Integer[0]);
            for (Integer key : keys) {
                if (key < id) {
                    Person per = new Person(key, name, coordinates, creationDate, height, weight, passportID, eyeColor, location);
                    personCollection.put(per.getID(), per);
                    System.out.print("Обновлена запись ");
                    per.show();

                }
            }
        }
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
    public void RemoveGreaterKey(Integer id){
        if (!personCollection.isEmpty()) {
            Set<Integer> setkeys = personCollection.keySet();
            Integer[] keys = setkeys.toArray(new Integer[0]);
            for (Integer key : keys) {
                if (key > id) {
                    personCollection.remove(key);
                }
            }
        }
    }
    /**
     * Вывести объект из коллекции с максимальным id
     */
    public void MaxByID(){
        Integer maxID=getMaxID();
        if (maxID>0){
            Person per=personCollection.get(maxID);
            per.show();
        }
    }
    /**
     * Удалить объект из коллекции с заданным id
     */
    public void remove(int id){
        personCollection.remove(id);
    }
}
