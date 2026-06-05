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

    @BeforeMethod
    public void setup() {

        System.out.println("========== JENKINS TEST STARTED ==========");

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)   // UI visible (local run)
                        .setSlowMo(50)
        );

        context = browser.newContext();

        // 🔥 START TRACE (PROOF)
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
            // 🔥 STOP TRACE (PROOF FILE)
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("target/trace.zip")));

            // 🔥 Screenshot final proof
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/final-screen.png"))
                    .setFullPage(true));

            System.out.println("Proof files generated: trace.zip + screenshot");

        } catch (Exception e) {
            System.out.println("Error in proof generation: " + e.getMessage());
        }

        browser.close();
        playwright.close();

        System.out.println("========== TEST FINISHED ==========");
    }
}