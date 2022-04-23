package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;

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




    public static String loginData_csv = "src\\main\\java\\resources\\datasheets\\logindata.csv";
    public static String Invalid_login_csv="src/main/java/resources/datasheets/Invalid_login_details.csv";
    public static ExtentTest logInfo=null;
    public static String loginDetails;



    public static void goto_login_page(){
        driver.findElement(Login_Page).click();
    }

    public static void goto_admin_logi(){
        driver.findElement(Admin_login).click();
    }


    //    This method is for successful login with valid credentials
    public static void valid_login(ExtentTest test) throws IOException {
        logInfo=test.createNode("userLogin_Valid");
        Log.info("Reading login data from csv file...");
        loginDetails= HandleCSV.readFromLast(loginData_csv);
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
        driver.findElement(success);
        Utils.extentScreenShotCapture(logInfo,"Login Successfull",success);
    }


    public static void verify_logo(ExtentTest test){
        logInfo=test.createNode("Logo verification");
        boolean logo_status=driver.findElement(logo).isDisplayed();
        Assert.assertEquals(logo_status,true);
        Log.info("Logo is present");
    }

    public static void cartempty(ExtentTest test){
        logInfo=test.createNode("Cart empty");
        String cart_status=driver.findElement(cart_empty).getText();
        Assert.assertEquals(cart_status,"Your cart is empty");
        Log.info("Cart is empty");
    }




    //    This method is for Invalid login with invalid credentials
    public static void invalid_login(ExtentTest test) throws IOException {
        logInfo=test.createNode("Invalid_Login");
        Log.info("Reading login data from csv file...");
        loginDetails= HandleCSV.readFromLast(Invalid_login_csv);
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
        driver.findElement(failed);
        Utils.extentScreenShotCapture(logInfo,"Login Failed",failed);
    }
}
