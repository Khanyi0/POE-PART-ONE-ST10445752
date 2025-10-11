/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package partonepoe;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author RC_Student_Lab
 */
public class Message {
  
   private int totalMessagesSent = 0;
    private final String FILE_NAME = "messages.json";

    public String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + (long) (random.nextDouble() * 8999999999L);
        return String.valueOf(id);
    }

    public int checkRecipientCell(String number) {
        return (number.startsWith("+") && number.length() <= 13) ? 1 : 0;
    }

    public String createMessageHash(String msgID, int msgNum, String msgText) {
        String firstWord = "", lastWord = "";
        if (!msgText.isBlank()) {
            String[] words = msgText.trim().split(" ");
            firstWord = words[0].toUpperCase();
            lastWord = words[words.length - 1].toUpperCase();
        }
        String firstTwo = msgID.substring(0, 2);
        return firstTwo + ":" + msgNum + ":" + firstWord + lastWord;
    }

    public String sendMessageAction(int action, String msgID, String hash, String recipient, String msgText) {
        switch (action) {
            case 0 -> { // Send
                totalMessagesSent++;
                JOptionPane.showMessageDialog(null,
                        "Message Sent Successfully!\n\n" +
                                "Message ID: " + msgID + "\n" +
                                "Message Hash: " + hash + "\n" +
                                "Recipient: " + recipient + "\n" +
                                "Message: " + msgText);
                return "Message successfully sent.";
            }
            case 1 -> { // Disregard
                return "Message disregarded.";
            }
            case 2 -> { // Store
                storeMessage(msgID, hash, recipient, msgText);
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid action.";
            }
        }
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    @SuppressWarnings("unchecked")
    public void storeMessage(String msgID, String hash, String recipient, String msgText) {
        JSONObject msgObj = new JSONObject();
        msgObj.put("MessageID", msgID);
        msgObj.put("MessageHash", hash);
        msgObj.put("Recipient", recipient);
        msgObj.put("Message", msgText);

        JSONArray messageArray;
        
        

        // Load existing messages if file exists
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_NAME));
            messageArray = (JSONArray) obj;
        } catch (Exception e) {
            messageArray = new JSONArray(); // start new array if file doesn't exist
        }

        messageArray.add(msgObj);

        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(messageArray.toJSONString());
            file.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving message: " + e.getMessage());
        }
        
    }
}