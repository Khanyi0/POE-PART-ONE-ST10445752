/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package partonepoe;

import java.util.Scanner;

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
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Enter cell phone number (e.g. +27733814684): ");
        String cellPhone = sc.nextLine();

        userinput newUser = new userinput(username, password, cellPhone, firstName, lastName);

        // Validate username
        String usernameMessage = registration.registerUser(newUser);
        System.out.println(usernameMessage);
        if (!usernameMessage.equals("Username successfully captured.")) {
            System.out.println("Please restart and try again.");
            sc.close();
            return;
        }

        // Validate password
        String passwordMessage = registration.validatePassword(newUser);
        System.out.println(passwordMessage);
        if (!passwordMessage.equals("Password successfully captured.")) {
            System.out.println("Please restart and try again.");
            sc.close();
            return;
        }

        
        String cellMessage = registration.validateCellPhone(newUser);
        System.out.println(cellMessage);
        if (!cellMessage.equals("Cell phone number successfully added.")) {
            System.out.println("Please restart and try again.");
            sc.close();
            return;
        }

        // Login section
        System.out.println("\n=== User Login ===");
        System.out.print("Enter username: ");
        String loginUsername = sc.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = sc.nextLine();

        boolean loginStatus = login.loginUser(loginUsername, loginPassword, newUser);
        System.out.println(login.returnLoginStatus(loginStatus, newUser));

        sc.close();
    }
}
     
    
    

