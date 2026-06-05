package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {

        LoginPage loginPage = new LoginPage(page);

        loginPage.login("Admin", "admin123");

        // optional verify
        System.out.println("Title after login: " + page.title());
    }
}