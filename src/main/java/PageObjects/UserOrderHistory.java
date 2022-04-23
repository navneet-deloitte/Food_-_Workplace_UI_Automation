package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;
import java.util.*;

public class UserOrderHistory extends BaseClass {

    public static By keyword_field_path = By.xpath("//input[@id='mat-input-0']");
    public static By reorder_button = By.xpath("//a[contains(text(),'Reorder')]");
    public static By customize_button = By.xpath("//div[@class='content']/div[3]/a[2]");
    public static By view_status_button = By.xpath("//button[@title='View Status']");
    public static By order_id = By.xpath("//div[@class='orderid']");
    public static By cancel_button = By.xpath("//button[@title='Cancel']");
    public static By pop_up_button = By.xpath("//button[contains(text(),'Yes')]");
    public static By itemlist = By.xpath("//div[@class='main text']/div[2]/div/div/div/div[1]/div[3]");
    public static By cancel_popup_message = By.xpath("//div[@id='toast-container']");


    public static List<String> items_in_cart = null;
    public static String filePath= properties.getProperty("itemsData_OnTheHouse");

    public static ExtentTest logInfo = null;


    /* Validating the Ordered Food Items in the "Order History" page */
    public static void validatingOrderedFoodItems(ExtentTest test) throws IOException{

        logInfo = test.createNode("Validating the Ordered Food Items in the Order History Page");
        Utils.implicitWait(20);

        List<List<String>> ordered_food_items_by_user = HandleCSV.newFileOperation(filePath) ;
        Log.info("Read the Ordered Food Items from the CSV File. For Validating the Cart Items");

        /* To Fetch the Item names from the "ordered Food items by user" list */
        List<String> ordered_items_list = new ArrayList<>();
        assert ordered_food_items_by_user != null;
        for (List<String> value : ordered_food_items_by_user) {
            ordered_items_list.add(String.valueOf(value.get(0)));
        }

        /* To Fetch the  Food Items from the cart after placing the order */
        items_in_cart = new ArrayList<>();
        List<WebElement> order_list = driver.findElements(itemlist);
        for (int i = 0 ; i < order_list.size()-1; i++){
            String itemName = order_list.get(i).getText();
            String[] arrOfStr = itemName.split("x");
            items_in_cart.add(arrOfStr[0]);
        }
        Log.info("Fetched the 'Item Names' from the Cart in Order History Page");
        Collections.sort(items_in_cart);
        Collections.sort(ordered_items_list);
        for (int i = 0; i<items_in_cart.size(); i++) {
            Assert.assertEquals(items_in_cart.get(i).replaceAll(" ", ""), ordered_items_list.get(i).replaceAll(" ", ""));
        }
        Log.info("Successfully Validated the Ordered Food Items in Order History Page");
    }

    /* Clicking on 'Keyword' Field */
    public static void filterOption_Keyword(ExtentTest test) throws IOException{
        logInfo = test.createNode("Applying Filters in Order History Page");
        driver.findElement(keyword_field_path).click();
        Log.info("Clicked on 'Keyword Tab' in Order History Page");
    }

    /* Clicking on reorder Button */
    public static void clickOnReorderButton(ExtentTest test) throws IOException{

        logInfo = test.createNode("Reorder Functionality in Order History Page");
        List<WebElement> reorder_buttons = driver.findElements(reorder_button);
        Utils.extentScreenShotCapture(logInfo,"Reorder Button",reorder_button);
        Utils.searchandclick(reorder_buttons.get(0));
        Log.info("Clicked on ReOrder Button");
        Utils.extentScreenShotCapture(logInfo,"Pop Up Message",pop_up_button);
        driver.findElement(pop_up_button).click();


    }

    /* Validating the Customize Functionality in Order History Page */
    public static void validatingCustomizeFunctionality(ExtentTest test) throws IOException{

        logInfo = test.createNode("Validating Customize Functionality in Order History Page");
        List<WebElement> customize_buttons = driver.findElements(customize_button);
        Utils.extentScreenShotCapture(logInfo,"Customize Button",customize_button);
        Utils.searchandclick(customize_buttons.get(0));
        Log.info("Clicked on Customize Button");
        //call the "add items to the cart" method
    }

    /* Validating The View Status Functionality in order History Page*/
    public static void validatingViewStatusFunctionality(ExtentTest test) throws InterruptedException,IOException{

        logInfo = test.createNode("Validating View Status functionality in Order History Page");
        List<WebElement> orderId = driver.findElements(order_id);
        String order_id = orderId.get(0).getText();
        List<WebElement> view_status_buttons = driver.findElements(view_status_button);
        Utils.searchandclick(view_status_buttons.get(0));
        Log.info("Clicked on View Status Button");
        Utils.wait(10);
        Utils.scrollUp();
        UserOrderStatus.validatingFoodItemsInOrderStatusPage(test);

    }

    /* Validating the Cancel Food Item Button in Order History Page */
    public static void validatingCancelFoodItemFuntionality(ExtentTest test) throws InterruptedException,IOException{

        logInfo = test.createNode("Validating Cancel Functionality in Order History Page");
        List<WebElement> orderId = driver.findElements(order_id);
        String order_ID = orderId.get(0).getText();
        Utils.extentScreenShotCapture(logInfo,"Before cancel the Food Item",order_id);
        Utils.scrollDown();
        Utils.wait(100);
        List<WebElement> cancel_buttons = driver.findElements(cancel_button);
        cancel_buttons.get(0).click();
        Log.info("Clicked on Cancel Button");
        driver.findElement(pop_up_button).click();
        String pop_message =  driver.findElement(cancel_popup_message).getText();  //popup text
        String new_order_ID = orderId.get(0).getText();
        Utils.extentScreenShotCapture(logInfo,"After cancel the Food Item",order_id);
        Assert.assertNotEquals(order_ID,new_order_ID);
        Assert.assertTrue(pop_message.contains("Success"));
        Log.info("Successfully Validated the Cancel Functionality in Order Status page");
    }

    /* Validating the Cancel food Items in Order History page */
    public static void validatingCancelFoodItemFuntionalityAfterChangingStatus(ExtentTest test) throws InterruptedException,IOException{

        logInfo = test.createNode("Validating Cancel Functionality in Order History Page after Changing the Status");
        List<WebElement> orderId = driver.findElements(order_id);
        String order_ID = orderId.get(0).getText();
        Utils.extentScreenShotCapture(logInfo,"Before cancel the Food Item",order_id);
        Utils.scrollDown();
        Utils.wait(100);
        List<WebElement> cancel_buttons = driver.findElements(cancel_button);
        cancel_buttons.get(0).click();
        Log.info("Clicked on Cancel Button");
        driver.findElement(pop_up_button).click();
        String pop_message =  driver.findElement(cancel_popup_message).getText();  //popup text
        String new_order_ID = orderId.get(0).getText();
        Utils.extentScreenShotCapture(logInfo,"After cancel the Food Item",order_id);
        Assert.assertEquals(order_ID,new_order_ID);
        Assert.assertTrue(pop_message.contains("Failed"));
        Log.info("Successfully Validated the Cancel Functionality in Order Status page");
    }
}
