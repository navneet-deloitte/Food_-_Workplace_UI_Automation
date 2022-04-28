package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import testAutomationListner.Log;


public class UserLoginPage extends BaseClass {
    static By logo=By.xpath("//img[@class='logo-img']");
//    static By Login_Page=By.xpath("//a[contains(text(),'Login')]");
    static By Login_Page=By.xpath("//a[@class='history login ng-star-inserted']");
//    static By mail=By.xpath("//input[contains(@class,'form-control input ng-pristine ng-valid ng-touched')]");
    static By mail=By.xpath("//input[@type = 'email']");
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
    static By oderHistory = By.xpath("//mat-icon[contains(@class,'history ng-star-inserted')]");



    public static ExtentTest logInfo;
    public static String loginDetails;


    // This method is for go to user login page
    public static void goto_login_page(){
//        Utils.deleteAllCookies();
        Utils.wait(1000);
        WebElement loginBtnElement = driver.findElement(Login_Page);
        Utils.searchandclick(loginBtnElement);
        Utils.wait(2000);
    }

    // This method is for go to admin login page
    public static void goto_admin_login(){
        driver.findElement(Admin_login).click();
        Utils.implicitWait(2);
    }


    //    This method is for successful login with valid credentials
    public static void valid_login(ExtentTest test){
        logInfo=test.createNode("userLogin_Valid");
        Log.info("Reading login data from csv file...");

        String userDetailsFilePath = properties.getProperty("user_login_data_csv");

        loginDetails= HandleCSV.readFromLast(userDetailsFilePath);

        if(loginDetails.isEmpty()){
            logInfo.error("Can't read User details file.");
            return;
        }

        fillLoginDetails(loginDetails,false);

        logInfo.pass("Login success");
        Utils.extentScreenShotCapture(logInfo,"Login Successfully",toastMsg);

        Utils.wait(1000);
    }

    // This method is for verifying the visibility of website logo
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

    public static void cartEmptyCheck(ExtentTest test){
        logInfo=test.createNode("Cart empty");
        Utils.searchandclick(driver.findElement(cartButton));
        Utils.wait(1000);
        String cart_status=driver.findElement(cart_empty).getText();
        try{
            Assert.assertEquals(cart_status,"Your cart is empty");
            logInfo.pass("cart is empty");
            Utils.extentScreenShotCapture(logInfo,"cart is empty",cart_empty);
        }
        catch (AssertionError e){
            logInfo.fail("Cart is not empty. " + e.getMessage());
            Utils.extentScreenShotCapture(logInfo,"cart is not empty",cart_empty);
        }
        Log.info("Cart is empty");
    }


    //    This method is for Invalid login with invalid credentials
    public static void invalid_login(ExtentTest test){
        logInfo=test.createNode("Invalid Login Test");
        Log.info("Reading login data from csv file...");

        loginDetails= HandleCSV.readFromLast(properties.getProperty("user_invalid_login_csv"));

        if(loginDetails.isEmpty()){
            logInfo.error("Can't read User details file.");
            return;
        }

        fillLoginDetails(loginDetails,true);


        logInfo.pass("Login failed for invalid credentials shows error message.");
        Utils.highlightElement(toastMsg,"blue");
        Utils.extentScreenShotCapture(logInfo,"Login Failed");
    }


    // This method is for filling login credentials
    private static void fillLoginDetails(String loginDetails,Boolean verify) {
        String[] credentials=loginDetails.split(",");
        Log.info("Entering emailId...");
        driver.findElement(mail).clear();
        driver.findElement(mail).sendKeys(credentials[0]);
        Log.info("Confirming the Mail id");
        try {
            driver.findElement(verify_email).click();
        }catch (NoSuchElementException e){
            Log.error("Element not found");
        }catch (Exception e){
            Log.error("Element not found");
        }
        Log.info("Entering password...");
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(credentials[1]);

        Log.info("Clicking on Login");
        driver.findElement(login).click();

        Utils.wait(1000);

    }
}
