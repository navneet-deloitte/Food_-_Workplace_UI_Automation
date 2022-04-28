package resources.helperClasses;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import testAutomationListner.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utils extends BaseClass
{
    public static String userDetailsFile="";
    public static String[] userList;

    static{
        userList = HandleCSV.fileOperation(userDetailsFile);
    }

    /*This method is to apply implicit wait
     *@param seconds is the first parameter in implicitWait
     */
    public static void implicitWait(int seconds)
    {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /*Scrolling up the page*/
    public static void scrollUp()
    {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }

    public static void scrollUpTo(WebElement webElement){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    /*
     * Wait for an element until it clickable and then clicking the element,
     * Parameter: By type element
     */
    public static void waitAndClick(By elementXpath)
    {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementXpath));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
    }

    /*This method is to select from dropdown
     *@param dropdown_locator is the first parameter in resultValidation
     *@param option_name is the second parameter in resultValidation
     */
    public static void select_from_dropdown(By dropdown_locator,String option_name)
    {
        WebElement dropDownElement = driver.findElement(dropdown_locator);
        Select select = new Select(dropDownElement);
        select.selectByVisibleText(option_name);
    }

    /*to generate random number*/
    public static int randomNumber()
    {
        int min=0;
        int max=1000;
        int randomNumber=(int)(Math.random()*(max-min+1)+min);
        return randomNumber;
    }

    /*To refresh page*/
    public static void refreshPage()
    {
        driver.navigate().refresh();
    }

    /*To maximize page*/
    public static void maximizePage()
    {
        driver.manage().window().maximize();
    }

    /*To delete all cookies*/
    public static void deleteAllCookies()
    {
        driver.manage().deleteAllCookies();
    }

    /*To navigate back to previous page*/
    public static void navigateBack()
    {
        driver.navigate().back();
    }

    /*To search for a particular job
     *@param jobName is the first parameter in search
     */
    public static void search(String jobName) throws InterruptedException {
        List<WebElement> pagination = driver.findElements(By.xpath("//div[@class='pagination']//a"));
        List<WebElement> job_list;
        List<String> elements = new ArrayList<>();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        for (int i = 1; i<=pagination.size(); i++) {
            int flag = 0;
            driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
            executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@class='pagination']//a[" +i+ "]")));
            Thread.sleep(1000);
            job_list = driver.findElements(By.xpath("//div[@class='right']/div[@class='title']"));
            for (int j = 0; j < job_list.size(); j++) {
                String title = job_list.get(j).getText();
                if (title.equals(jobName)) {
                    Log.info("Job found");
                    flag = 1;
                    break;
                } else {
                    elements.add(title);
                }

            }
            if (flag == 1)
                break;
        }
    }


    /*To wait until the visibility of any specified element
     *@param elementXpath is the first parameter in waitForVisibilityOfElements
     * @param specifiedTimeout is the second parameter in waitForVisibilityOfElements
     */
    public static void waitForVisibilityOfElements(By elementXpath,int specifiedTimeout)
    {
        WebDriverWait wait = new WebDriverWait(driver,specifiedTimeout);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(elementXpath));
        }catch (TimeoutException e){
            Log.error("Element not visible");
        }
    }


    /*To wait for clicking of any specified element
     *@param elementXpath is the first parameter in waitForClickingElements
     * @param specifiedTimeout is the second parameter in waitForClickingOfElements
     */
    public static void waitForClickingElemets(By elementXpath,int specifiedTimeout )
    {
        WebDriverWait wait = new WebDriverWait(driver,specifiedTimeout);
        wait.until(ExpectedConditions.elementToBeClickable(elementXpath));
    }

    /*To wait for certain seconds*/
    public static void wait(int timeInMilliSeconds){
        try {
            Thread.sleep(timeInMilliSeconds);
        }catch (InterruptedException e){
            Log.error(e.getMessage());
        }
    }


    //scroll down a page
    public static void scrollDown()
    {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.PAGE_DOWN).build().perform();    //scroll down a page
    }

    //for highlighting a particular web element with a default red color
    public static void highlightElement(WebElement guide)
    {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].style.border='2px solid red'", guide);

    }
    //for highlighting a particular web element with a specific color
    public static void highlightElement(By guide, String color)
    {
        waitForVisibilityOfElements(guide,1);

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].style.border='2px solid "+ color + "'", driver.findElement(guide));

    }

    public static void captureScreenShotHighlighted(ExtentTest logInfo, String logInfoMsg, WebElement webElement) {
        try {
            Utils.highlightElement(webElement);
            logInfo.log(Status.PASS,logInfoMsg);
            logInfo.addScreenCaptureFromPath(captureScreenShot(driver));
        }catch (IOException ioException){
            Log.error("Failed to captured screenshot "+ioException.getMessage());
            logInfo.log(Status.FAIL,"Failed to captured screenshot "+ioException.getMessage());
        }
    }

    /*To capture screenshort and append it to extent report
     *@param logInfo is the first parameter in extentScreenShotCapture
     * @param logInfoMsg is the second parameter in extentScreenShotCapture
     */
    public static void extentScreenShotCapture(ExtentTest logInfo,String logInfoMsg){
        try {
            logInfo.log(Status.PASS,logInfoMsg);
            logInfo.addScreenCaptureFromPath(captureScreenShot(driver));
        }catch (IOException ioException){
            Log.error("Failed to captured screenshot "+ioException.getMessage());
            logInfo.log(Status.FAIL,"Failed to captured screenshot "+ioException.getMessage());
        }
    }

    // for capturing screenshot with highlighting a particular web element using xpath
    public static void extentScreenShotCapture(ExtentTest logInfo, String logInfoMsg, By guide){
        waitForVisibilityOfElements(guide,30);
        try {
            WebElement webElement = driver.findElement(guide);
            captureScreenShotHighlighted(logInfo,logInfoMsg,webElement);
        }catch (NoSuchElementException e){
            Log.error("No such element available");
        }


    }

    // for capturing screenshot with highlighting a particular web element using xpath
    public static void extentScreenShotCapture(ExtentTest logInfo, String logInfoMsg, WebElement webElement){
        captureScreenShotHighlighted(logInfo,logInfoMsg,webElement);
    }



    //For searching the element and click
    public static void searchandclick(WebElement path){
        int i=1;
        while (i==1){
            try{
                path.click();
                i=0;
            }
            catch (Exception e){
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("arguments[0].scrollIntoView()", path);
            }
        }
    }
    /* for hovering on a web element
    @para first para is xpath of web element */
    public static void hover(By xpath){
        WebElement ele = driver.findElement(xpath);
        Actions action = new Actions(driver);
        action.moveToElement(ele).perform();

    }
    /* for merging two list into one and return that
    @para first para is list in which second list is merged
    and second para is list which will merge in first list  */
    public static List<List<String>> mergeLists(List<List<String>> parentList, List<List<String>> childList){

        for(List<String> listItem : childList){
            List<String> tobeAdd = new ArrayList<>(listItem);
            parentList.add(tobeAdd);
        }
        return parentList;
    }


}
