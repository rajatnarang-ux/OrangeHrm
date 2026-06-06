package base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Paths;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    // ✅ FINAL OUTPUT FOLDER
    protected String ROOT = "C:/Users/HP/Downloads/HRM/HRM/output";

    @BeforeMethod
    public void setup() {

        System.out.println("========== JENKINS TEST STARTED ==========");

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true)
                        .setSlowMo(50)
        );

        context = browser.newContext();

        // 🔥 TRACE START
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();

        page.navigate("https://opensource-demo.orangehrmlive.com/");

        page.waitForSelector("input[name='username']");

        System.out.println("Browser launched + OrangeHRM opened");
    }

    @AfterMethod
    public void tearDown() {

        try {
            // 🔥 TRACE FILE
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(ROOT + "/trace.zip")));

            // 📸 FINAL SCREENSHOT
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(ROOT + "/final-screen.png"))
                    .setFullPage(true));

            context.close();
            browser.close();
            playwright.close();

            System.out.println("✅ Proof saved in: " + ROOT);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("========== TEST FINISHED ==========");
    }
}