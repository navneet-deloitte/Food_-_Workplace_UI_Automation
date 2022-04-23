package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class  UserRegister extends BaseClass {

    static By Login_Page=By.xpath("//a[contains(text(),'Login')]");
    static By mail=By.xpath("//input[@placeholder='Enter Email address']");
    static By verify_email=By.xpath("//div[@class='verify-btn-div']");
    static By random_email=By.xpath("//body/div[@id='app']/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/button[3]");
    static By get_random_mail=By.xpath("//input[@id='email-address']");
    static By otpMail=By.xpath("//div[contains(text(),'OTP Verification for Food@Workplace')]");
    static By read_otp=By.xpath("/html[1]/body[1]/div[1]/main[1]/main[1]/div[1]/div[1]/div[2]/div[2]/dl[1]/div[2]/h4[1]");
    static By otpField=By.xpath("//input[@id='otp']");
    static By verifyButton=By.xpath("//body/app-root[1]/app-email-verification[1]/div[1]/div[1]/div[2]/div[7]");
    static By passwordField=By.xpath("//body/app-root[1]/app-email-verification[1]/div[1]/div[1]/div[2]/div[5]/input[1]");
    static By loginButton=By.xpath("//button[contains(text(),'Login')]");
    static By menuText=By.xpath("/html/body/app-root/app-menu/div/div/div[1]/div/h1");


    public static ExtentTest logInfo = null;

    public static Properties properties;
    static String propertyFile="\\src\\main\\java\\resources\\properties\\data.properties";

    // This method is for new user register
    public static void register(ExtentTest test) throws InterruptedException, IOException {
        logInfo = test.createNode("register");
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get("https://fakermail.com/");
        driver.findElement(random_email).click();
        Log.info("Generated random email");
        String random_email=driver.findElement(get_random_mail).getAttribute("placeholder");
        driver.switchTo().window(tabs.get(0));
        driver.findElement(Login_Page).click();
        driver.findElement(mail).sendKeys(random_email);
        Log.info("Entered generated random mail");
        driver.findElement(verify_email).click();
        Log.info("clicked on conform");
        driver.switchTo().window(tabs.get(1));
        Utils.wait(4000);
        Utils.implicitWait(7);
        Utils.waitForVisibilityOfElements(otpMail, 50);
        driver.findElement(otpMail).click();
        Log.info("opened Mail containing OTP");
        String otp_generated=driver.findElement(read_otp).getText();
        String otp = otp_generated.substring(9,otp_generated.length());
        driver.switchTo().window(tabs.get(0));
        driver.findElement(otpField).sendKeys(otp);
        Log.info("OTP entered");
        int index = random_email.indexOf("@");
        String password = random_email.substring(0,index);
        Utils.scrollDown();
        Utils.wait(10000);
        Actions act =  new Actions(driver);
        act.moveToElement(driver.findElement(verifyButton)).click().perform();
        Log.info("OTP verified");
        Utils.wait(5000);
        driver.findElement(passwordField).sendKeys(password);
        Log.info("Password entered");
        driver.findElement(loginButton).click();
        Log.info("Login button clicked");
        Utils.extentScreenShotCapture(logInfo,"Register Successful", menuText);
        String menu = driver.findElement(menuText).getText();
        Assert.assertTrue(menu.contains("Menu"));
        Log.info("Email registered successfully");

        FileInputStream fileInputStream=new FileInputStream(System.getProperty("user.dir")+propertyFile);
        properties=new Properties();
        properties.load(fileInputStream);
        String RegisterDataFIle = properties.getProperty("register_data_file");
        HandleCSV.writeData(RegisterDataFIle,random_email,password);

    }
}