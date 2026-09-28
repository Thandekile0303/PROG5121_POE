package com.mycompany.quickchatapp;

/**
 * Login class - handles user registration and authentication.
 * 
 * See REFERENCES section at the bottom of this file.
 * 
 * @author Thandekile Mntungwa
 */


public class Login {

    // Handles user registration and authentication
  
    
    private String storedUsername;
    private String storedPassword;
    private String storedCellPhone;
    private String firstName;
    private String lastName;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }
/**
 * Checks that the cell phone number contains the international 
 * country code (+27) followed by exactly 9 digits.
 * 
 * Regex pattern based on [1] and [2] (see REFERENCES at bottom).
 */
    public boolean checkCellPhoneNumber(String cellPhone) {
        String regex = "^\\+27[0-9]{9}$";
        return cellPhone.matches(regex);
    }

    public String registerUser(String username, String password, String cellPhone,
                               String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username "
                 + "contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password "
                 + "contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this.storedUsername = username;
        this.storedPassword = password;
        this.storedCellPhone = cellPhone;
        this.firstName = firstName;
        this.lastName = lastName;

        {
            return "Username successfully captured.\n"
                 + "Password successfully captured.\n"
                 + "Cell phone number successfully added.";
        } 
    }

    public boolean loginUser(String username, String password) {
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    public String getStoredCellPhone() {
        return storedCellPhone;
    }
}
// ============================================================
// REFERENCES
// ============================================================
// [1] Oracle, "Pattern (Java Platform SE 8)," Oracle Documentation.
//     [Online]. Available:
//     https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html.
//     [Accessed: Sep. 28, 2026].
//
// [2] "Regex to check South African phone numbers," Stack Overflow.
//     [Online]. Available:
//     https://stackoverflow.com/questions/4210450/.
//     [Accessed: Sep. 28, 2026].
// ============================================================