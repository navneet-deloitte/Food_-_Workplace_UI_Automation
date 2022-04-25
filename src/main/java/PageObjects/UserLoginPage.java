package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.testng.Assert;

import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;
import java.util.Properties;

public class UserLoginPage extends BaseClass {
    static By logo=By.xpath("//img[@class='logo-img']");
    static By Login_Page=By.xpath("//a[contains(text(),'Login')]");
    static By mail=By.xpath("//input[@placeholder='Enter Email address']");
    static By verify_email=By.xpath("//div[@class='verify-btn-div']");
    static By password=By.xpath("//input[@placeholder='Enter Password']");
    static By login=By.xpath("//button[contains(text(),'Login')]");
    static By Order_History=By.xpath("//a[contains(text(),'Order History')]");
    static By failed=By.xpath("//div[contains(text(),'Failed')]");
    static By success=By.xpath("//div[contains(text(),'Success')]");
    static By cart_empty=By.xpath("//div[@class='ng-star-inserted']/div[2]/div[1]");
    static By Admin_login=By.xpath("//a[contains(text(),'Login Here')]");
    static By cartButton = By.xpath("//a[contains(text(),'Cart')]");
    static By toastMsg = By.xpath("//div[contains(@class,'toast-bottom-right toast-container')]");




    public static String Invalid_login_csv="src/main/java/resources/datasheets/Invalid_login_details.csv";
    public static ExtentTest logInfo;
    public static String loginDetails;



    public static void goto_login_page(){
        driver.get(properties.getProperty("baseUrl"));
        Utils.searchandclick(driver.findElement(Login_Page));
    }

    public static void goto_admin_login(){
        Utils.searchandclick(driver.findElement(Admin_login));
    }


    //    This method is for successful login with valid credentials
    public static void valid_login(ExtentTest test){
        logInfo=test.createNode("userLogin_Valid");
        Log.info("Reading login data from csv file...");

        String userDetailsFilePath = properties.getProperty("user_loginData_csv");

        loginDetails= HandleCSV.readFromLast(userDetailsFilePath);

        if(loginDetails.isEmpty()){
            logInfo.error("Can't read User details file.");
            return;
        }

        fillLoginDetails(loginDetails);

        logInfo.pass("Login success");
        Utils.extentScreenShotCapture(logInfo,"Login Successfully",toastMsg);
    }


    public static void verify_logo(ExtentTest test){
        logInfo=test.createNode("Logo verification");
        boolean logo_status=driver.findElement(logo).isDisplayed();
        try{
            Assert.assertEquals(logo_status,true);
            logInfo.pass("Logo is present");
            Utils.extentScreenShotCapture(logInfo,"Logo is displayed",logo);
        }
        catch (AssertionError e){
            logInfo.fail("Logo is not present");
            Utils.extentScreenShotCapture(logInfo,"Logo is not displayed",logo);
        }
        Log.info("Logo is present");
    }

    public static void cartempty(ExtentTest test) throws IOException, InterruptedException {
        logInfo=test.createNode("Cart empty");
        Utils.searchandclick(driver.findElement(cartButton));
        Utils.wait(1000);
        String cart_status=driver.findElement(cart_empty).getText();
        try{
            Assert.assertEquals(cart_status,"Your cart is empty");
            logInfo.pass("cart is empty");
            Utils.extentScreenShotCapture(logInfo,"cart is empty",cart_empty);
        }
        catch (Exception e){
            logInfo.fail("Cart is not empty");
            Utils.extentScreenShotCapture(logInfo,"cart is not empty",cart_empty);
        }
        Log.info("Cart is empty");
    }




    //    This method is for Invalid login with invalid credentials
    public static void invalid_login(ExtentTest test){
        logInfo=test.createNode("Invalid Login Test");
        Log.info("Reading login data from csv file...");

        loginDetails= HandleCSV.readFromLast(properties.getProperty("invalid_user_login_csv"));

        if(loginDetails.isEmpty()){
            logInfo.error("Can't read User details file.");
            return;
        }

        fillLoginDetails(loginDetails);


        driver.findElement(failed);
        logInfo.fail("Login failed for invalid credentials");
        Utils.highlightElementYellow(toastMsg);
        Utils.extentScreenShotCapture(logInfo,"Login Failed");
    }

    private static void fillLoginDetails(String loginDetails) {
        String[] credentials=loginDetails.split(",");
        Utils.refreshPage();
        Log.info("Entering emailId...");
        driver.findElement(mail).sendKeys(credentials[0]);
        Log.info("Confirming the Mail id");
        driver.findElement(verify_email).click();
        Log.info("Entering password...");
        driver.findElement(password).sendKeys(credentials[1]);

        Log.info("Clicking on Login");
        driver.findElement(login).click();

    }
}
