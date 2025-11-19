/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package partonepoe;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



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

        // Format message for stored/disregarded display
        String getFormattedForStorage() {
            return "Recipient: " + recipient + ", Message: " + text;
        }

        // Format message including sender (for sent messages)
        String getFormattedWithSender() {
            return "Sender: Developer, Recipient: " + recipient + ", Message: " + text;
        }
    }

    // -------------------- List to hold all messages --------------------
    private final List<Message> allMessages = new ArrayList<>();

    // -------------------- Populate Test Data --------------------
    public void populateTestData() {
        allMessages.clear();

        // SENT messages
        addMessage("sent", "MSG001", "HASH001", "+27834557896", "Did you get the cake?");
        addMessage("sent", "MSG004", "HASH004", "+27838884567", "It is dinner time!");

        // STORED messages
        addMessage("stored", "MSG002", "HASH002", "+27838884567",
                   "Where are you? You are late! I have asked you to be on time.");
        addMessage("stored", "MSG005", "HASH005", "+27838884567",
                   "Ok, I am leaving without you.");

        // DISREGARDED message
        addMessage("disregard", "MSG003", "HASH003", "+27834484567", "Yohoooo, I am at your gate.");
    }

    // -------------------- Add Message --------------------
    public void addMessage(String status, String msgID, String hash, String recipient, String message) {
        allMessages.add(new Message(msgID, hash, recipient, message, status));
    }

    // -------------------- Getters --------------------
    public List<String> getSentMessages() {
        List<String> result = new ArrayList<>();
        for (Message m : allMessages) {
            if ("sent".equals(m.status)) result.add(m.getFormattedWithSender());
        }
        return result;
    }

    public List<String> getStoredMessages() {
        List<String> result = new ArrayList<>();
        for (Message m : allMessages) {
            if ("stored".equals(m.status)) result.add(m.getFormattedForStorage());
        }
        return result;
    }

    public List<String> getDisregardedMessages() {
        List<String> result = new ArrayList<>();
        for (Message m : allMessages) {
            if ("disregard".equals(m.status)) result.add(m.getFormattedForStorage());
        }
        return result;
    }

    public List<String> getRecipients() {
        List<String> result = new ArrayList<>();
        for (Message m : allMessages) result.add(m.recipient);
        return result;
    }

    public List<String> getMessageHashes() {
        List<String> result = new ArrayList<>();
        for (Message m : allMessages) result.add(m.hash);
        return result;
    }

    public List<String> getMessageIDs() {
        List<String> result = new ArrayList<>();
        for (Message m : allMessages) result.add(m.id);
        return result;
    }

    // -------------------- Get Longest Sent Message --------------------
    public String getLongestSentMessage() {
        Message longestMsg = null;

        for (Message m : allMessages) {
            if ("sent".equals(m.status)) {
                if (longestMsg == null || m.text.length() > longestMsg.text.length()) {
                    longestMsg = m;
                }
            }
        }

        return (longestMsg != null) ? longestMsg.getFormattedWithSender() : "";
    }

    // -------------------- Search by Message ID --------------------
    public String searchByMessageID(String msgID) {
        Optional<Message> opt = allMessages.stream()
                .filter(m -> m.id.equals(msgID))
                .findFirst();

        return opt.map(m -> m.text).orElse("Message ID not found.");
    }

    // -------------------- Search by Recipient --------------------
    public List<String> searchByRecipient(String recipient) {
        List<String> results = new ArrayList<>();
        for (Message m : allMessages) {
            if (recipient.equals(m.recipient)) {
                if ("sent".equals(m.status)) results.add(m.getFormattedWithSender());
                else results.add(m.getFormattedForStorage());
            }
        }
        return results;
    }

    // -------------------- Delete Message by Hash --------------------
    public String deleteMessageByHash(String hash) {
        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).hash.equals(hash)) {
                allMessages.remove(i);
                return "Message with hash " + hash + " successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // -------------------- Sent Messages Report --------------------
    public List<String> getSentMessagesReport() {
        List<String> report = new ArrayList<>();
        for (Message m : allMessages) {
            if ("sent".equals(m.status)) {
                report.add("Message ID: " + m.id + ", Hash: " + m.hash + ", " + m.getFormattedWithSender());
            }
        }
        return report;
    }
}
