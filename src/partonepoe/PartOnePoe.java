/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package partonepoe;

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
        Message messageObj = new Message();
        int choice;
        do {
            String input = JOptionPane.showInputDialog("""
                    Please select an option:
                    1) Send Messages
                    2) Show Recently Sent Messages
                    3) Quit""");
            if (input == null || input.isEmpty()) {
                choice = 0;
            } else {
                choice = Integer.parseInt(input);
            }

            switch (choice) {
                case 1 -> {
                    int numMessages = Integer.parseInt(
                            JOptionPane.showInputDialog("How many messages would you like to send?"));
                    for (int i = 1; i <= numMessages; i++) {
                        JOptionPane.showMessageDialog(null, "Entering message " + i + " of " + numMessages);

                        // Recipient validation
                        String recipient;
                        do {
                            recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27718693002):");
                            if (messageObj.checkRecipientCell(recipient) != 1) {
                                JOptionPane.showMessageDialog(null,
                                        "Invalid number format. Must start with '+' and have ≤ 10 digits after code.");
                            }
                        } while (messageObj.checkRecipientCell(recipient) != 1);

                        // Message validation
                        String msgText;
                        do {
                            msgText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
                            if (msgText.length() > 250) {
                                JOptionPane.showMessageDialog(null,
                                        "Message exceeds 250 characters by " + (msgText.length() - 250) + ". Please shorten it.");
                            }
                        } while (msgText.length() > 250);

                        // message ID and hash
                        String msgID = messageObj.generateMessageID();
                        String msgHash = messageObj.createMessageHash(msgID, i, msgText);

                        // Choose send/store/disregard
                        String[] options = {"Send Message", "Disregard Message", "Store Message"};
                        int action = JOptionPane.showOptionDialog(null, "Choose an option for this message:", "Message Action",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                        String status = messageObj.sendMessageAction(action, msgID, msgHash, recipient, msgText);
                        JOptionPane.showMessageDialog(null, status);
                    }
                    JOptionPane.showMessageDialog(null, "Total Messages Sent: " + messageObj.returnTotalMessages());
                }
                case 2 -> JOptionPane.showMessageDialog(null, "Coming soon.");
                
                case 3 -> JOptionPane.showMessageDialog(null, "Goodbye! Exiting QuickChat...");
                
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please enter 1, 2, or 3.");
            }
        } while (choice != 3);

        sc.close();
    }
}


       