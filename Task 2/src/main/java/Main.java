import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PersonDatabase db = new PersonDatabase();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an action: [1] Add Person, [2] Update Person, [3] Delete Person, [4] View Persons, [5] Search by ID, [6] Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Person person = new Person();
                    person.setName(name);
                    person.setAge(age);
                    db.addPerson(person);
                    break;

                case 2:
                    System.out.print("Enter ID of the person to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new age: ");
                    int newAge = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Person newPerson = new Person();
                    newPerson.setName(newName);
                    newPerson.setAge(newAge);
                    db.updatePerson(updateId, newPerson);
                    break;

                case 3:
                    System.out.print("Enter ID of the person to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    db.deletePerson(deleteId);
                    break;

                case 4:
                    db.viewPersons();
                    break;

                case 5:
                    System.out.print("Enter ID of the person to search: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Person foundPerson = db.getPersonById(searchId);
                    if (foundPerson != null) {
                        System.out.println("ID: " + foundPerson.getId() + ", Name: " + foundPerson.getName() + ", Age: " + foundPerson.getAge());
                    } else {
                        System.out.println("Person not found.");
                    }
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

