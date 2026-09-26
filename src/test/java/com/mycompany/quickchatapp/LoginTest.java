package com.mycompany.quickchatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    
        // Unit tests for the Login class

    Login login = new Login();

    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexity() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccessful() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
    }

    @Test
    public void testRegisterUserInvalidUsernameMessage() {
        String result = login.registerUser("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976",
                                           "Kyle", "Smith");
        assertEquals("Username is not correctly formatted; please ensure that your username "
                   + "contains an underscore and is no more than five characters in length.",
                     result);
    }

    @Test
    public void testRegisterUserInvalidPasswordMessage() {
        String result = login.registerUser("kyl_1", "password", "+27838968976",
                                           "Kyle", "Smith");
        assertEquals("Password is not correctly formatted; please ensure that the password "
                   + "contains at least eight characters, a capital letter, a number, and a special character.",
                     result);
    }

    @Test
    public void testRegisterUserInvalidCellMessage() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553",
                                           "Kyle", "Smith");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.",
                     result);
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        String result = login.returnLoginStatus(true);
        assertEquals("Welcome Kyle, Smith it is great to see you again.", result);
    }

    @Test
    public void testReturnLoginStatusFailure() {
        String result = login.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", result);
    }
}