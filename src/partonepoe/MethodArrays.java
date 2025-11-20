/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package partonepoe;


import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author RC_Student_Lab
 */
public class MethodArrays{

    
    private static class Message {
        String id;
        String hash;
        String recipient;
        String text;
        String status; // "sent", "stored", "disregard"

        Message(String id, String hash, String recipient, String text, String status) {
            this.id = id;
            this.hash = hash;
            this.recipient = recipient;
            this.text = text;
            this.status = status.toLowerCase();
        }

        String getId() {
            return id;
        }

        String getHash() {
            return hash;
        }

        String getRecipient() {
            return recipient;
        }

        String getText() {
            return text;
        }

        String getStatus() {
            return status;
        }
    }

    private final List<Message> allMessages = new ArrayList<>();

    //  Populate Test Data 
    public void populateTestData() {
        allMessages.clear();
        // SENT messages
        addMessage("MSG001", "HASH001", "+27834557896", "Did you get the cake?", "sent");
        addMessage("MSG004", "HASH004", "+2783888450", "It is dinner time!", "sent");
        // STORED messages
        addMessage("MSG002", "HASH002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "stored");
        addMessage("MSG005", "HASH005", "+27838884567", "Ok, I am leaving without you.", "stored");
        // DISREGARDED message
        addMessage("MSG003", "HASH003", "+27834484567", "Yohoooo, I am at your gate.", "disregard");
    }

    // Add Message 
    public void addMessage(String id, String hash, String recipient, String message, String status) {
        allMessages.add(new Message(id, hash, recipient, message, status));
    }

   
    public List<String> getSentMessages() {
        List<String> sentMessages = new ArrayList<>();
        for (Message message : allMessages) {
            if (message.getStatus().equals("sent")) {
                sentMessages.add(message.getText());
            }
        }
        return sentMessages;
    }

    public List<String> getStoredMessages() {
        List<String> storedMessages = new ArrayList<>();
        for (Message message : allMessages) {
            if (message.getStatus().equals("stored")) {
                storedMessages.add(message.getText());
            }
        }
        return storedMessages;
    }

    public List<String> getDisregardedMessages() {
        List<String> disregardedMessages = new ArrayList<>();
        for (Message message : allMessages) {
            if (message.getStatus().equals("disregard")) {
                disregardedMessages.add(message.getText());
            }
        }
        return disregardedMessages;
    }

    public List<String> getMessageHashes() {
        List<String> messageHashes = new ArrayList<>();
        for (Message message : allMessages) {
            messageHashes.add(message.getHash());
        }
        return messageHashes;
    }

    public List<String> getMessageIDs() {
        List<String> messageIDs = new ArrayList<>();
        for (Message message : allMessages) {
            messageIDs.add(message.getId());
        }
        return messageIDs;
    }

    // Longest Sent Message 
    public String getLongestSentMessage() {
        String longestMessage = "";
        for (Message message : allMessages) {
            if (message.getStatus().equals("sent") && message.getText().length() > longestMessage.length()) {
                longestMessage = message.getText();
            }
        }
        return longestMessage;
    }

    // Search by Message ID 
    public String searchByMessageID(String msgID) {
    for (Message m : allMessages) {
        if (m.id.equals(msgID)) {
            return m.text;   // return ONLY the raw message text
        }
    }
    return "Message ID not found.";
}

    // Search by Recipient 
    public List<String> searchByRecipient(String recipient) {
        List<String> messages = new ArrayList<>();
        for (Message message : allMessages) {
            if (message.getRecipient().equals(recipient)) {
                messages.add(message.getText());
            }
        }
        return messages;
    }

    // Delete Message by Hash 
    public String deleteMessageByHash(String messageHash) {
        for (Message message : allMessages) {
            if (message.getHash().equals(messageHash)) {
                allMessages.remove(message);
                return "Message successfully deleted";
            }
        }
        return "Message not found";
    }

    // Sent Messages Report 
    public List<String> getSentMessagesReport() {
        List<String> report = new ArrayList<>();
        for (Message message : allMessages) {
            if (message.getStatus().equals("sent")) {
                report.add("Message Hash: " + message.getHash() + ", Recipient: " + message.getRecipient() + ", Message: " + message.getText());
            }
        }
        return report;
    }
}

// Github Link:  https://github.com/Khanyi0/POE-PART-ONE-ST10445752.git