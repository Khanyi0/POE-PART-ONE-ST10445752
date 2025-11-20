/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package partonepoe;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class MethodArraysNGTest {
    
private MethodArrays methodArrays;

    @BeforeMethod
    public void setUp() {
        methodArrays = new MethodArrays();
        methodArrays.populateTestData(); // populate test messages
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        List<String> sentMessages = methodArrays.getSentMessages();
        Assert.assertEquals(sentMessages.size(), 2, "There should be 2 sent messages.");
        Assert.assertTrue(sentMessages.contains("Did you get the cake?"));
        Assert.assertTrue(sentMessages.contains("It is dinner time!"));
    }

    @Test
    public void testLongestSentMessage() {
        String longest = methodArrays.getLongestSentMessage();
        Assert.assertTrue(longest.equals("Did you get the cake?") || longest.equals("It is dinner time!"),
                "Longest sent message is incorrect.");
    }

    @Test
    public void testSearchByMessageID() {
        String expectedMessage = "It is dinner time!";
        String result = methodArrays.searchByMessageID("MSG004");
        Assert.assertEquals(result, expectedMessage, "Search by Message ID failed.");
    }

    @Test
    public void testSearchByRecipient() {
        List<String> results = methodArrays.searchByRecipient("+27838884567");
        Assert.assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        Assert.assertTrue(results.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageByHash() {
        // Using iterator-safe deletion
        String result = methodArrays.deleteMessageByHash("HASH002"); // Delete stored message
        Assert.assertTrue(result.contains("successfully deleted"), "Delete operation failed");

        List<String> stored = methodArrays.getStoredMessages();
        Assert.assertFalse(stored.contains("Where are you? You are late! I have asked you to be on time."),
                "Message not removed from stored messages.");
    }

    @Test
    public void testSentMessagesReport() {
        List<String> report = methodArrays.getSentMessagesReport();
        Assert.assertTrue(report.stream().anyMatch(s -> s.contains("Did you get the cake?")));
        Assert.assertTrue(report.stream().anyMatch(s -> s.contains("It is dinner time!")));
    }
}