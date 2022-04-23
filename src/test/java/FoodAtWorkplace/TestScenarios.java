package FoodAtWorkplace;
import PageObjects.LaunchUrl;
import PageObjects.UserOrderHistory;
import PageObjects.UserOrderStatus;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.TestAllureListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Listeners({TestAllureListener.class})
public class TestScenarios extends BaseClass {

    ExtentTest test;
    LaunchUrl launchUrl = new LaunchUrl();
    public Logger logger = Logger.getLogger(this.getClass().getName());
    public static ExtentTest logInfo;

    @BeforeClass(groups = {"smoke","regression","functional"})
    public void launchUrl() throws IOException {

        test=extent.createTest("launchUrl");
        logInfo = test.createNode("launchUrl");
        driver = initializeDriver();
        logger.info("Driver is initialized!!");
        String log4jPath=System.getProperty("user.dir")+"/log4j.properties";
        //PropertyConfigurator.configure(log4jPath);
        String urlName=properties.getProperty("pageUrl");
        //String pageName = properties.getProperty("pageUrl");
        driver.get(urlName);
        logger.info("Navigating to the required url!!");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //Utils.extentScreenShotCapture(logInfo,"Url Launched Successfully", By.xpath("//div[@class='container top-space-50']"));
        driver.manage().deleteAllCookies();
        driver.findElement(By.xpath("//input[@placeholder=\"Enter Email address\"]")).sendKeys("beanmagnet@spamfellas.com");
        driver.findElement(By.xpath("//div[@class=\"verify-btn-div\"]")).click();
        driver.findElement(By.xpath("//input[@placeholder=\"Enter Password\"]")).sendKeys("beanmagnet");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Order History')]")).click();

    }
    @Test
    public void ValidatingCart() throws Exception {
        ExtentTest extentTest = extent.createTest("Validating Cart items After Adding Placing Order");
        extentTest.log(Status.PASS,"Success");
        UserOrderHistory.validatingOrderedFoodItems(test);
        UserOrderHistory.clickOnReorderButton(test);
        UserOrderStatus.validatingFoodItemsInOrderStatusPage(test);
//        UserOrderStatus.validatingFoodStatusReceived("FW00447",test);
//        UserOrderHistory.validatingViewStatusFunctionality(test);
//        UserOrderHistory.validatingCancelFoodItemFuntionality(test);
//        UserOrderHistory.validatingCustomizeFunctionality(test);
    }


    // TODO add all scenarios and test cases here

    @Test
    public void Test1(){
        ExtentTest extentTest = extent.createTest("Test 1");

        extentTest.log(Status.PASS,"Success");
    }



    @AfterClass(groups = {"smoke","regression","functional"})
    public void tearDown()
    {
        driver.quit();
    }
}