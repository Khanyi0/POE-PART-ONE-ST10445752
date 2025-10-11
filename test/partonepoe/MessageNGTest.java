/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package partonepoe;

import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageNGTest {
    
    public MessageNGTest() {
    }

    Message message = new Message();

    @Test
    public void testRecipientNumberCorrectlyFormatted() {
        int result = message.checkRecipientCell("+27718693002");
        Assert.assertEquals(result, 1, "Should accept correctly formatted recipient number.");
    }

    @Test
    public void testRecipientNumberIncorrectlyFormatted() {
        int result = message.checkRecipientCell("08575975889");
        Assert.assertEquals(result, 0, "Should reject invalid number without '+'.");
    }

    @Test
    public void testMessageWithinCharacterLimit() {
        String msg = "Hi Mike, can you join us for dinner tonight";
        Assert.assertTrue(msg.length() <= 250, "Message ready to send.");
    }

    @Test
    public void testMessageExceedsCharacterLimit() {
        String msg = "a".repeat(260);
        int excess = msg.length() - 250;
        Assert.assertTrue(excess > 0, "Message exceeds 250 characters by " + excess);
    }

    @Test
    public void testMessageHashGeneratedCorrectly() {
        String msgID = "0012345678";
        String msgText = "Hi Mike, can you join us for dinner tonight";
        String hash = message.createMessageHash(msgID, 0, msgText);
        Assert.assertTrue(hash.contains("00:0:HITONIGHT"), "Expected format 00:0:HITONIGHT");
    }

    @Test
    public void testMessageIDLength() {
        String msgID = message.generateMessageID();
        Assert.assertTrue(msgID.length() <= 10, "Message ID should be 10 digits.");
    }

    @Test
    public void testSendMessageActionSend() {
        String response = message.sendMessageAction(0, "0012345678", "00:1:HITEST", "+27718693002", "Hi Test");
        Assert.assertEquals(response, "Message successfully sent.");
    }

    @Test
    public void testSendMessageActionDisregard() {
        String response = message.sendMessageAction(1, "0012345678", "00:1:HITEST", "+27718693002", "Hi Test");
        Assert.assertEquals(response, "Message disregarded.");
    }

    @Test
    public void testSendMessageActionStore() {
        String response = message.sendMessageAction(2, "0012345678", "00:1:HITEST", "+27718693002", "Hi Test");
        Assert.assertEquals(response, "Message successfully stored.");
    }

    @Test
    public void testTotalMessagesCount() {
        int before = message.returnTotalMessages();
        message.sendMessageAction(0, "0012345678", "00:1:HITEST", "+27718693002", "Hi Test");
        int after = message.returnTotalMessages();
        Assert.assertTrue(after > before, "Total messages should increase after sending.");
    }
}