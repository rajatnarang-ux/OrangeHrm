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

    // ✅ Project Root Path
    protected String ROOT = System.getProperty("user.dir");

    @BeforeMethod
    public void setup() {

        System.out.println("========== JENKINS TEST STARTED ==========");

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(50)
        );

        context = browser.newContext();

        // 🔥 Start Trace Recording
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

            // 🔥 TRACE FILE in PROJECT ROOT
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(ROOT + "/trace.zip")));

            // 🔥 FINAL SCREENSHOT in PROJECT ROOT
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(ROOT + "/final-screen.png"))
                    .setFullPage(true));

            System.out.println("Proof files generated in PROJECT ROOT");

        } catch (Exception e) {
            System.out.println("Error in proof generation: " + e.getMessage());
        }

        browser.close();
        playwright.close();

        System.out.println("========== TEST FINISHED ==========");
    }
}