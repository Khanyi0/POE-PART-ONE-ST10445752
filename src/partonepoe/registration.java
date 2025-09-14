/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package partonepoe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RC_Student_lab
 */
public class registration {
    // Checks if username contains underscore and <= 5 chars
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks password complexity: >=8 chars, uppercase, number, special char
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8)
            return false;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    // Checks if cell phone matches +27XXXXXXXXX
    public boolean checkCellPhoneNumber(String cellPhone) {
        String regex = "^\\+27\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellPhone);
        return matcher.matches();
    }

    // Registration method for username
    public String registerUser(userinput user) {
        if (!checkUserName(user.getUsername())) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        } else {
            return "Username successfully captured.";
        }
    }

    // Separate method for password message
    public String validatePassword(userinput user) {
        if (!checkPasswordComplexity(user.getPassword())) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        } else {
            return "Password successfully captured.";
        }
    }

    // Separate method for cell phone message
    public String validateCellPhone(userinput user) {
        if (!checkCellPhoneNumber(user.getCellPhone())) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        } else {
            return "Cell phone number successfully added.";
        }
    }
}
    

