/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package partonepoe;

/**
 *
 * @author RC_Student_lab
 */
public class Login {
    // Returns true if username & password match the user object
    public boolean loginUser(String username, String password, userinput user) {
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

    public String returnLoginStatus(boolean loginStatus, userinput user) {
        if (loginStatus) {
            return "Welcome " + user.getFirstName() + " ," + user.getLastName() + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
    
    

