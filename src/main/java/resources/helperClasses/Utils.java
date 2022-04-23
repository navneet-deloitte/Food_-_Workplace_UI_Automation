package resources.helperClasses;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        try {
            userList = HandleCSV.fileOperation(userDetailsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*This method is to apply implicit wait
     *@param seconds is the first parameter in implicitWait
     */
    public static void implicitWait(int seconds)
    {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }


    /*This method is to click element
     *@param guide is the first parameter in javascriptExecutor
     */
    public static void javascriptExecutor(By guide)
    {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", driver.findElement(guide));
    }


    /*This method is to validate the result
     *@param content is the first parameter in resultValidation
     *@param roleType is the second parameter in resultValidation
     *@param logInfo is the third parameter in resultValidation
     *@param msg is the fourth parameter in resultValidation
     */
    public static void resultValidation(By content, String roleType,ExtentTest logInfo,String msg) throws InterruptedException, IOException {
        String guideContent=driver.findElement(content).getText();
        String checkString = new String(roleType);
        boolean textIsEqual = guideContent.contains(checkString);
        wait(20);
        if (guideContent.contains(roleType))
        {
            logInfo.pass(msg);
        }
        else
        {
            logInfo.fail(msg);
        }
        Assert.assertTrue(textIsEqual);
        Utils.extentScreenShotCapture(logInfo,"Result validated");
        Utils.implicitWait(20);
    }

    /*This method is to validate the result
     *@param expected is the first parameter in resultValidation
     *@param actual is the second parameter in resultValidation
     *@param logInfo is the third parameter in resultValidation
     *@param msg is the fourth parameter in resultValidation
     */
    public static void resultValidation(Boolean expected, Boolean actual, ExtentTest logInfo,String msg) throws InterruptedException, IOException {

        if(actual.equals(expected)){
            logInfo.pass(msg);
        }
        else{
            logInfo.fail(msg);
        }
        hardAssert(expected,actual,"Result Validated Successfully..");
        logInfo.addScreenCaptureFromPath(captureScreenShot(driver));
    }

    /*This method is to validate the result
     *@param expected is the first parameter in resultValidation
     *@param actual is the second parameter in resultValidation
     *@param logInfo is the third parameter in resultValidation
     *@param msg is the fourth parameter in resultValidation
     */
    public static void resultValidation(String expected, String actual, ExtentTest logInfo,String msg) throws InterruptedException, IOException {

        if(actual.equals(expected)){
            logInfo.pass(msg);
        }
        else{
            logInfo.fail(msg);
        }
        hardAssert(expected,actual,"Result Validated Successfully..");
        logInfo.addScreenCaptureFromPath(captureScreenShot(driver));
    }

    /*Scrolling up the page*/
    public static void scrollUp()
    {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
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
        WebElement dropdown_list= driver.findElement(dropdown_locator);
        List<WebElement> optionList=dropdown_list.findElements(By.tagName("a"));
        for (WebElement li : optionList) {
            if (li.getText().equals(option_name)) {
                Utils.implicitWait(30);
                li.click();
                break;

            }
        }
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

    /*This method is to assert the result
     *@param actualValue is the first parameter in harAssert
     *@param expectedValue is the second parameter in hardAssert
     *@param testFailureMessage is the fourth parameter in hardAssert
     */
    public static void hardAssert(String actualValue,String expectedValue,String testFailureMessage)
    {
        Assert.assertEquals(actualValue,expectedValue,testFailureMessage);
    }

    /*This method is to assert the result
     *@param actualValue is the first parameter in harAssert
     *@param expectedValue is the second parameter in hardAssert
     *@param testFailureMessage is the fourth parameter in hardAssert
     */
    public static void hardAssert(boolean actualValue,boolean expectedValue,String testFailureMessage)
    {
        Assert.assertEquals(actualValue,expectedValue,testFailureMessage);

    }

    /*This method is to assert the result
     *@param actualValue is the first parameter in softAssert
     *@param expectedValue is the second parameter in softAssert
     *@param testFailureMessage is the fourth parameter in softAssert
     */
    public static void softAssert(int actualValue,int expectedValue,String testFailureMessage)
    {
        softAssert(actualValue,expectedValue,testFailureMessage);
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


    /*This method is to assert the result
     *@param element is the first parameter in resultValidation
     *@param expectedString is the second parameter in resultValidation
     *@param drive is the fourth parameter in resultValidation
     *@param driver is the fifth parameter in resultValidation
     *@param logInfo is the fifth parameter in resultValidation
     *@param msg is the sixth parameter in resultValidation
     */
    public static void resultValidation(WebElement element, String expectedString, WebDriver driver, int specifiedTimeout,ExtentTest logInfo,String msg) throws IOException, InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
        implicitWait(10);
        wait(10);
        System.out.println(element.getText());
        if(element.getText().contains(expectedString)){
            logInfo.pass(msg);
        }
        else{
            logInfo.fail(msg);
        }
        logInfo.addScreenCaptureFromPath(captureScreenShot(driver));
//        javascriptExecutor(LoginPage.okayButton);
    }

    /*To wait until the visibility of any specified element
     *@param elementXpath is the first parameter in waitForVisibilityOfElements
     * @param specifiedTimeout is the second parameter in waitForVisibilityOfElements
     */
    public static void waitForVisibilityOfElements(By elementXpath,int specifiedTimeout)
    {
        WebDriverWait wait = new WebDriverWait(driver,specifiedTimeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementXpath));
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
    public static void wait(int timeInSeconds) throws InterruptedException {
        Thread.sleep(timeInSeconds);
    }

    /*To capture screenshort and append it to extent report
     *@param logInfo is the first parameter in extentScreenShotCapture
     * @param logInfoMsg is the second parameter in extentScreenShotCapture
     */
    public static void extentScreenShotCapture(ExtentTest logInfo,String logInfoMsg) throws IOException {
        logInfo.pass(logInfoMsg);
        logInfo.addScreenCaptureFromPath(captureScreenShot(driver));
    }

    public static void highlightElement(WebElement guide)
    {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].style.border='2px solid red'", guide);

    }

    public static void extentScreenShotCapture(ExtentTest logInfo, String logInfoMsg, By guide) throws IOException {
        WebElement element_node = driver.findElement(guide);
        Utils.highlightElement(element_node);
        logInfo.addScreenCaptureFromPath(captureScreenShot(driver));
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
    public static void scrollDown(){
        Actions action = new Actions(driver);
        action.sendKeys(Keys.PAGE_DOWN).build().perform();    //scroll down a page
    }
}
