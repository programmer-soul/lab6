package command;
import main.Persons;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import main.ResponseManager;
import main.ResponsePerson;
/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 * @author Matvei Baranov
 */
public class Save extends Command{
    private final Persons persons;
    public Save(Persons persons){
        super("save","сохранить коллекцию в файл (xml)");
        this.persons=persons;
    }
    /**
     * Сохраняет коллекцию в файл
     * @param filename имя файла
     */
    public ResponseManager saveXML(String filename){
        ResponseManager responsemanager= new ResponseManager();
        try {
            File xmlFile = new File(filename);
            OutputStream outputStream = new FileOutputStream(xmlFile);
            XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            out.writeStartDocument("UTF-8", "1.0");
            out.writeCharacters(System.getProperty("line.separator"));
            out.writeStartElement("Persons");
            out.writeCharacters(System.getProperty("line.separator"));
            boolean res=persons.saveXML(out);
            out.writeEndElement();
            out.writeEndDocument();
            out.flush();
            out.close();
            outputStream.close();
            if (res){
                responsemanager.addResponse("Успешное сохранение в XML");
            }
            else{
                responsemanager.addResponse("Ошибка сохранения! ");
            }
            return responsemanager;
        } catch (Exception e) {
            responsemanager.addResponse("Ошибка сохранения! "+e);
            return responsemanager;
        }
    }
    public ResponseManager saveXML(){
        return saveXML("persons.xml");
    }

    @Override
    public ResponseManager execute(String commandName,String parametr,ResponsePerson person,boolean script) {
        /*if (parametr.isEmpty()){
            return saveXML();
        }
        else{
            return saveXML(parametr);
        }*/
        ResponseManager responsemanager= new ResponseManager();
        responsemanager.addResponse("Команда save на сервере отключена!");
        return responsemanager;
    }
}
