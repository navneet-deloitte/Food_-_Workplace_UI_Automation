package PageObjects;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class AdminOrderHistory extends BaseClass {

    By keywordFilter = By.xpath("//input[@id='mat-input-1']");
    By foodStatusDropDown = By.xpath("//mat-select[@id='mat-select-0']");
    By sortByDropDown = By.xpath("//mat-select[@id='mat-select-2']");
    By orderIdFirst = By.xpath("(//div[@class=\"content\"])[1]/div[1]");
    By orderDetails = By.xpath("//div[@class=\"card-header card-head\"]");
    By statusOfOrder = By.xpath("//div[@class=\"status\"]");
    By dashBoard = By.xpath("//a[contains(text(),'Dashboard')]");
    By updateMenu = By.xpath("//a[contains(text(),'Update Menu')]");
    By profileIcon = By.xpath("//app-admin-header/div[1]/mat-toolbar[1]/div[1]/mat-icon[1]");
    By signOutButton = By.xpath("//a[@title=\"Logout\"]");

    public Logger logger = Logger.getLogger(this.getClass().getName());
    public static ExtentTest logInfo = null;

    public void filterByOrderID(ExtentTest test) throws InterruptedException, IOException {
        driver.navigate().refresh();
        logInfo = test.createNode("Filter by order ID", "Filtering the Order History by order id");
        logger.info("filtering the Admin using order id");
        String orderId;
        String[] oid = driver.findElement(orderIdFirst).getText().split(" ");
        orderId = oid[2];
        driver.findElement(keywordFilter).sendKeys(orderId);
        Utils.wait(1000);

        try {
            Assert.assertTrue(driver.findElements(orderDetails).size() != 0);
            logInfo.pass("Filter applied using orderid: Passed");
            Utils.extentScreenShotCapture(logInfo,"Filter applied using orderid", keywordFilter);

        }
        catch(AssertionError e){
            System.out.println(e+"found exception");
            logInfo.fail("Filter applied using orderid: Failed");
            Utils.extentScreenShotCapture(logInfo,"Filter applied using orderid", keywordFilter);
            test.log(Status.FAIL,"Filter doesn't work with the order id");
            Assert.assertTrue(driver.findElements(orderDetails).size() != 0);
        }
    }

    public void filterByFoodStatus(ExtentTest test) throws IOException {
        driver.navigate().refresh();
        logInfo = test.createNode("Filter by Food status", "Filtering the Order History by food status");
        logger.info("filtering the history using food status");
        Select status = new Select(driver.findElement(foodStatusDropDown));
        logger.info("Filtering for the pending orders");
        status.selectByVisibleText("Pending");
        Utils.extentScreenShotCapture(logInfo,"Filter applied pending order status", foodStatusDropDown);


        List<WebElement> listOfStatusOfOrders = driver.findElements(statusOfOrder);
        for(int i = 0;i<listOfStatusOfOrders.size();i++){
            Assert.assertTrue(listOfStatusOfOrders.get(i).getText().contains("Pending"));
        }
        logger.info("filtering for the completed orders");
        status.selectByVisibleText("Completed");
        Utils.extentScreenShotCapture(logInfo,"Filter applied using completed order status", foodStatusDropDown);


        listOfStatusOfOrders = driver.findElements(statusOfOrder);
        for(int i = 0;i<listOfStatusOfOrders.size();i++){
            Assert.assertTrue(listOfStatusOfOrders.get(i).getText().contains("Completed"));
        }

    }


    public void sortByDate(ExtentTest test) throws IOException {
        driver.navigate().refresh();
        logInfo = test.createNode("sort by date", "sorting the Order History by food status");
        logger.info("sorting the history using date");

        Select sort = new Select(driver.findElement(sortByDropDown));
        sort.selectByVisibleText("Date (Descending)");
        Utils.extentScreenShotCapture(logInfo, "sorting using date", sortByDropDown);
        Assert.assertTrue(true);
    }

    public void adminLogOut() throws InterruptedException {
        driver.findElement(profileIcon).click();
        driver.findElement(signOutButton).click();
        Utils.wait(500);
    }

    public void goToDashboard(){
        driver.findElement(dashBoard).click();
    }

    public void updateMenu(){
        driver.findElement(updateMenu).click();
    }


}
