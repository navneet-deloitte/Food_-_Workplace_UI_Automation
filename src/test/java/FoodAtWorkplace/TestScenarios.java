package FoodAtWorkplace;
import PageObjects.LaunchUrl;
import PageObjects.Menu_Page;
import PageObjects.UserLoginPage;
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

//    @Test(priority = 1)
    public void websiteOverview(){

        UserLoginPage.verify_logo(test);

        Menu_Page.hoverCardItem();
        Utils.wait(1000);

        Menu_Page.clickOnYou();
        Utils.wait(1000);

        Menu_Page.hoverCardItem();
        Utils.wait(1000);

        Utils.scrollDown();
        Utils.wait(1000);
        Menu_Page.scrollToTop();


        Menu_Page.clickOnTheHouseFunctionality();
        Utils.wait(1000);

        Menu_Page.hoverCardItem();
        Utils.wait(1000);

    }

    
//    @Test(priority = 2)
    public void addingItemInCartTest(){
        
        ExtentTest addingItemInCartTest = extent.createTest("Adding Item on Cart");
        
        Menu_Page.addToCartFunctionality(addingItemInCartTest);

        ExtentTest emptyCartOnRefreshTest = addingItemInCartTest.createNode("Refresh page after adding items in cart");
        
        Utils.refreshPage();
        Menu_Page.cart_validation(emptyCartOnRefreshTest);
        emptyCartOnRefreshTest.log(Status.FAIL,"Items are deleted after refreshing the page");


    }

//    @Test(priority = 3)
    public void userRegistrationTest(){

        ExtentTest useRegistrationTest = extent.createTest("Registering the new user");

        Utils.refreshPage();

        UserRegister.clickLoginBtn();

        UserRegister.register(useRegistrationTest);

    }

    @Test(priority = 4)
    public void UserLoginTest(){

        ExtentTest loginTest = extent.createTest("User Login Test");


        UserLoginPage.goto_login_page();

//        UserLoginPage.valid_login(loginTest);
        UserLoginPage.invalid_login(loginTest);

        Utils.wait(3000);
    }

   @AfterClass
   public void tearDown()
   {
       driver.quit();
   }

}