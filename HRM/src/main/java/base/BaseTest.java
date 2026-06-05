package base;

// Playwright library import kar rahe hain browser automation ke liye
import com.microsoft.playwright.*;

// TestNG annotations import kar rahe hain
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

// BaseTest class banayi gayi hai jisme common setup aur teardown code rahega
public class BaseTest {

    // Playwright engine ka object
    protected Playwright playwright;

    // Browser object (Chrome/Chromium browser ko control karega)
    protected Browser browser;

    // Browser context object (ek fresh browser session create karta hai)
    protected BrowserContext context;

    // Page object (browser tab/page ko represent karta hai)
    protected Page page;

    // @BeforeMethod har test method se pehle execute hota hai
    @BeforeMethod
    public void setup() {

        // Playwright engine initialize kar rahe hain
        playwright = Playwright.create();

        // Chromium browser launch kar rahe hain
        // setHeadless(false) ka matlab browser UI visible rahega
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        // Naya browser context create kar rahe hain
        // Ye ek fresh session ki tarah kaam karta hai
        context = browser.newContext();

        // Nayi tab/page open kar rahe hain
        page = context.newPage();

        // OrangeHRM website open kar rahe hain
        page.navigate("https://opensource-demo.orangehrmlive.com/");

        // Username input field aane tak wait karega
        // Isse ensure hota hai ki login page fully load ho gaya hai
        page.waitForSelector("input[name='username']");
    }

    // @AfterMethod har test method ke baad execute hota hai
    @AfterMethod
    public void tearDown() {

        // Browser close kar rahe hain
        browser.close();

        // Playwright engine band kar rahe hain
        playwright.close();
    }
}