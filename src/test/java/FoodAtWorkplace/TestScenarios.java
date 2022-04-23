package FoodAtWorkplace;
import PageObjects.AdminLogin;
import PageObjects.LaunchUrl;
import PageObjects.UserRegister;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.After;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.TestAllureListener;

import java.io.IOException;

@Listeners({TestAllureListener.class})
public class TestScenarios extends BaseClass {

    ExtentTest test;
    LaunchUrl launchUrl = new LaunchUrl();

    @BeforeClass(groups = {"smoke","regression","functional"})
    public void launchUrl() throws IOException {

        test=extent.createTest("Launch Url");
        launchUrl.getUrl(test);
        Utils.maximizePage();
        Utils.deleteAllCookies();
        Utils.implicitWait(30);

    }

    // TODO add all scenarios and test cases here

    @Test
    public void Test1() throws IOException, InterruptedException {
        ExtentTest extentTest = extent.createTest("Test 1");

        extentTest.log(Status.PASS,"Success");
    }


   @AfterClass
   public void tearDown()
   {
       driver.quit();
   }

}