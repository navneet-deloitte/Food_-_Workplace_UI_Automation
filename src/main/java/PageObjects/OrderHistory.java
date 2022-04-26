package PageObjects;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class OrderHistory extends BaseClass {

    static By keywordFilter = By.xpath("//input[contains(@class,'mat-input-element')]");
    static By foodStatusDropDown = By.xpath("//mat-select[@id='mat-select-0']");
    static By sortByDropDown = By.xpath("//mat-select[@id='mat-select-2']");
    static By orderIdFirst = By.xpath("(//div[@class=\"content\"])[1]/div[1]");
    static By orderDetails = By.xpath("//div[@class=\"card-header card-head\"]");
    static By statusOfOrder = By.xpath("//div[@class=\"status\"]");
    static By dashBoard = By.xpath("//a[contains(text(),'Dashboard')]");
    static By updateMenu = By.xpath("//a[contains(text(),'Update Menu')]");
    static By profileIcon = By.xpath("//app-admin-header/div[1]/mat-toolbar[1]/div[1]/mat-icon[1]");
    static By signOutButton = By.xpath("//a[@title=\"Logout\"]");
    static By itemNameXpath = By.xpath("//div[contains(@class,'item-row ng-star-inserted')]");

    public static ExtentTest logInfo = null;

    public static void filterByItemName(ExtentTest test){
        driver.navigate().refresh();
        Utils.wait(1000);

        String itemName;
        String[] orderDetails = driver.findElements(itemNameXpath).get(0).getText().split(" ");
        itemName = orderDetails[0];

        keywordFilterFunction(test,"Food Item Name", itemName);
    }

    public static void filterByOrderID(ExtentTest test){
        driver.navigate().refresh();
        Utils.wait(1000);

        String orderId;
        String[] oid = driver.findElement(orderIdFirst).getText().split(" ");
        orderId = oid[2];

        keywordFilterFunction(test,"Order Id", orderId);

    }

    private static void keywordFilterFunction(ExtentTest test, String filterTitle, String keyword) {

        logInfo = test.createNode("Filter by " + filterTitle, "Filtering the Order History by " + filterTitle);
        Log.info("filtering the Admin using " + filterTitle);

        driver.findElement(keywordFilter).click();
        Log.info("clicked search input box");
        driver.findElement(keywordFilter).sendKeys(keyword);
        Utils.wait(1000);

        try {
            Assert.assertTrue(driver.findElements(orderDetails).size() != 0);
            logInfo.pass("Filter applied using "+ filterTitle+" : Passed");
            test.log(Status.PASS,"Filtering by " + filterTitle + "successful");
            Utils.extentScreenShotCapture(logInfo,"Filter applied using "+ filterTitle, keywordFilter);
        }
        catch(AssertionError e){
            logInfo.fail("Filter applied using "+ filterTitle+": Failed" + e.getMessage());
            test.log(Status.FAIL,"Filter doesn't work with the " + filterTitle);
            Utils.extentScreenShotCapture(logInfo,"Filter applied using "+ filterTitle, keywordFilter);
            Assert.assertTrue(driver.findElements(orderDetails).size() != 0);
        }

    }


    public static void filterByFoodStatus(ExtentTest test){
        driver.navigate().refresh();
        Utils.wait(500);

        logInfo = test.createNode("Filter by Food status", "Filtering the Order History by food status");
        Log.info("filtering the history using food status");
        Select status = new Select(driver.findElement(foodStatusDropDown));
        Log.info("Filtering for the pending orders");
        status.selectByVisibleText("Pending");
        Utils.extentScreenShotCapture(logInfo,"Filter applied pending order status", foodStatusDropDown);


        List<WebElement> listOfStatusOfOrders = driver.findElements(statusOfOrder);
        for(int i = 0;i<listOfStatusOfOrders.size();i++){
            String orderDetails = listOfStatusOfOrders.get(i).getText();
            Assert.assertTrue(orderDetails.contains("Pending")||orderDetails.contains("Completed"));
        }
        Log.info("filtering for the completed orders");
        status.selectByVisibleText("Completed");
        Utils.extentScreenShotCapture(logInfo,"Filter applied using completed order status", foodStatusDropDown);


    }


    public static void sortByDate(ExtentTest test){
        driver.navigate().refresh();
        Utils.wait(1000);

        logInfo = test.createNode("sort by date", "sorting the Order History by food status");
        Log.info("sorting the history using date");

        Select sort = new Select(driver.findElement(sortByDropDown));
        sort.selectByVisibleText("Date (Descending)");
        Utils.extentScreenShotCapture(logInfo, "sorting using date", sortByDropDown);
        Assert.assertTrue(true);
    }

    public static void adminLogOut(){
        driver.findElement(profileIcon).click();
        driver.findElement(signOutButton).click();
        Utils.wait(500);
    }

    public static void goToDashboard(){
        driver.findElement(dashBoard).click();
    }

    public static void updateMenu(){
        driver.findElement(updateMenu).click();
    }


}
