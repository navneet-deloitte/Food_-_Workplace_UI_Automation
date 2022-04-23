package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class UserOrderStatus extends BaseClass {

    public static By order_id_path = By.xpath("//span[@class='order-id']/span");
    public static By received = By.xpath("//div[@class='container-fluid body']/div[4]/div[1]/div[1]");
    public static By cooking = By.xpath("//div[@class='container-fluid body']/div[4]/div[2]/div[1]");
    public static By ready = By.xpath("//div[@class='container-fluid body']/div[4]/div[2]/div[1]");
    public static By pop_up_button = By.xpath("//button[contains(text(),'Yes')]");
    public static By re_order_confirmation_popup = By.xpath("//div[@id='toast-container']");
    public static By details_button = By.xpath("//div[@class='details-heading']/span");
    public static By confirmation_text = By.xpath("//p[@class='card-text']");
    public static By re_ordered_items = By.xpath("//div[@class='row order-row']/div/span");

    public static ExtentTest logInfo = null;

    /* Validating Ordered Food Items in Order Status Page */
    public static void validatingFoodItemsInOrderStatusPage(ExtentTest test) throws IOException{

        logInfo = test.createNode("Validating the Reordered food Items in Order Status Page");
        Utils.implicitWait(20);
        Utils.scrollDown();
        String confirmation_message = driver.findElement(re_order_confirmation_popup).getText();  /* popup text */
        Utils.extentScreenShotCapture(logInfo,"ReOrder Confirmation Message",re_order_confirmation_popup);
        Utils.scrollDown();
        String reOrderconfirmationText = driver.findElement(confirmation_text).getText();        /*Re Order Confirmation Message */
        Log.info(reOrderconfirmationText);
        Utils.extentScreenShotCapture(logInfo,"Order Status",confirmation_text);
        driver.findElement(details_button).click();                                              /* details button in "Order status" Page */
        Log.info("Clicked On Details Button In Order Status Page");
        List<String> items_in_reordered_cart = new ArrayList<>();
        List<WebElement> reOrderedItemList = driver.findElements(re_ordered_items);
        for (int i = 0; i < reOrderedItemList.size(); i++) {
            if (i % 3 == 0) {
                String itemName = reOrderedItemList.get(i).getText();
                items_in_reordered_cart.add(itemName);
            }
        }
        Utils.extentScreenShotCapture(logInfo,"Items in Order Status Page",re_ordered_items);
        Log.info("Fetched the Item names from the Order Status Page");
        Collections.sort(items_in_reordered_cart);

        Log.info("Asserting the Reordered Items in Order Status Page");
        for (int i = 0; i < UserOrderHistory.items_in_cart.size(); i++) {
            Assert.assertEquals(UserOrderHistory.items_in_cart.get(i).replaceAll(" ", ""), items_in_reordered_cart.get(i).replaceAll(" ", ""));
        }
        Log.info("Successfully validated the Re ordered Food Items in Order Status Page");
    }

    /* Validing Food status in Order Status Page  */
    public static void validatingFoodStatusReceived(String id, ExtentTest test) throws InterruptedException, IOException {

        logInfo = test.createNode("Validating the Status Of the Ordered Food");
        Utils.wait(3000);
        String orderID = driver.findElement(order_id_path).getText();
        Utils.extentScreenShotCapture(logInfo,"Order ID",order_id_path);
        Utils.scrollDown();
        String status_message = driver.findElement(received).getText();
        Utils.extentScreenShotCapture(logInfo,"Order Status Message",received);
        Assert.assertTrue(status_message.contains("Order Recieved, Chef will perform his Magic Soon!!"));
        Assert.assertEquals(orderID,id);
        Log.info("Successfully Validated the Food Status in Order Status Page");

    }

    /* Validing Food status in Order Status Page  */
    public static void validatingFoodStatusCooking(String id, ExtentTest test) throws InterruptedException, IOException {

        logInfo = test.createNode("Validating the Status Of Food");
        Utils.wait(3000);
        String orderID = driver.findElement(order_id_path).getText();
        Utils.extentScreenShotCapture(logInfo,"Order ID",order_id_path);
        Utils.scrollDown();
        String status_message = driver.findElement(cooking).getText();
        Utils.extentScreenShotCapture(logInfo,"Order Status Message",cooking);
        Assert.assertTrue(status_message.contains("Delicious Food in Making"));
        Assert.assertEquals(orderID,id);
        Log.info("Successfully Validated the Food Status in Order Status Page");

    }

    /* Validing Food status in Order Status Page  */
    public static void validatingFoodStatusReady(String id, ExtentTest test) throws InterruptedException, IOException {

        logInfo = test.createNode("Validating the Status Of Food");
        Utils.wait(3000);
        String orderID = driver.findElement(order_id_path).getText();
        Utils.extentScreenShotCapture(logInfo,"Order ID",order_id_path);
        Utils.scrollDown();
        String status_message = driver.findElement(ready).getText();
        Utils.extentScreenShotCapture(logInfo,"Order Status Message",ready);
        Assert.assertTrue(status_message.contains("Food is Ready to Eat!!"));
        Assert.assertEquals(orderID,id);
        Log.info("Successfully Validated the Food Status in Order Status Page");

    }
}