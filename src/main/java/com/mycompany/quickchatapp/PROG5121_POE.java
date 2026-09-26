package com.mycompany.quickchatapp;

import java.util.Scanner;

public class PROG5121_POE {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== REGISTRATION ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number (+27...): ");
        String cellPhone = scanner.nextLine();

        String registrationMessage = login.registerUser(username, password, cellPhone,
                                                        firstName, lastName);
        System.out.println(registrationMessage);

        System.out.println("\n=== LOGIN ===");

        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        boolean loginSuccess = login.loginUser(loginUsername, loginPassword);
        String loginMessage = login.returnLoginStatus(loginSuccess);
        System.out.println(loginMessage);

        scanner.close();
    }
}