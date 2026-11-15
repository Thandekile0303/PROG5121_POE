package com.mycompany.quickchatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageManagerTest {
    private MessageManager manager;

    @BeforeEach
    public void setUp() {
        manager = new MessageManager();
        manager.getSentMessages().clear();
        manager.getStoredMessages().clear();
        manager.getDisregardedMessages().clear();
        
        manager.addSentMessage(new Message("+27834557896", "Did you get the cake?", 1));
        manager.addStoredMessage(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2));
        manager.addDisregardedMessage(new Message("+27834484567", "Yohoooo, I am at your gate.", 3));
        manager.addSentMessage(new Message("0838884567", "It is dinner time !", 4));
        manager.addStoredMessage(new Message("+27838884567", "Ok, I am leaving without you.", 5));
    }

    // ... [The rest of the test methods go here] ...
}