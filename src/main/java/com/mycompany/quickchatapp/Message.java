package com.mycompany.quickchatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Message class handles creation, validation, hashing, and storage of messages.
 *
 * JSON storage implemented using the Gson library:
 * Google. (2023). Gson (Version 2.10.1) [Computer software].
 * https://github.com/google/gson
 */
public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag;

    private static int totalMessagesSent = 0;
    private static final String JSON_FILE = "messages.json";

    public Message() {
    }

    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

    public static int getTotalMessagesSent() { return totalMessagesSent; }
    public static void resetTotalMessagesSent() { totalMessagesSent = 0; }

    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        String regex = "^\\+[0-9]{1,11}$";
        if (recipient.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageText == null) {
            return "";
        }
        String firstTwo = messageID.substring(0, 2);
        String number = String.valueOf(messageNumber);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");
        String hash = firstTwo + ":" + number + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String checkMessageLength() {
        if (messageText == null) {
            return "Message exceeds 250 characters by 250; please reduce the size.";
        }
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                flag = "Sent";
                totalMessagesSent++;
                return "Message successfully sent";
            case 2:
                flag = "Disregard";
                return "Press 0 to delete the message";
            case 3:
                flag = "Stored";
                storeMessage();
                return "Message successfully stored";
            default:
                return "Invalid option. Please choose 1, 2, or 3.";
        }
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText + "\n";
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Message> messages = readMessagesFromJson();
        messages.add(this);
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(messages, writer);
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public static List<Message> readMessagesFromJson() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Message>>() {}.getType();
        try (FileReader reader = new FileReader(JSON_FILE)) {
            List<Message> messages = gson.fromJson(reader, listType);
            return messages != null ? messages : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageID='" + messageID + '\'' +
                ", messageNumber=" + messageNumber +
                ", recipient='" + recipient + '\'' +
                ", messageText='" + messageText + '\'' +
                ", messageHash='" + messageHash + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
