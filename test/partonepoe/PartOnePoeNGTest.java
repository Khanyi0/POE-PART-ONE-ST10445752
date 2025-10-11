/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package partonepoe;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_lab
 */
public class PartOnePoeNGTest {

    registration registration = new registration();
    Login login = new Login();
    userinput user = new userinput("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

    // assertEquals tests 

    @Test
    public void testUsernameCorrectlyFormatted() {
        String result = registration.registerUser(user);
        Assert.assertEquals(result, "Username successfully captured.");
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        userinput invalidUser = new userinput("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        String result = registration.registerUser(invalidUser);
        Assert.assertEquals(result,
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
    }

    @Test
    public void testPasswordMeetsComplexity() {
        String result = registration.validatePassword(user);
        Assert.assertEquals(result, "Password successfully captured.");
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        userinput weakPasswordUser = new userinput("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        String result = registration.validatePassword(weakPasswordUser);
        Assert.assertEquals(result,
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        String result = registration.validateCellPhone(user);
        Assert.assertEquals(result, "Cell phone number successfully captured.");
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        userinput invalidCellUser = new userinput("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        String result = registration.validateCellPhone(invalidCellUser);
        Assert.assertEquals(result,
                "Cell phone number incorrectly formatted or does not contain international code, please correct the number and try again.");
    }
    
    

    // assertTrue and assertFalse tests 

    @Test
    public void testLoginSuccessful() {
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!", user);
        Assert.assertTrue(result);
    }

    @Test
    public void testLoginFailed() {
        boolean result = login.loginUser("wrongUser", "wrongPass", user);
        Assert.assertFalse(result);
    }

    @Test
    public void testUsernameCheckTrue() {
        Assert.assertTrue(registration.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameCheckFalse() {
        Assert.assertFalse(registration.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordCheckTrue() {
        Assert.assertTrue(registration.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordCheckFalse() {
        Assert.assertFalse(registration.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneCheckTrue() {
        Assert.assertTrue(registration.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneCheckFalse() {
        Assert.assertFalse(registration.checkCellPhoneNumber("08966553"));
    }
}



