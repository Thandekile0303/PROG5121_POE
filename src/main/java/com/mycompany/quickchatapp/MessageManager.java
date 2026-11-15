package com.mycompany.quickchatapp;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private ArrayList<Message> sentMessages = new ArrayList<>();
    private ArrayList<Message> disregardedMessages = new ArrayList<>();
    private ArrayList<Message> storedMessages = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();

    public MessageManager() {
        loadStoredMessagesFromJson();
    }

    public void addSentMessage(Message m) {
        sentMessages.add(m);
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageID());
    }

    public void addDisregardedMessage(Message m) {
        disregardedMessages.add(m);
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageID());
    }

    public void addStoredMessage(Message m) {
        storedMessages.add(m);
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageID());
    }

    public void loadStoredMessagesFromJson() {
        List<Message> loadedMessages = Message.readMessagesFromJson();
        for (Message msg : loadedMessages) {
            storedMessages.add(msg);
            messageHashes.add(msg.getMessageHash());
            messageIDs.add(msg.getMessageID());
        }
    }

    public String displayStoredSenderRecipient() {
        StringBuilder sb = new StringBuilder("--- Stored Messages Senders & Recipients ---\n");
        if (storedMessages.isEmpty()) return "No stored messages found.";
        for (Message m : storedMessages) {
            sb.append("Sender ID: ").append(m.getMessageID())
              .append(" | Recipient: ").append(m.getRecipient()).append("\n");
        }
        return sb.toString();
    }

    public String longestStoredMessage() {
        if (storedMessages.isEmpty()) return "No stored messages.";
        Message longest = storedMessages.get(0);
        for (Message m : storedMessages) {
            if (m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        return longest.getMessageText();
    }

    public String searchByMessageID(String id) {
        for (Message m : storedMessages) {
            if (m.getMessageID().equals(id)) {
                return "Recipient: " + m.getRecipient() + "\nMessage: " + m.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    public String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            if (m.getRecipient().equals(recipient)) {
                sb.append(m.getMessageText()).append("\n");
            }
        }
        return sb.length() == 0 ? "No messages for this recipient." : sb.toString();
    }

    public String deleteByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(hash)) {
                String id = storedMessages.get(i).getMessageID();
                String deletedText = storedMessages.get(i).getMessageText();
                storedMessages.remove(i);
                messageHashes.remove(hash);
                messageIDs.remove(id);
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    public String generateReport() {
        StringBuilder sb = new StringBuilder("=== STORED MESSAGE REPORT ===\n");
        if (storedMessages.isEmpty()) return "No stored messages to report.";
        for (Message m : storedMessages) {
            sb.append("Hash: ").append(m.getMessageHash())
              .append(" | Recipient: ").append(m.getRecipient())
              .append(" | Message: ").append(m.getMessageText()).append("\n");
        }
        return sb.toString();
    }

    public ArrayList<Message> getSentMessages() { return sentMessages; }
    public ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<Message> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIDs() { return messageIDs; }
}