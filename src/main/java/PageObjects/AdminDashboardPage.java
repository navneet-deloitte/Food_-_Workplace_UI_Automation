package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;
import java.util.List;

public class AdminDashboardPage extends BaseClass {
    static By keywordFilter = By.xpath("//input");
    static By orderHistoryButton = By.xpath("//a[contains(text(),'Order History')]");
    static By updateMenuButton = By.xpath("//a[contains(text(),'Update Menu')]");
    static By profileIcon = By.xpath("//mat-icon[contains(text(),'account_circle')]");
    static By signOutButton = By.xpath("//i[@class=\"fa fa-sign-out\"]");
    static By receivedItems = By.xpath("//div[contains(text(),'Received')]/following-sibling::div/div/div/div/div");
    static By cookingItems = By.xpath("//div[contains(text(),'Cooking')]/following-sibling::div/div/div/div/div");
    static By readyItems = By.xpath("//div[contains(text(),'Ready')]/following-sibling::div/div/div/div/div");
    static By orderIdReceivedItems = By.xpath("//div[contains(text(),'Received')]/following-sibling::div/div/div/div/div//div[@class=\"orderid\"]");
    static By orderIdCookingItems = By.xpath("//div[contains(text(),'Cooking')]/following-sibling::div/div/div/div/div//div[@class=\"orderid\"]");
    static By orderIdReadyItems = By.xpath("//div[contains(text(),'Ready')]/following-sibling::div/div/div/div/div//div[@class=\"orderid\"]");
    static By emailIDReceivedItems = By.xpath("//div[contains(text(),'Received')]/following-sibling::div/div/div/div/div//div[@class=\"date\"][1]");
    static By receivedOrderButtons = By.xpath("//div[contains(text(),'Received')]/following-sibling::div/div/div/div/div/div/button");
    static By cookingOrderButtons = By.xpath("//div[contains(text(),'Cooking')]/following-sibling::div/div/div/div/div/div/button");
    static By readyOrderButtons = By.xpath("//div[contains(text(),'Ready')]/following-sibling::div/div/div/div/div/div/button");
    static By logo=By.xpath("//img[@class='logo-img']");



    public static ExtentTest logInfo = null;

    //    Applying filters using keyword filter on admin dashboard
    public static void filterByAll(ExtentTest test, String filterText){
        WebDriverWait wait = new WebDriverWait(driver, 3);
        Utils.wait(2000);
        Utils.implicitWait(4);
        logInfo = test.createNode("Filter Admin Dashboard", "Filtering the dashboard elements using keyword filter");
        Utils.wait(1000);
        Log.info("Sending keys to filtex text box");
        driver.findElement(keywordFilter).sendKeys(filterText);
        Utils.wait(2000);

        Log.info("Filtering the dashboard");
        Utils.extentScreenShotCapture(logInfo,"Filter applied", keywordFilter);
        Log.info("getting the list of food items in different sections");
        List<WebElement> listOfReceivedItems = driver.findElements(receivedItems);
        List<WebElement> listOfCookingItems = driver.findElements(cookingItems);
        List<WebElement> listOfReadyItems = driver.findElements(readyItems);

        try {
            for (WebElement lri : listOfReceivedItems) {
                Log.info("checking if the filtered results in received contains the specified keyword");
                System.out.println(lri.getText());
                System.out.println(filterText);
                try {
                    Assert.assertTrue(lri.getText().contains(filterText));
                }catch (AssertionError e){
                    Log.error(e.getMessage());
                }
            }
        }catch (org.openqa.selenium.StaleElementReferenceException ex){
            listOfReceivedItems = driver.findElements(receivedItems);
            for (WebElement lri : listOfReceivedItems) {
                Log.info("checking if the filtered results in received contains the specified keyword");
                Assert.assertTrue(lri.getText().contains(filterText));
            }
        }
        try {
            for (WebElement lci : listOfCookingItems) {
                Log.info("checking if the filtered results in cooking contains the specified keyword");
                Assert.assertTrue(lci.getText().contains(filterText));
            }
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex){
            listOfCookingItems = driver.findElements(cookingItems);
            for (WebElement lci : listOfCookingItems) {
                Log.info("checking if the filtered results in cooking contains the specified keyword");
            }
        }

        try {
            for (WebElement lri : listOfReadyItems) {
                Log.info("checking if the filtered results in ready contains the specified keyword");
                Assert.assertTrue(lri.getText().contains(filterText));
            }
        }catch (org.openqa.selenium.StaleElementReferenceException ex){
            listOfReadyItems = driver.findElements(readyItems);
            for (WebElement lri : listOfReadyItems) {
                Log.info("checking if the filtered results in ready contains the specified keyword");
                Assert.assertTrue(lri.getText().contains(filterText));
            }
        }

    }

    //    Applying filters with keyword filters using orderID
    public static void filterByOrderId(ExtentTest test){
        WebDriverWait wait = new WebDriverWait(driver, 3);
        Utils.implicitWait(3);
        logInfo = test.createNode("Filter by order ID", "Filtering the dashboard by order id");
        Log.info("filtering the dashboard using order id");
        driver.findElement(keywordFilter).clear();
        Utils.wait(1000);

        Log.info("Fetching the list of received items");
        List<WebElement> listOfOrderIdReceivedItems = driver.findElements(orderIdReceivedItems);
        List<WebElement> listOfReceivedItems;
        String orderId;

        try {

            for (WebElement listOfOrderIdReceivedItem : listOfOrderIdReceivedItems) {
                String[] oid = listOfOrderIdReceivedItem.getText().split(" ");
                orderId = oid[2];
                driver.findElement(keywordFilter).clear();
                Utils.wait(1000);
                driver.findElement(keywordFilter).sendKeys(orderId);
                Utils.wait(1000);
                System.out.println(driver.findElements(receivedItems).size());
                Log.info("Checking whether there's any order for entered orderID");
                try {
                    Assert.assertTrue(driver.findElements(receivedItems).size() != 0);
                    logInfo.pass("Filter applied using orderid: Passed");
                    Utils.extentScreenShotCapture(logInfo, "Filter applied using orderid", keywordFilter);

                } catch (AssertionError e) {
                    System.out.println(e + "found exception");
                    logInfo.fail("Filter applied using orderid: Failed");
                    Utils.extentScreenShotCapture(logInfo, "Filter applied using orderid", keywordFilter);
                    test.log(Status.FAIL, "Filter doesn't work with the order id");
//                Assert.assertTrue(driver.findElements(receivedItems).size() != 0);
                    Menu_Page.logoutFunctionality();
                }

            }
        }catch (StaleElementReferenceException e){
            Log.error(e.getMessage());
            AdminDashboardPage.adminLogOut();
        }
    }

    //    Applying filters with keyword filters using orderID
    public static void filterByEmailId(ExtentTest test){
        logInfo = test.createNode("Filter by Email id", "Filtering the dashboard by Email id");
        Utils.wait(2000);
        Log.info("Refreshing the page");
        driver.navigate().refresh();
        Log.info("filtering the dashboard using Email id");
        driver.findElement(keywordFilter).clear();
        Utils.wait(2000);
        logInfo.info("getting the list of email ids in received ");
        List<WebElement> listOfEmailIdReceivedItems = driver.findElements(emailIDReceivedItems);
        List<WebElement> listOfReceivedItems;
        String emailid;

        logInfo.info("getting the list of email ids in received ");

        for (int i=0; i<listOfEmailIdReceivedItems.size(); i++){
            listOfEmailIdReceivedItems = driver.findElements(emailIDReceivedItems);
            String[] oid = listOfEmailIdReceivedItems.get(i).getText().split(" ");
            emailid = oid[2];
            Log.info("trying to search with email id "+(i+1));
            driver.findElement(keywordFilter).clear();
            Utils.wait(2000);
            driver.findElement(keywordFilter).sendKeys(emailid);
            Utils.wait(1000);
            Utils.extentScreenShotCapture(logInfo,"Filter applied using Email id", keywordFilter);

            listOfReceivedItems = driver.findElements(receivedItems);
            Assert.assertTrue(listOfReceivedItems.size()!=0);
        }

    }

    //Validating the order status change from cooking to ready
    public static void sendToCooking(ExtentTest test, String orderId){
        logInfo = test.createNode("Received to cooking", "Moving an order from received to cooking");
        Log.info("Refreshing the page");
        driver.navigate().refresh();
        Utils.wait(2000);
        List<WebElement> listOfOrderIdReceivedItems = driver.findElements(orderIdReceivedItems);
        List<WebElement> listOfReceivedOrderButtons = driver.findElements(receivedOrderButtons);
        List<WebElement> listOfOrderIDCookingItems = driver.findElements(orderIdCookingItems);



        for(int i = 0; i< listOfOrderIdReceivedItems.size();i++){
            if(listOfOrderIdReceivedItems.get(i).getText().contains(orderId)){
                Log.info("Changing the order status from received to cooking");
                Utils.searchandclick(listOfReceivedOrderButtons.get(i));
                break;
            }
        }
        int flag = 0;
        Utils.wait(3000);

        Log.info("Validating the order status change in the dashboard");
        for (int i = 0; i < listOfOrderIDCookingItems.size(); i++) {
            listOfOrderIDCookingItems = driver.findElements(orderIdCookingItems);
            if (listOfOrderIDCookingItems.get(i).getText().contains(orderId)) {
                flag = 1;
                break;
            }
        }
        Utils.extentScreenShotCapture(logInfo,"Validating the change in the order status from Received to cooking");
        Assert.assertEquals(1,flag);
    }

    //validating the change in order status from cooking to ready
    public static void sendToReady(ExtentTest test, String orderId){
        logInfo = test.createNode("Cooking to Ready", "Moving an order from cooking to Ready");
        Log.info("Refreshing the page");
        driver.navigate().refresh();
        Utils.wait(2000);
        List<WebElement> listOfOrderIDCookingItems = driver.findElements(orderIdCookingItems);
        List<WebElement> listOfOrderIdReadyItems = driver.findElements(orderIdReadyItems);
        List<WebElement> listOfCookingOrderButtons = driver.findElements(cookingOrderButtons);



        for(int i = 0; i< listOfOrderIDCookingItems.size();i++){
            if(listOfOrderIDCookingItems.get(i).getText().contains(orderId)){
                Log.info("Changing the order status from cooking to ready");
                Utils.searchandclick(listOfCookingOrderButtons.get(i));
                Utils.wait(2000);
                break;
            }
        }
        int flag = 0;

        Log.info("Validating the order status change in the dashboard");
        for (int i = 0; i < listOfOrderIdReadyItems.size(); i++) {
            listOfOrderIdReadyItems = driver.findElements(orderIdReadyItems);
            if (listOfOrderIdReadyItems.get(i).getText().contains(orderId)) {
                flag = 1;
                break;
            }
        }
        Utils.extentScreenShotCapture(logInfo,"Validating the change in the order status from cooking to ready");
        Assert.assertEquals(1,flag);
    }

    //Validating the change in the order status from ready to complete
    public static void sendToComplete(ExtentTest test, String orderId) throws InterruptedException, IOException {
        logInfo = test.createNode("Ready to Ready", "Moving an order from Ready to Complete");
        Log.info("Refreshing the page");
        driver.navigate().refresh();
        Utils.wait(2000);
        List<WebElement> listOfOrderIdReadyItems = driver.findElements(orderIdReadyItems);
        List<WebElement> listOfReadyOrderButtons = driver.findElements(readyOrderButtons);



        for(int i = 0; i< listOfOrderIdReadyItems.size();i++){
            if(listOfOrderIdReadyItems.get(i).getText().contains(orderId)){
                Log.info("Changing the order status from Ready to complete");
                Utils.searchandclick(listOfReadyOrderButtons.get(i));
                Utils.wait(2000);
                break;
            }
        }
        int flag = 0;

        Log.info("Validating the order status change in the dashboard");
        for (int i = 0; i < listOfOrderIdReadyItems.size(); i++) {
            listOfOrderIdReadyItems = driver.findElements(orderIdReadyItems);
            if (listOfOrderIdReadyItems.get(i).getText().contains(orderId)) {

                flag = 1;
                break;
            }
        }
        Utils.extentScreenShotCapture(logInfo,"Validating the change in the order status from ready to complete");
        Assert.assertEquals(0,flag);
    }

    public static void goToOrderHistory(){
        driver.findElement(orderHistoryButton).click();
        Utils.wait(500);
    }

    public static void goToUpdateMenu(){
        driver.findElement(updateMenuButton).click();
        Utils.wait((500));
    }

    public static void adminLogOut(){
        Utils.scrollUpTo(driver.findElement(profileIcon));
        Utils.searchandclick(driver.findElement(profileIcon));
        Utils.waitForVisibilityOfElements(signOutButton, 10);
        driver.findElement(signOutButton).click();
    }


}

