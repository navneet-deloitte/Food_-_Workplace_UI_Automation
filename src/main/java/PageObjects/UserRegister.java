package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.util.ArrayList;


public class UserRegister extends BaseClass {

    static By loginBtn=By.xpath("//a[contains(@class,'history login ng-star-inserted')]");
    static By mail=By.xpath("//input[@placeholder='Enter Email address']");
    static By verify_email=By.xpath("//div[@class='verify-btn-div']");
    static By random_email=By.xpath("//body/div[@id='app']/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/button[3]");
    static By get_random_mail=By.xpath("/html/body/div/div/div[1]/div/div[1]/div");
    static By otpMail=By.xpath("//div[contains(@class,'message--container message--container-bold')]");
    static By read_otp=By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/div[3]/div[1]/h4[1]");
    static By otpField=By.xpath("//input[@id='otp']");
    static By verifyButton=By.xpath("//body/app-root[1]/app-email-verification[1]/div[1]/div[1]/div[2]/div[7]");
    static By passwordField=By.xpath("//input[@type= 'password']");
    static By loginButton=By.xpath("//button[@class= 'btn']");
    static By menuText=By.xpath("/html/body/app-root/app-menu/div/div/div[1]/div/h1");
    static By toastMsg = By.xpath("//div[contains(@class,'toast-bottom-right toast-container')]");

    public static ExtentTest extentTest;

    public static ArrayList<String> tabs = new ArrayList<>();


    // This method is for new user register
    public static void register(ExtentTest test){

        Utils.deleteAllCookies();

        extentTest = test;
        ((JavascriptExecutor) driver).executeScript("window.open()");
        tabs = new ArrayList<String>(driver.getWindowHandles());

        launchFakerMail();

        //open website to generate a random email and store that email
        String random_email_val = getRandomEmail();


        //switch to food@workplace, goto login page, enter that email and click on conform button
        driver.switchTo().window(tabs.get(0));
        driver.findElement(mail).sendKeys(random_email_val);
        Log.info("Entered generated random mail");
        driver.findElement(verify_email).click();
        Log.info("clicked on conform");


        //switch to fakermail site, open mail containing otp and store that otp
        driver.switchTo().window(tabs.get(1));
        Utils.wait(4000);
        Utils.implicitWait(5);
        Utils.scrollDown();
        Utils.waitForVisibilityOfElements(otpMail, 60);
        driver.findElement(otpMail).click();
        Log.info("opened Mail containing OTP");
        String otp_generated=driver.findElement(read_otp).getText();
        String otp = otp_generated.substring(9,otp_generated.length());


        //switch tab, enter otp, and click on verify button
        driver.switchTo().window(tabs.get(0));
        driver.findElement(otpField).sendKeys(otp);
        Log.info("OTP entered");
        int index = random_email_val.indexOf("@");
        String password = random_email_val.substring(0,index);

        Utils.wait(1500);
        Actions act =  new Actions(driver);
        driver.findElement(verifyButton).click();
        int clickCount = 0;

        while (driver.findElement(verifyButton).isDisplayed() && clickCount < 10){
            Utils.wait(2000);
            try {
                driver.findElement(verifyButton).click();
            }catch (NoSuchElementException | StaleElementReferenceException e){
                break;
            }
            clickCount++;
            Log.info("verify button clicked");

        }

        enterPasswordAfterVerify(random_email_val,password);

    }

    // This method is for entering password after verified user email
    private static void enterPasswordAfterVerify(String email, String password) {

        ExtentTest verifyEmailTestNode = extentTest.createNode("User email verification ");
        Log.info("ss of success toast msg");
//        Utils.wait(1000);

        Log.info("OTP verified");
        //enter password and click on login button
        Utils.wait(1000);
        driver.findElement(passwordField).sendKeys(password);
        Log.info("Password entered");
        Utils.wait(1000);
        driver.findElement(loginButton).click();
        Log.info("Login button clicked");

        afterLogin(email,password);

    }
    //    This method is for storing user data after login
    public static void afterLogin(String email, String password){
        ExtentTest userCreateTestNode = extentTest.createNode("User Authorize verification ");
        Utils.wait(1000);
        Utils.extentScreenShotCapture(userCreateTestNode,"User Authorized",toastMsg);

        ExtentTest registrationResultTestNode = extentTest.createNode("User Authorize verification ");

        Utils.wait(2000);
        Utils.extentScreenShotCapture(registrationResultTestNode,"Register Successful", menuText);
        //validating that registration is successful
        String menu = driver.findElement(menuText).getText();
        Assert.assertTrue(menu.contains("Menu"));
        Log.info("Email registered successfully");

        String userDetailsFilePath = properties.getProperty("user_details_csv");
        System.out.println("path " + userDetailsFilePath);
        //writing the email and password to a csv file
        HandleCSV.writeData(userDetailsFilePath,email,password);
    }

    //    This method is for click on Login Button
    public static void clickLoginBtn(){
        Utils.scrollUpTo(driver.findElement(loginBtn));
        driver.findElement(loginBtn).click();
    }

    //    This method is for generate random email using faker mail website
    public static String getRandomEmail(){

        Log.info("Generating the random email");


        Log.info("Generated random email");
        Utils.wait(1000);
        String random_email_val ="";

        random_email_val = driver.findElement(get_random_mail).getText();

        return random_email_val;
    }

    private static void launchFakerMail(){
        //open website to generate a random email and store that email
        driver.switchTo().window(tabs.get(1));
        driver.get(properties.getProperty("cryptoMailUrl"));
        Log.info("crypto Mail website launched");
        Utils.wait(2000);

        checkingEmailField();

    }

    private static void checkingEmailField() {
        String emailId = driver.findElement(get_random_mail).getText();
        try {
            System.out.println("generated mail id "+ emailId);
            if(emailId.isEmpty()) {
                Utils.refreshPage();
                Utils.wait(2000);
                checkingEmailField();
            }
        }catch (NoSuchElementException e){
            Utils.refreshPage();
            Log.info("crypto Mail website refresh");
            checkingEmailField();
        }
    }

}