/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package partonepoe;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



/**
 *
 * @author RC_Student_Lab
 */
public class MessageManager {
    private final String FILE_NAME = "messages.json"; // same as Message class

    // Arrays required by Part 3
    private List<JSONObject> sentMessages = new ArrayList<>();
    private List<JSONObject> disregardedMessages = new ArrayList<>();
    private List<JSONObject> storedMessages = new ArrayList<>(); // loaded from JSON file
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIDs = new ArrayList<>();

    public MessageManager() {
        loadStoredMessagesFromFile();
    }

    // Call when a message was sent during the session
    public void addSentMessage(String msgID, String hash, String recipient, String messageText, String sender) {
        JSONObject obj = new JSONObject();
        obj.put("MessageID", msgID);
        obj.put("MessageHash", hash);
        obj.put("Recipient", recipient);
        obj.put("Message", messageText);
        obj.put("Sender", sender == null ? "Unknown" : sender);

        sentMessages.add(obj);
        messageHashes.add(hash);
        messageIDs.add(msgID);
    }

    // Call when user disregards a message during the session
    public void addDisregardedMessage(String msgID, String hash, String recipient, String messageText, String sender) {
        JSONObject obj = new JSONObject();
        obj.put("MessageID", msgID);
        obj.put("MessageHash", hash);
        obj.put("Recipient", recipient);
        obj.put("Message", messageText);
        obj.put("Sender", sender == null ? "Unknown" : sender);

        disregardedMessages.add(obj);
        messageHashes.add(hash);
        messageIDs.add(msgID);
    }

    // Save session-stored messages to the global JSON file (append)
    @SuppressWarnings("unchecked")
    public void persistStoredMessage(JSONObject messageObject) {
        // append to messages.json
        try {
            JSONParser parser = new JSONParser();
            JSONArray arr;
            try (FileReader fr = new FileReader(FILE_NAME)) {
                Object obj = parser.parse(fr);
                arr = (JSONArray) obj;
            } catch (Exception e) {
                arr = new JSONArray();
            }

            arr.add(messageObject);

            try (FileWriter fw = new FileWriter(FILE_NAME)) {
                fw.write(arr.toJSONString());
                fw.flush();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error persisting message: " + e.getMessage());
        }
    }

    // Loads messages.json into storedMessages list
    @SuppressWarnings("unchecked")
    public void loadStoredMessagesFromFile() {
        storedMessages.clear();
        try (FileReader reader = new FileReader(FILE_NAME)) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONArray array = (JSONArray) obj;
            for (Object o : array) {
                storedMessages.add((JSONObject) o);
            }
        } catch (Exception e) {
            // file missing or empty -> no stored messages
        }
    }

    // a) Display the sender and recipient of all sent messages.
    public List<String> getSenderRecipientPairs() {
        List<String> pairs = new ArrayList<>();
        for (JSONObject obj : sentMessages) {
            String sender = (String) obj.getOrDefault("Sender", "Unknown");
            String recipient = (String) obj.getOrDefault("Recipient", "Unknown");
            pairs.add(sender + " -> " + recipient);
        }
        return pairs;
    }

    // b) Display the longest sent message
    public Optional<String> getLongestSentMessage() {
        return sentMessages.stream()
                .map(o -> (String) o.getOrDefault("Message", ""))
                .max(Comparator.comparingInt(String::length));
    }

    // c) Search for a message ID and display corresponding recipient and message
    public Optional<JSONObject> searchByMessageID(String msgID) {
        for (JSONObject obj : sentMessages) {
            if (msgID.equals(obj.get("MessageID"))) return Optional.of(obj);
        }
        for (JSONObject obj : storedMessages) {
            if (msgID.equals(obj.get("MessageID"))) return Optional.of(obj);
        }
        for (JSONObject obj : disregardedMessages) {
            if (msgID.equals(obj.get("MessageID"))) return Optional.of(obj);
        }
        return Optional.empty();
    }

    // d) Search for all messages sent to a particular recipient (sent + stored)
    public List<JSONObject> searchByRecipient(String recipient) {
        List<JSONObject> results = new ArrayList<>();
        for (JSONObject obj : sentMessages) {
            if (recipient.equals(obj.get("Recipient"))) results.add(obj);
        }
        for (JSONObject obj : storedMessages) {
            if (recipient.equals(obj.get("Recipient"))) results.add(obj);
        }
        for (JSONObject obj : disregardedMessages) {
            if (recipient.equals(obj.get("Recipient"))) results.add(obj);
        }
        return results;
    }

    // e) Delete a message using the message hash (will delete from storedMessages and from file if found)
    @SuppressWarnings("unchecked")
    public boolean deleteMessageByHash(String hash) {
        // Remove from session lists
        boolean removed = sentMessages.removeIf(o -> hash.equals(o.get("MessageHash")));
        removed = disregardedMessages.removeIf(o -> hash.equals(o.get("MessageHash"))) || removed;

        // Remove from storedMessages in memory
        boolean removedFromStoredMemory = storedMessages.removeIf(o -> hash.equals(o.get("MessageHash")));
        removed = removed || removedFromStoredMemory;

        if (removedFromStoredMemory) {
            // rewrite messages.json without the deleted entry
            try {
                JSONArray arr = new JSONArray();
                // load existing file, filter out
                JSONParser parser = new JSONParser();
                try (FileReader fr = new FileReader(FILE_NAME)) {
                    Object obj = parser.parse(fr);
                    JSONArray fileArr = (JSONArray) obj;
                    for (Object o : fileArr) {
                        JSONObject jo = (JSONObject) o;
                        if (!hash.equals(jo.get("MessageHash"))) arr.add(jo);
                    }
                }
                try (FileWriter fw = new FileWriter(FILE_NAME)) {
                    fw.write(arr.toJSONString());
                    fw.flush();
                }
            } catch (Exception e) {
                // silent fail; return true because we removed in-memory
            }
        }

        return removed;
    }

    // f) Display a report that lists the full details of all the sent messages.
    public List<String> getSentMessagesReport() {
        List<String> report = new ArrayList<>();
        for (JSONObject obj : sentMessages) {
            String line = String.format("MessageHash: %s | Recipient: %s | Message: %s",
                    obj.get("MessageHash"), obj.get("Recipient"), obj.get("Message"));
            report.add(line);
        }
        return report;
    }

    // helper: expose arrays for unit tests
    public List<JSONObject> getSentMessagesList() { return sentMessages; }
    public List<JSONObject> getDisregardedMessagesList() { return disregardedMessages; }
    public List<JSONObject> getStoredMessagesList() { return storedMessages; }
    public List<String> getMessageHashesList() { return messageHashes; }
    public List<String> getMessageIDsList() { return messageIDs; }

}

