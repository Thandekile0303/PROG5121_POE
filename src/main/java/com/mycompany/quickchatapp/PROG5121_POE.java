package com.mycompany.quickchatapp;

import java.util.Scanner;

public class PROG5121_POE {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login(); // From Part 1
        MessageManager manager = new MessageManager(); // From Part 3

        // --- PART 1: REGISTRATION ---
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

        String registrationMessage = login.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println("\n" + registrationMessage);

        if (!registrationMessage.contains("successfully")) {
            System.out.println("\nRegistration failed. Exiting.");
            scanner.close();
            return;
        }

        // --- PART 1: LOGIN ---
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

        // --- PART 2 & 3: MAIN MENU ---
        boolean running = true;
        int messageCounter = 0; 

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    // PART 2: Send Message Logic
                    System.out.print("Enter recipient cell number: ");
                    String recipient = scanner.nextLine();
                    System.out.print("Enter message text: ");
                    String text = scanner.nextLine();
                    
                    messageCounter++;
                    Message newMessage = new Message(recipient, text, messageCounter);
                    System.out.println(newMessage.printMessages());
                    
                    System.out.println("1) Send Message\n2) Disregard Message\n3) Store Message");
                    System.out.print("Choice: ");
                    int sendChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    String status = newMessage.sentMessage(sendChoice);
                    System.out.println(status);
                    
                    if (sendChoice == 1) {
                        manager.addSentMessage(newMessage);
                    } else if (sendChoice == 2) {
                        manager.addDisregardedMessage(newMessage);
                    } else if (sendChoice == 3) {
                        manager.addStoredMessage(newMessage);
                    }
                    break;
                    
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                    
                case 3:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                    
                case 4:
                    storedMessagesMenu(scanner, manager); // Calls Part 3 sub-menu
                    break;
                    
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    // --- PART 3: STORED MESSAGES SUB-MENU ---
    private static void storedMessagesMenu(Scanner scanner, MessageManager manager) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search all messages for a recipient");
            System.out.println("e) Delete a message by hash");
            System.out.println("f) Display full report");
            System.out.println("x) Back to Main Menu");
            System.out.print("Choose an option: ");
            String opt = scanner.nextLine().trim().toLowerCase();

            switch (opt) {
                case "a":
                    System.out.println(manager.displayStoredSenderRecipient());
                    break;
                case "b":
                    System.out.println("Longest Message: " + manager.longestStoredMessage());
                    break;
                case "c":
                    System.out.print("Enter Message ID: ");
                    System.out.println(manager.searchByMessageID(scanner.nextLine()));
                    break;
                case "d":
                    System.out.print("Enter recipient number: ");
                    System.out.println(manager.searchByRecipient(scanner.nextLine()));
                    break;
                case "e":
                    System.out.print("Enter Message Hash: ");
                    System.out.println(manager.deleteByHash(scanner.nextLine()));
                    break;
                case "f":
                    System.out.println(manager.generateReport());
                    break;
                case "x":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}