package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    // ✅ Login method
    public void login(String username, String password) {

        page.fill("input[name='username']", username);
        page.fill("input[name='password']", password);

        page.click("button[type='submit']");

        // optional wait after login
        page.waitForTimeout(2000);
    }
}