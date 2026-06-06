package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.nio.file.Paths;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {

        LoginPage loginPage = new LoginPage(page);

        loginPage.login("Admin", "admin123");

        System.out.println("Login Success");

        // 📸 LOGIN SCREENSHOT
        page.screenshot(new com.microsoft.playwright.Page.ScreenshotOptions()
                .setPath(Paths.get(ROOT + "/login-success.png"))
                .setFullPage(true));
    }
}