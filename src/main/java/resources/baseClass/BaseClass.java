package resources.baseClass;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import resources.helperClasses.Utils;
import testAutomationListner.ExtentReportListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseClass extends ExtentReportListener {
    public static WebDriver driver;
    public Properties properties;
    public static ExtentTest test;


    //to initialize driver
    public WebDriver initializeDriver() throws IOException {

        String propertyFile="\\src\\main\\java\\resources\\properties\\data.properties";
        FileInputStream fileInputStream=new FileInputStream(System.getProperty("user.dir")+propertyFile);
        properties=new Properties();
        properties.load(fileInputStream);
        String browserName=properties.getProperty("browser");

        /*Checking for specified browser name*/
        if(browserName.equals("chrome"))
        {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+properties.getProperty("chrome_driver"));
            driver=new ChromeDriver();
        }
       else if (browserName.equals("firefox"))
        {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+properties.getProperty("firefox_driver"));
            driver = new FirefoxDriver();
        }
       else if (browserName.equals("edge"))
        {
            System.setProperty("webdriver.msedge.driver", System.getProperty("user.dir")+properties.getProperty("edge_driver"));
            driver=new InternetExplorerDriver();
        }
//        Utils.implicitWait(3);
        return driver;

    }

}
