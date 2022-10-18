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
    private static Scanner scanner = new Scanner(System.in);

    // Initializing variables for the file paths and names
    private static String currentDirectory = System.getProperty("user.dir");
    private static String directory = "data";
    private static String fileName = "contactInfo.txt";
    private static Path dataDirectory = Paths.get(currentDirectory, directory);
    private static Path dataFile = Paths.get(directory, fileName);
    private static Path filePath = Paths.get(currentDirectory, directory, fileName);


    private static ArrayList<String> contactList = new ArrayList<>();
    //private static List<String> alreadyInTheFile = Files.readAllLines(filePath);


    public static void main(String[] args) throws IOException {

        // If the files/directories don't exist, create them.
        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }
        if (Files.notExists(dataFile)) {
            Files.createFile(dataFile);
        }

        //Local List To Store File Data
        List<String> alreadyInTheFile = Files.readAllLines(filePath);

        //Adds Data From File List to Local Array List
        for (int i = 0; i < alreadyInTheFile.size(); i++) {
            contactList.add(alreadyInTheFile.get(i));
        }

        //Loop To Format Array Data In Console
        viewContacts();


        System.out.printf("%nWelcome to the contacts update application");

        int testCondition = 0;
        while (testCondition == 0) {

            // Gives users options to choose from.
            userOptions();

            // User enters what they would like to do, recursion used to correct user errors.
            try {
                int userChoice = scanner.nextInt();
                while (userChoice <= 0 || userChoice >= 6) {
                    System.out.println();
                    System.out.println("Enter a number between 1 and 5");
                    userOptions();
                    scanner.nextLine();
                    userChoice = scanner.nextInt();
                }
                // Clearing out the scanner.
                scanner.nextLine();

                switch (userChoice) {
                    case 1:
                        //View Contacts List
                        viewContacts();
                        break;
                    case 2:
                        //Add Contact
                        addContact();
                        break;
                    case 3:
                        //Search Contact
                        searchContact();
                        break;
                    case 4:
                        //Delete Contact
                        deleteContact();
                        break;
                    case 5:
                        //Exit App
                        System.out.println("Exiting the program.");
                        Files.write(filePath, contactList);

                        // This is to break out of the loop.
                        testCondition += 1;
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("Enter a number between 1 and 5.");
                scanner.nextLine();
            }
        }
    }
    public static void userOptions(){
        System.out.println("Please choose an option.");
        System.out.println("1: View Contacts");
        System.out.println("2: Add Contact");
        System.out.println("3: Search Contacts");
        System.out.println("4: Delete Contact");
        System.out.println("5: Exit");
    }

    private static void viewContacts(){
        System.out.println("Current saved contacts: ");
        for (String contact : contactList) {
            String[] correspondingArray = contact.split(" : ", 0);
            System.out.printf("%s: %s%n", correspondingArray[0], correspondingArray[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)$2-$3"));
        }
        System.out.println();
    }

    private static void addContact() {
        System.out.println("Please enter the name of your new contact.");
        String contactName = scanner.nextLine();
        System.out.println("Please enter the phone number of your new contact.");
        String contactNumber = collectNumber();
        Contact newContact = new Contact(contactName, contactNumber);
        contactList.add(newContact.getName() + " : " + newContact.getPhoneNumber());
        System.out.printf("%s added to contacts%n", contactName);
    }

    private static String collectNumber() {
        String contactNumber = scanner.nextLine();
        if (contactNumber.length() != 10) {
            System.out.println("Please enter a phone number that's exactly 10 digits in length.");
            collectNumber();
        }
        return contactNumber;
    }

    private static void searchContact(){
        System.out.println("Please enter the name of the contact, or just press enter to return to main menu.");
        String nameSearch = scanner.nextLine();
        if (!nameSearch.equals("")) {
            for (int i = 0; i < contactList.size(); i++) {
                String currentIterationName = contactList.get(i);
                String[] correspondingArray = currentIterationName.split(" : ", 0);
                if (nameSearch.equalsIgnoreCase(correspondingArray[0])) {
                    System.out.printf("Phone number for %s is %s%n", nameSearch, correspondingArray[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3"));
                    break;
                } else if ((contactList.size() - 1) == i) {
                    System.out.printf("%s not found.", nameSearch);
                    System.out.println();
                    searchContact();
                }
            }
        }
    }
    private static void deleteContact() {
        System.out.println("What is the name of the contact that you would like to delete?");
        String userInput = scanner.nextLine();
        for (int i = 0; i < contactList.size(); i++) {
            String currentIterationName = contactList.get(i);
            String[] correspondingArray = currentIterationName.split(" : ", 0);
            if (userInput.equalsIgnoreCase(correspondingArray[0])) {
                contactList.remove(i);
                System.out.printf("%s deleted from contacts.", userInput);
                System.out.println();
            }
            else if ((contactList.size() - 1) == i) {
                System.out.printf("%s not found.", userInput);
            }
        }
    }
}
