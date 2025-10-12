/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package partonepoe;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageNGTest {
    
    private Message message;

    @BeforeMethod
    public void setUp() {
        // Initialize the Message object before each test
        message = new Message();
        System.out.println("\n--- Starting new test ---");
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("--- Test completed ---\n");
    }

    // Valid recipient number
    @Test
    public void testRecipientNumberCorrectlyFormatted() {
        int result = message.checkRecipientCell("+27718693002");
        Assert.assertEquals(result, 1, "Should accept correctly formatted recipient number.");
    }

    //Invalid recipient number (missing '+')
    @Test
    public void testRecipientNumberIncorrectlyFormatted() {
        int result = message.checkRecipientCell("08575975889");
        Assert.assertEquals(result, 0, "Should reject invalid number without '+'.");
    }

    //Message within character limit (under 250)
    @Test
    public void testMessageWithinCharacterLimit() {
        String msg = "Hi Mike, can you join us for dinner tonight";
        Assert.assertTrue(msg.length() <= 250, "Message ready to send.");
    }

    //Message exactly 250 characters (boundary test)
    @Test
    public void testMessageExactly250Characters() {
        String msg = "a".repeat(250);
        Assert.assertEquals(msg.length(), 250, "Message should be exactly 250 characters.");
    }

    // Message exceeds character limit
    @Test
    public void testMessageExceedsCharacterLimit() {
        String msg = "a".repeat(260);
        int excess = msg.length() - 250;
        Assert.assertTrue(excess > 0, "Message exceeds 250 characters by " + excess);
    }

    // Message ID and hash generation
    @Test
    public void testMessageHashGeneratedCorrectly() {
        String msgID = "0012345678";
        String msgText = "Hi Mike, can you join us for dinner tonight";
        String hash = message.createMessageHash(msgID, 0, msgText);
        Assert.assertTrue(hash.contains("00:0:HITONIGHT"), "Expected format 00:0:HITONIGHT");
    }

    // Message ID length validation
    @Test
    public void testMessageIDLength() {
        String msgID = message.generateMessageID();
        Assert.assertTrue(msgID.length() <= 10, "Message ID should be 10 digits.");
    }

    //Send message action - SEND
    @Test
    public void testSendMessageActionSend() {
        String response = message.sendMessageAction(0, "0012345678", "00:1:HITEST", "+27718693002", "Hi Test");
        Assert.assertEquals(response, "Message successfully sent.");
    }

    //Send message action - DISREGARD
    @Test
    public void testSendMessageActionDisregard() {
        String response = message.sendMessageAction(1, "0012345678", "00:1:HITEST", "+27718693002", "Hi Test");
        Assert.assertEquals(response, "Message disregarded.");
    }

    // Send message action - STORE
    @Test
    public void testSendMessageActionStore() {
        String response = message.sendMessageAction(2, "0012345678", "00:1:HITEST", "+27718693002", "Hi Test");
        Assert.assertEquals(response, "Message successfully stored.");
    }

    // Total messages counter increases after sending
    @Test
    public void testTotalMessagesCount() {
        int before = message.returnTotalMessages();
        message.sendMessageAction(0, "0012345678", "00:1:HITEST", "+27718693002", "Hi Test");
        int after = message.returnTotalMessages();
        Assert.assertTrue(after > before, "Total messages should increase after sending.");
    }

    //Null message text handling
    @Test
    public void testNullMessageText() {
        String response = message.sendMessageAction(0, "0012345678", "00:1:HITEST", "+27718693002", null);
        Assert.assertNotNull(response, "System should handle null message text gracefully.");
    }

    //Empty message text handling
    @Test
    public void testEmptyMessageText() {
        String response = message.sendMessageAction(0, "0012345678", "00:1:HITEST", "+27718693002", "");
        Assert.assertNotNull(response, "System should handle empty message text gracefully.");
    }
}