package gr.aueb.cf.mobilecontacts;

import gr.aueb.cf.mobilecontacts.controller.MobileContactController;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in =  new Scanner(System.in);
    private final static MobileContactController controller = new MobileContactController();

    public static void main(String[] args) {
        String choice;

        while (true) {
            printMenu();
            choice = getToken();
            if (choice.equals("q") || choice.equals(("Q"))) {
                break;
            }
            handleChoice(choice);
        }
    }

    public static void handleChoice(String choice) {
        String firstname;
        String lastname;
        String phoneNumber;
        String response;
        Long id;

        switch (choice) {
            case "1":
                System.out.println("Insert Firstname, Lastname, Phone Number");
                firstname = getToken();
                lastname = getToken();
                phoneNumber = getToken();

                MobileContactInsertDTO mobileContactInsertDTO = new MobileContactInsertDTO(firstname,
                        lastname, phoneNumber);

                response = controller.insertContact(mobileContactInsertDTO);
                if (response.startsWith("OK")) {
                    System.out.println("Success insert");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Failure insert");
                    System.out.println(response.substring(7));
                }
                break;

            case "2":
                System.out.println("Insert phone number");
                phoneNumber = getToken();
                response = controller.getContactByPhoneNumber(phoneNumber);
                if (response.startsWith("Error")) {
                    System.out.println("Contact not found.");
                    System.out.println(response.substring(3));
                    return;
                }
                System.out.println("Insert failure");
                System.out.println(response.substring(6));
                System.out.println("Insert old ID");
                long oldId = Long.parseLong(getToken());
                System.out.println("Insert new firstname");
                firstname = getToken();
                System.out.println("Insert new lastname");
                lastname = getToken();
                System.out.println("Insert new phone number");
                phoneNumber = getToken();
                MobileContactUpdateDTO mobileContactUpdateDTO = new MobileContactUpdateDTO(oldId,
                        firstname, lastname, phoneNumber);
                response = controller.updateContact(mobileContactUpdateDTO);
                System.out.println(response);
                break;
            case "3":
                System.out.println("Insert ID");
                id = Long.parseLong(getToken());
                response = controller.deleteContactById(id);
                if (response.startsWith("OK")) {
                    System.out.println("Delete success");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Delete failure");
                    System.out.println(response.substring(6));
                }
                break;
            case "4":
                System.out.println("Insert ID");
                id =  Long.parseLong(getToken());
                response = controller.getContactById(id);
                if (response.startsWith("OK")) {
                    System.out.println("Success searching");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Failure searching");
                    System.out.println(response.substring(6));
                }
                break;
            case "5":
                List<String > mobileContacts = controller.getAllContacts();
                if (mobileContacts.isEmpty()) System.out.println("Empty contact list");
                mobileContacts.forEach(System.out::println);
                break;
            default:
                System.out.println("Wrong choice");
                break;
        }

    }

    public static void printMenu() {
        System.out.println("Select one of the following options:");
        System.out.println("1. Add Contact");
        System.out.println("2. Update Contact");
        System.out.println("3. Delete Contact");
        System.out.println("4. Search Contact");
        System.out.println("5. View Contacts");
        System.out.println("Q/q. Exit");
    }


    public static String getToken() {
        return in.nextLine();
    }
}
