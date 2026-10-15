package com.mycompany.quickchatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message class (Part 2).
 */
public class MessageTest {

    @BeforeEach
    public void setUp() {
        Message.resetTotalMessagesSent();
    }

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        StringBuilder longMsg = new StringBuilder();
        for (int i = 0; i < 260; i++) longMsg.append("a");
        Message msg = new Message("+27718693002", longMsg.toString(), 0);
        int excess = longMsg.length() - 250;
        assertEquals(
                "Message exceeds 250 characters by " + excess + "; please reduce the size.",
                msg.checkMessageLength());
    }

    @Test
    public void testRecipientCorrectlyFormatted() {
        Message msg = new Message("+27718693002", "Hello", 0);
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientIncorrectlyFormatted() {
        Message msg = new Message("08575975889", "Hello", 0);
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    @Test
    public void testMessageHashFormat() {
        Message msg = new Message("+27718693002", "Hi Mike", 5);
        String hash = msg.createMessageHash();
        String[] parts = hash.split(":");
        assertEquals(3, parts.length, "Hash should have 3 parts separated by ':'");
        assertEquals("5", parts[1], "Second part should be the message number");
        assertEquals(hash, hash.toUpperCase(), "Hash should be uppercase");
    }

    @Test
    public void testMessageHashEndsWithFirstAndLastWords() {
        Message msg = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        String hash = msg.createMessageHash();
        assertTrue(hash.endsWith("HITONIGHT"),
                "Hash should end with HITONIGHT but was: " + hash);
    }

    @Test
    public void testSentMessageSend() {
        Message msg = new Message("+27718693002", "Hello", 0);
        assertEquals("Message successfully sent", msg.sentMessage(1));
        assertEquals(1, Message.getTotalMessagesSent());
    }

    @Test
    public void testSentMessageDisregard() {
        Message msg = new Message("+27718693002", "Hello", 0);
        assertEquals("Press 0 to delete the message", msg.sentMessage(2));
    }

    @Test
    public void testSentMessageStore() {
        Message msg = new Message("+27718693002", "Hello", 0);
        assertEquals("Message successfully stored", msg.sentMessage(3));
    }

    @Test
    public void testCheckMessageID() {
        Message msg = new Message("+27718693002", "Hello", 0);
        assertTrue(msg.checkMessageID(), "Message ID should be ≤ 10 characters");
    }

    @Test
    public void testReturnTotalMessages() {
        Message msg1 = new Message("+27718693002", "Hello", 0);
        Message msg2 = new Message("+27718693002", "World", 1);
        msg1.sentMessage(1);
        msg2.sentMessage(1);
        assertEquals(2, msg1.returnTotalMessages());
    }
}