package main;

import javax.xml.stream.XMLStreamWriter;
import java.time.*;
import javax.xml.stream.*;
/**
 * Класс Человек
 * @author Matvei Baranov
 */
public class Person implements Comparable<Person> {
    private int id;
    private String name;
    private Coordinates coordinates;
    private java.time.ZonedDateTime creationDate;
    private long height;
    private Integer weight;
    private String passportID;
    private Color eyeColor;
    private Location location;
    /**
     * Конструктор класса
     */
    public Person(int id,String name,Coordinates coordinates,ZonedDateTime creationDate,long height,Integer weight,String passportID,Color eyeColor,Location location){
        this.id=id;
        this.name=name;
        this.coordinates=coordinates;
        this.creationDate=creationDate;
        this.height=height;
        this.weight=weight;
        this.passportID=passportID;
        this.eyeColor=eyeColor;
        this.location=location;
    }
    /**
     * Сохраняет свой данные в открытый стрим в формате XML
     * @param out открытый стрим для записи
     */
    public void saveXML(XMLStreamWriter out){
        try {
            out.writeStartElement("Person");

            out.writeStartElement("id");
            out.writeCharacters(Integer.toString(id));
            out.writeEndElement();

            out.writeStartElement("name");
            out.writeCharacters(name);
            out.writeEndElement();

            coordinates.SaveXML(out);

            out.writeStartElement("creationDate");
            out.writeCharacters(creationDate.toString());
            out.writeEndElement();

            out.writeStartElement("height");
            out.writeCharacters(Long.toString(height));
            out.writeEndElement();

            out.writeStartElement("weight");
            out.writeCharacters(Integer.toString(weight));
            out.writeEndElement();

            out.writeStartElement("passportID");
            out.writeCharacters(passportID);
            out.writeEndElement();

            out.writeStartElement("eyeColor");
            out.writeCharacters(eyeColor.toString());
            out.writeEndElement();

            location.SaveXML(out);

            out.writeEndElement();
            out.writeCharacters(System.getProperty("line.separator"));
        }
        catch (XMLStreamException e) {
            System.out.println("Ошибка сохранения! "+e);
        }
    }
    /**
     * @return возвращает id
     */
    public int getID() {
        return id;
    }
    /**
     * @return возвращает Рост
     */
    public long getHeight() {
        return height;
    }
    /**
     * @return возвращает Вес
     */
    public Integer getWeight() {
        return weight;
    }
    /**
     * @return разницу текущего id и сравниваемого id для сортировки по id
     */
    public int compareTo(Person per) {
        return this.id - per.id; //сортируем по возрастанию
    }
    /**
     * @param S строка для проверки
     * @return Проверка строки на валидность ФИО.
     */
    public static boolean validateName(String S){
        if (S.isEmpty()) {
            return false;
        } else{
            return true;
        }
    }
    /**
     * @param S строка для проверки
     * @return Проверка строки на валидность Номера Паспорта.
     */
    public static boolean validatePassportID(String S){
        if (S.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    /**
     * @param S строка для проверки
     * @return Проверка строки на валидность Роста.
     */
    public static boolean validateHeight(String S){
        try
        {
            long num = Long.parseLong(S);
            if (num>0)
                return true;
            else
                return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    /**
     * @param S строка для проверки
     * @return Проверка строки на валидность Веса.
     */
    public static boolean validateWeight(String S){
        try
        {
            int num = Integer.parseInt(S);
            if (num>0)
                return true;
            else
                return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    /**
     * @param S строка для проверки
     * @return Проверка строки на валидность ID.
     */
    public static boolean validateID(String S){
        try
        {
            int num = Integer.parseInt(S);
            if (num>0)
                return true;
            else
                return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    /**
     * печатает все данные в одну строку
     */
    public void show() {
        System.out.println(id+":"+name+" "+passportID+" height:"+height+" weight:"+weight+" eye:"+eyeColor+" "+coordinates.toString()+" "+location.toString());
    }
}
