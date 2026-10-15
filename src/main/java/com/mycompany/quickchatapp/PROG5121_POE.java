package com.mycompany.quickchatapp;

import java.util.Scanner;

/**
 * Main entry point for the QuickChat application (Part 2).
 */
public class PROG5121_POE {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== QuickChat Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username (must contain '_' and be ≤ 5 chars): ");
        String username = scanner.nextLine();

        System.out.print("Enter password (≥ 8 chars, capital, number, special): ");
        String password = scanner.nextLine();

        System.out.print("Enter SA cell phone number (e.g. +27838968976): ");
        String cellPhone = scanner.nextLine();

        String registrationMessage = login.registerUser(
                username, password, cellPhone, firstName, lastName);
        System.out.println("\n" + registrationMessage);

        if (!registrationMessage.contains("successfully")) {
            System.out.println("\nRegistration failed. Exiting.");
            scanner.close();
            return;
        }

        System.out.println("\n=== QuickChat Login ===");
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        String loginStatus = login.returnLoginStatus(loginUsername, loginPassword);
        System.out.println("\n" + loginStatus);

        if (!login.loginUser(loginUsername, loginPassword)) {
            System.out.println("\nLogin failed. Exiting.");
            scanner.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages do you wish to enter? ");
        int numMessages;
        try {
            numMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Exiting.");
            scanner.close();
            return;
        }

        boolean running = true;
        int messagesEntered = 0;

        while (running) {
            System.out.println("\n=== QuickChat Menu ===");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (messagesEntered >= numMessages) {
                        System.out.println("You have already entered the maximum number of messages ("
                                + numMessages + ").");
                        break;
                    }

                    for (int i = messagesEntered; i < numMessages; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");

                        System.out.print("Enter recipient cell number (e.g. +27718693002): ");
                        String recipient = scanner.nextLine().trim();

                        System.out.print("Enter message (max 250 characters): ");
                        String messageText = scanner.nextLine();

                        Message message = new Message(recipient, messageText, i);

                        String recipientCheck = message.checkRecipientCell();
                        System.out.println(recipientCheck);

                        String lengthCheck = message.checkMessageLength();
                        System.out.println(lengthCheck);

                        if (!lengthCheck.equals("Message ready to send.")) {
                            System.out.println("Message not sent due to length. Please try again.");
                            i--;
                            continue;
                        }

                        System.out.println("\n--- Message Details ---");
                        System.out.println(message.printMessages());

                        System.out.println("Choose an option:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Enter choice: ");

                        int action;
                        try {
                            action = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid option. Message disregarded.");
                            action = 2;
                        }

                        String result = message.sentMessage(action);
                        System.out.println(result);

                        if (action == 1) {
                            System.out.println("\n--- Message Sent Successfully ---");
                            System.out.println(message.printMessages());
                        } else if (action == 3) {
                            System.out.println("\n--- Message Stored Successfully ---");
                            System.out.println(message.printMessages());
                        }

                        messagesEntered++;
                    }

                    System.out.println("\nTotal messages sent: " + Message.getTotalMessagesSent());
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}