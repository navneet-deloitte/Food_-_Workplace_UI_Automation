package PageObjects;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;


public class LaunchUrl extends BaseClass {

    public static ExtentTest logInfo = null;

    public static By img = By.xpath("//img[contains(@class,'card-img-top')]");

    /*This method is to launch the base url of application*/
    public void getUrl(ExtentTest test) throws IOException {
        logInfo=test.createNode("getUrl");
        Log.info("Initializing driver...");
        driver = initializeDriver();
        Log.info("Loading base url from data file...");
        String urlName = properties.getProperty("baseUrl");
        Log.info("Launching...");
        driver.get(urlName);
        Utils.implicitWaitUntilElementVisible(driver.findElement(img));
        Utils.extentScreenShotCapture(logInfo,"Url Launched Successfully");
        Log.info("Url Launched Successfully...");
    }

    public void login() {
        driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Login Here')]")).click();
        driver.findElement(By.xpath("//input[@type=\"email\"]")).sendKeys("admin@deloitte.com");
        driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys("admin");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
    }

}
