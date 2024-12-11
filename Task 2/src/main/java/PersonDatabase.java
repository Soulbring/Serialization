import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonDatabase {
    private List<Person> persons;
    private static final String JSON_FILE = "persons.json";
    private static final String XML_FILE = "persons.xml";
    private int currentId = 1;

    public PersonDatabase() {
        persons = new ArrayList<>();
    }

    public void addPerson(Person person) {
        person.setId(currentId++);
        persons.add(person);
        saveToFile();
    }

    public void updatePerson(int id, Person newPerson) {
        for (Person person : persons) {
            if (person.getId() == id) {
                person.setName(newPerson.getName());
                person.setAge(newPerson.getAge());
                saveToFile();
                return;
            }
        }
    }

    public void deletePerson(int id) {
        persons.removeIf(person -> person.getId() == id);
        saveToFile();
    }

    public Person getPersonById(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public void viewPersons() {
        for (Person person : persons) {
            System.out.println("ID: " + person.getId() + ", Name: " + person.getName() + ", Age: " + person.getAge());
        }
    }

    private void saveToFile() {
        try {
            // Save to JSON
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.writeValue(new File(JSON_FILE), persons);

            // Save to XML
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(new File(XML_FILE), persons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try {
            // Load from JSON
            ObjectMapper jsonMapper = new ObjectMapper();
            persons = jsonMapper.readValue(new File(JSON_FILE), jsonMapper.getTypeFactory().constructCollectionType(List.class, Person.class));

            // Load from XML
            XmlMapper xmlMapper = new XmlMapper();
            persons = xmlMapper.readValue(new File(XML_FILE), xmlMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
