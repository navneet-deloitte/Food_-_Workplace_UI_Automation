package PageObjects;
import com.aventstack.extentreports.ExtentTest;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;


public class LaunchUrl extends BaseClass {

    public static ExtentTest logInfo = null;

    /*This method is to launch the base url of application*/
    public void getUrl(ExtentTest test) throws IOException {
        logInfo=test.createNode("getUrl");
        Log.info("Initializing driver...");
        driver = initializeDriver();
        Log.info("Loading base url from data file...");
        String urlName = properties.getProperty("baseUrl");
        Log.info("Launching...");
        driver.get(urlName);
        Utils.extentScreenShotCapture(logInfo,"Url Launched Successfully");
        Log.info("Url Launched Successfully...");
    }

}
