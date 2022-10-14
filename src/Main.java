import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);


        // Initalizing variables for the file paths and names
        String currentDirectory = System.getProperty("user.dir");
        String directory = "data";
        String fileName = "contactInfo.txt";
        Path dataDirectory = Paths.get(currentDirectory, directory);
        Path dataFile = Paths.get(directory, fileName);
        Path filePath = Paths.get(currentDirectory, directory, fileName);

        // If the files/directories don't exist, create them.
        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }
        if (Files.notExists(dataFile)) {
            Files.createFile(dataFile);
        }


        ArrayList<String> contactList = new ArrayList<>();
        List<String> aleradyInTheFile = Files.readAllLines(filePath);

        for (int i = 0; i < aleradyInTheFile.size(); i++) {
            contactList.add(aleradyInTheFile.get(i));
        }

        System.out.println(contactList);

        System.out.println("Welcome to the contacts update application");

        System.out.println(contactList.size());





//        String testValue = contactList.get(0);
//        String[] arr = testValue.split("-", 2);
////        System.out.println(arr);
//        for (String a : arr) {
//            System.out.println(a);
//        }






        int testCondition = 0;
        while (testCondition == 0) {

            // Gives users options to choose from.
            System.out.println("Please choose an option.");
            System.out.println("1: View contacts.");
            System.out.println("2: Add a new contact.");
            System.out.println("3: Search a contact by name.");
            System.out.println("4: Delete an existing contact.");
            System.out.println("5: Exit");

            // User enters what they would like to do, recursion used to correct user errors.
            int userChoice = scanner.nextInt();
            while (userChoice < 1 || userChoice > 5) {
                System.out.println("Please enter a number between 1 and 5");
                userChoice = scanner.nextInt();
            }
            // Clearing out the scanner.
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    System.out.println("Current saved contacts: ");
                    for (String contact : contactList) {
                        System.out.println(contact);
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Please enter the name of your new contact.");
                    String contactName = scanner.nextLine();
                    System.out.println("Please enter the phone number of your new contact.");
                    String contactNumber = scanner.nextLine();
                    Contact newContact = new Contact(contactName, contactNumber);
                    contactList.add(newContact.getName() + " - " + newContact.getPhoneNumber());
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Please enter the name of the contact");
                    String nameSearch = scanner.nextLine();
                    for (int i = 0; i < contactList.size(); i++) {
                        String currentIterationName = contactList.get(i);
                        String[] correspondingArray = currentIterationName.split(" - ", 0);
                        if (nameSearch.equalsIgnoreCase(correspondingArray[0])) {
                            System.out.println("Phone number for " + nameSearch + " is " + correspondingArray[1]);
                            break;
                        }
                        else if ((contactList.size() - 1) == i) {
                            System.out.println("Name not found.");
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("What is the name of the contact that you would like to delete?");
                    String userInput = scanner.nextLine();
                    for (int i = 0; i < contactList.size(); i++) {
                        String currentIterationName = contactList.get(i);
                        String[] correspondingArray = currentIterationName.split(" - ", 0);
                        if (userInput.equalsIgnoreCase(correspondingArray[0])) {
                            contactList.remove(i);
                            break;
                        }
                        else if ((contactList.size() - 1) == i) {
                            System.out.println("Name not found.");
                            break;
                        }
                    }
                    break;
                case 5:
                    System.out.println("Exiting the program.");
                    Files.write(filePath, contactList);
                    testCondition += 1;
            }
        }
    }
}
