package Runner;
//import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.net.URL;
@CucumberOptions(
features = "src/test/java/Features/appFeature/app.feature",
glue = {"stepDefinitions"}
)
public class appRunner {
    public TestNGCucumberRunner testNGCucumberRunner;
    public static AndroidDriver<AndroidElement> connection;

//    @BeforeClass(alwaysRun = true)
//    public void setUpCucumber() {
//        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
//        System.out.println("Cucumber runner initialized");
//    }

    @BeforeMethod(alwaysRun = true)
    public void setUpClass() throws Exception {
        System.out.println("Starting setup");
        String username = System.getenv("LT_USERNAME") == null ? "YOUR LT_USERNAME" : System.getenv("LT_USERNAME");
        String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "YOUR LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");

        DesiredCapabilities capability = new DesiredCapabilities();

        capability.setCapability("build", "Cucumber Sample Build");
        capability.setCapability("name", "Cucumber Sample Test");
//        capability.setCapability("deviceName", "OPPO A15");
//        capability.setCapability("platformVersion","10");
//        capability.setCapability("platformName", "Android");
        capability.setCapability("app","lt://APP10011921653852606324666");
        capability.setCapability("isRealMobile", true);

        connection = new AndroidDriver<AndroidElement>(
                new URL("https://" + username + ":" + accesskey + "@beta-hub.lambdatest.com/wd/hub"),capability);

        System.out.println(capability);
        System.out.println(connection.getSessionId());
        WebDriverWait wait = new WebDriverWait(connection, 10);
    }
    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }
    @DataProvider
    public Object[][] features() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        System.out.println("Cucumber runner initialized");
        return testNGCucumberRunner.provideFeatures();
    }
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
}
