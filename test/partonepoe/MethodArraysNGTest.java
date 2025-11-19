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
        methodArrays.populateTestData(); // Populate Part 3 test messages
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        List<String> sentMessages = methodArrays.getSentMessages();
        Assert.assertEquals(sentMessages.size(), 2, "There should be 2 sent messages.");
        Assert.assertTrue(sentMessages.get(0).contains("Did you get the cake?"));
        Assert.assertTrue(sentMessages.get(1).contains("It is dinner time!"));
    }

    @Test
    public void testLongestSentMessage() {
        String longest = methodArrays.getLongestSentMessage();
        Assert.assertTrue(longest.contains("Did you get the cake?") || longest.contains("It is dinner time!"), "Longest sent message is incorrect.");
    }

   @Test
public void testSearchByMessageID() {
    String expectedMessage = "It is dinner time!";
    String result = methodArrays.searchByMessageID("MSG004");
    Assert.assertEquals(expectedMessage, result, "Search by Message ID failed.");
}



    @Test
    public void testSearchByRecipient() {
        List<String> results = methodArrays.searchByRecipient("+27838884567");
        Assert.assertTrue(results.stream().anyMatch(msg -> msg.contains("Where are you? You are late!")));
        Assert.assertTrue(results.stream().anyMatch(msg -> msg.contains("Ok, I am leaving without you.")));
    }

    @Test
    public void testDeleteMessageByHash() {
        String result = methodArrays.deleteMessageByHash("HASH002"); // Test Message 2
        Assert.assertTrue(result.contains("successfully deleted"));
        Assert.assertFalse(methodArrays.getStoredMessages().stream()
                .anyMatch(msg -> msg.contains("Where are you? You are late!")), "Message not removed from stored messages.");
    }

    @Test
    public void testSentMessagesReport() {
        List<String> report = methodArrays.getSentMessagesReport();
        Assert.assertTrue(report.get(0).contains("Did you get the cake?"));
        Assert.assertTrue(report.get(1).contains("It is dinner time!"));
    }
}
   