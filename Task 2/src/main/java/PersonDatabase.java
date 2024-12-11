import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonDatabase {
    private List<Person> persons;
    private Set<Integer> usedIds;
    private static final String JSON_FILE = "persons.json";
    private static final String XML_FILE = "persons.xml";
    private int currentId = 1;

    public PersonDatabase() {
        persons = new ArrayList<>();
        usedIds = new HashSet<>();
    }

    public void addPerson(Person person) {
        while (usedIds.contains(currentId)) {
            currentId++;
        }
        person.setId(currentId++);
        usedIds.add(person.getId());
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
        usedIds.remove(id);
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
            File jsonFile = new File(JSON_FILE);
            if (jsonFile.exists()) {

                ObjectMapper jsonMapper = new ObjectMapper();
                persons = jsonMapper.readValue(jsonFile, jsonMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
            } else {
                File xmlFile = new File(XML_FILE);
                if (xmlFile.exists()) {

                    XmlMapper xmlMapper = new XmlMapper();
                    persons = xmlMapper.readValue(xmlFile, xmlMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
                } else {
                    System.out.println("Neither JSON nor XML file found, starting with an empty database.");
                    return;
                }
            }

            // Populate usedIds set and determine currentId
            for (Person person : persons) {
                usedIds.add(person.getId());
                currentId = Math.max(currentId, person.getId() + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

