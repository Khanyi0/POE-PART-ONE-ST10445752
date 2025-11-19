/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package partonepoe;

import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_lab
 */
public class PartOnePoe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner sc = new Scanner(System.in);
        registration registration = new registration();
        Login login = new Login();

        System.out.println("=== User Registration ===");

        // Ask for first and last name
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        // Username validation
        String username;
        do {
            System.out.print("Enter username (must contain '_' and ≤ 5 characters): ");
            username = sc.nextLine();
            userinput tempUser = new userinput(username, "tempPass123!", "+27123456789", firstName, lastName);
            String usernameMessage = registration.registerUser(tempUser);
            System.out.println(usernameMessage);
        } while (!registration.checkUserName(username));

        // Password validation
        String password;
        do {
            System.out.print("Enter password (min 8 chars, capital letter, number, special char): ");
            password = sc.nextLine();
            userinput tempUser = new userinput(username, password, "+27123456789", firstName, lastName);
            String passwordMessage = registration.validatePassword(tempUser);
            System.out.println(passwordMessage);
        } while (!registration.checkPasswordComplexity(password));

        // Cell phone validation
        String cellPhone;
        do {
            System.out.print("Enter cell phone number (e.g. +27733814684): ");
            cellPhone = sc.nextLine();
            userinput tempUser = new userinput(username, password, cellPhone, firstName, lastName);
            String cellMessage = registration.validateCellPhone(tempUser);
            System.out.println(cellMessage);
        } while (!registration.checkCellPhoneNumber(cellPhone));

        // user object
        userinput newUser = new userinput(username, password, cellPhone, firstName, lastName);

        // Login section
        System.out.println("\n=== User Login ===");
        boolean loginStatus = false;
        do {
            System.out.print("Enter username: ");
            String loginUsername = sc.nextLine();
            System.out.print("Enter password: ");
            String loginPassword = sc.nextLine();
            loginStatus = login.loginUser(loginUsername, loginPassword, newUser);
            System.out.println(login.returnLoginStatus(loginStatus, newUser));
            if (!loginStatus) {
                System.out.println("Incorrect credentials. Please try again.\n");
            }
        } while (!loginStatus);

        //part two QuickChat section
      
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");
        MethodArrays methodArrays = new MethodArrays();
        methodArrays.populateTestData(); // Populate Part 3 test messages

        int choice;
        do {
            String input = JOptionPane.showInputDialog("""
                    QuickChat Menu:
                    1) Send Messages
                    2) Message Management
                    3) Quit""");

            if (input == null || input.isEmpty()) choice = 0;
            else choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> sendMessages(methodArrays);
                case 2 -> manageMessagesPart3(methodArrays);
                case 3 -> JOptionPane.showMessageDialog(null, "Goodbye! Exiting QuickChat...");
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please enter 1-3.");
            }

        } while (choice != 3);

        sc.close();
    }

    // ------------------- Part 2: Send Messages -------------------
    private static void sendMessages(MethodArrays methodArrays) {
        int numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
        for (int i = 1; i <= numMessages; i++) {
            JOptionPane.showMessageDialog(null, "Entering message " + i + " of " + numMessages);

            String recipient;
            do {
                recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27718693002):");
                if (recipient == null || !recipient.matches("^\\+\\d{9,10}$")) {
                    JOptionPane.showMessageDialog(null, "Invalid number format. Must start with '+' and have 9-10 digits after code.");
                    recipient = null;
                }
            } while (recipient == null);

            String msgText;
            do {
                msgText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
            } while (msgText.length() > 250);

            String msgID = "MSG" + (methodArrays.getSentMessages().size() + methodArrays.getRecipients().size() + 1);
            String msgHash = "HASH" + msgID;

            String[] options = {"Send Message", "Disregard Message", "Store Message"};
            int action = JOptionPane.showOptionDialog(null, "Choose an option for this message:", "Message Action",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            String status;
            switch(action) {
                case 0 -> status = "sent";
                case 1 -> status = "disregard";
                case 2 -> status = "stored";
                default -> status = "sent";
            }

            methodArrays.addMessage(status, msgID, msgHash, recipient, msgText);
            JOptionPane.showMessageDialog(null, "Message processed as " + status + ".");
        }
    }

    // ------------------- Part 3: Message Management -------------------
    private static void manageMessagesPart3(MethodArrays methodArrays) {
        int subChoice;
        do {
            String input = JOptionPane.showInputDialog("""
                    Part 3 Menu:
                    1) Display Sender & Recipient of Sent Messages
                    2) Display Longest Sent Message
                    3) Search by Message ID
                    4) Search Messages by Recipient
                    5) Delete Message by Hash
                    6) Display Sent Messages Report
                    7) Back to Main Menu""");

            if (input == null || input.isEmpty()) subChoice = 0;
            else subChoice = Integer.parseInt(input);

            switch (subChoice) {
                case 1 -> {
                    List<String> sent = methodArrays.getSentMessages();
                    List<String> rec = methodArrays.getRecipients();
                    StringBuilder sb = new StringBuilder("Sent Messages:\n");
                    for (int i = 0; i < sent.size(); i++)
                        sb.append("Recipient: ").append(rec.get(i)).append(" - Message: ").append(sent.get(i)).append("\n");
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
                case 2 -> JOptionPane.showMessageDialog(null, "Longest Sent Message: " + methodArrays.getLongestSentMessage());
                case 3 -> {
                    String id = JOptionPane.showInputDialog("Enter Message ID to search:");
                    JOptionPane.showMessageDialog(null, methodArrays.searchByMessageID(id));
                }
                case 4 -> {
                    String recipient = JOptionPane.showInputDialog("Enter recipient number to search:");
                    List<String> messages = methodArrays.searchByRecipient(recipient);
                    if (messages.isEmpty()) JOptionPane.showMessageDialog(null, "No messages found for recipient " + recipient);
                    else JOptionPane.showMessageDialog(null, "Messages to " + recipient + ":\n" + String.join("\n", messages));
                }
                case 5 -> {
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                    JOptionPane.showMessageDialog(null, methodArrays.deleteMessageByHash(hash));
                }
                case 6 -> {
                    List<String> report = methodArrays.getSentMessagesReport();
                    JOptionPane.showMessageDialog(null, String.join("\n", report));
                }
                case 7 -> JOptionPane.showMessageDialog(null, "Returning to Main Menu...");
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }

        } while (subChoice != 7);
    }
}