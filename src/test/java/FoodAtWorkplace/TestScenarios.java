package FoodAtWorkplace;
import PageObjects.LaunchUrl;
import PageObjects.UpdateMenuPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.*;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.TestAllureListener;

import java.io.IOException;

@Listeners({TestAllureListener.class})
public class TestScenarios extends BaseClass {

    ExtentTest test;
    LaunchUrl launchUrl = new LaunchUrl();

//    @BeforeClass(groups = {"smoke","regression","functional"})
    @Test(priority = 1)
    public void launchUrl() throws IOException {

        test=extent.createTest("Launch Url");
        launchUrl.getUrl(test);
        Utils.maximizePage();
        Utils.deleteAllCookies();
        Utils.implicitWait(5);

    }


    // TODO add all scenarios and test cases here

    @Test(priority = 2)
    public void login(){
        ExtentTest extentTest = extent.createTest("Login");

        extentTest.log(Status.PASS,"Success");

        launchUrl.login();

        extentTest.log(Status.FAIL,"login then Fail");

    }

    @Test(priority = 3)
    public void editItem(){
        ExtentTest extentTest = extent.createTest("Edit Item");

        UpdateMenuPage.usedItem();

        UpdateMenuPage.clickUpdateMenuBtn();
        System.out.println("clickUpdateMenuBtn");
        UpdateMenuPage.clickEditItemBtn();
        System.out.println("clickEditItemBtn");

        driver.switchTo().activeElement();
        System.out.println("Frame switched");


        UpdateMenuPage.editItemDetails();
        System.out.println("edit item");

//        UpdateMenuPage.clickAddBtn();
//        System.out.println("Edit");

    }



    @AfterClass(groups = {"smoke","regression","functional"})
    public void tearDown()
    {
//        driver.quit();
    }
}