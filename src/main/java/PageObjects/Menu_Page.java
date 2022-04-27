package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import testAutomationListner.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu_Page extends BaseClass {
    static By onTheHouse = By.xpath("//div[contains(text(),'On The House')]");
    static By onYou = By.xpath("//div[contains(text(),'On You')]");
    static By cartButton = By.xpath("//a[contains(text(),'Cart')]");
    static By loginButton = By.xpath("//a[contains(text(),'Login')]");
    static By orderHistoryButton = By.xpath("//a[contains(text(),'Order History')]");
    static By profileIcon = By.xpath("//mat-icon[contains(text(),'account_circle')]");
    static By logoutButton=By.xpath("//i[@class='fa fa-sign-out']");
    static By AllItems= By.xpath("//div[contains(@class,'itemname')]");
    static By Add_button = By.xpath("//div[@class='addbuttons']");
    static By items_cart=By.xpath("//div[@class='cent1']");
    static By item_count_cart=By.xpath("//div[@class='col-1 cent']");
    static By price_cart=By.xpath("//div[@class='col-2 cent ng-star-inserted']");
    static By total_price_cart=By.xpath("//div[@class='col-4 right']");
    static By place_order=By.xpath("//button[@class='btn btnOrder']");
    static By click_yes=By.xpath("//button[contains(text(),'Yes')]");
    static By entire_cart=By.xpath("//div[@class='col-lg-3 col-sm-12 cart']");
    static By get_order_id=By.xpath("//span[@class='order-id']/span");
    static By order_status=By.xpath("//div[@class='row order-details']");
    static By order_saved=By.xpath("//div[contains(text(),'The order is saved')]");
    static By orderplaced=By.xpath("//div[contains(text(),'Order Placed')]");
    static By itemDiv = By.xpath("//div[contains(@class,'card-body')]");
    static By logo=By.xpath("//img[@class='logo-img']");




    public static ExtentTest logInfo = null;
    public static String pathOnTheHouse = properties.getProperty("itemsData_OnTheHouse");
    public static String order_id_file = properties.getProperty("order_id_csv");
    public static String pathOnYou = properties.getProperty("itemsData_OnYou");


    public static void clickOnTheHouseFunctionality() {
        Utils.searchandclick(driver.findElement(onTheHouse));
    }

    public static void clickOnYou() {
        WebElement on_you=driver.findElement(onYou);
        Utils.searchandclick(on_you);
    }

    public static void clickonplaceorder(){
        WebElement placeorder=driver.findElement(place_order);
        Utils.searchandclick(placeorder);
    }

    public static void clickonyesinpopup(){
        WebElement yes=driver.findElement(click_yes);
        Utils.searchandclick(yes);
    }

    public static void clickCartButton() {
        WebElement cart=driver.findElement(cartButton);
        Utils.searchandclick(cart);
    }

    public static void hoverCardItem(){
        Utils.hover(itemDiv);
    }

    public static void scrollToTop(){
        WebElement loginButtonElement = driver.findElement(logo);
        Utils.scrollUpTo(loginButtonElement);
    }

    public static void clickLoginButton(ExtentTest test) {
        Utils.searchandclick(driver.findElement(loginButton));
    }

    public static void clickOrderHistoryButton() {
        Utils.searchandclick(driver.findElement(orderHistoryButton));
    }

    public static void logoutFunctionality() {
        Utils.searchandclick(driver.findElement(profileIcon));
        Utils.waitForVisibilityOfElements(logoutButton, 10);
        driver.findElement(logoutButton).click();
    }

    public static void addToCartFunctionality(ExtentTest test){
        logInfo=test.createNode("Adding items to cart");
        Utils.wait(2000);
        clickOnTheHouseFunctionality();
        Utils.wait(2000);
        add_items(properties.getProperty("itemsData_OnTheHouse"));
        clickOnYou();
        Utils.wait(2000);
        add_items(pathOnYou);
        clickCartButton();
        Utils.wait(500);
        logInfo.pass("Items are added to cart");
        Utils.extentScreenShotCapture(logInfo,"Items added to cart",entire_cart);
    }

    public static void deletefromCartFunctionality(ExtentTest test) throws InterruptedException, IOException {
        logInfo=test.createNode("deleting items from cart");
        clickOnTheHouseFunctionality();
        Utils.wait(3000);
        delete_items(pathOnTheHouse);
        clickOnYou();
        Utils.wait(3000);
        delete_items(pathOnYou);
        clickCartButton();
        Utils.wait(500);
        logInfo.pass("Items are deleted from cart");
        Utils.extentScreenShotCapture(logInfo,"Items deleted from cart",entire_cart);
    }

    public static void delete_items(String location) throws IOException {
        List<List<String>> itemsData = HandleCSV.newFileOperation(location);
        List<WebElement> fooditems = driver.findElements(AllItems);
        Log.info("Deleting the items in cart");
        for (int i = 0;i<itemsData.size();i++) {
            for (int j = 0; j < fooditems.size(); j++) {
                if (fooditems.get(j).getText().equals(itemsData.get(i).get(0))) {
                    int item_count=Integer.parseInt(itemsData.get(i).get(1));
                    int Food_item_place=j+1;
                    try {
                        while (item_count > 0) {
                            List<WebElement> minus = driver.findElements(By.xpath("//mat-tab-body/div[1]/div[1]/div[" + Food_item_place + "]/div/div/div/div/div[2]/div/button"));
                            Utils.searchandclick(minus.get(0));
                            item_count--;
                        }
                    }
                    catch (Exception e){
                        System.out.println("Add the items to delete");
                    }
                }
            }
        }
    }

    public static void cart_validation(ExtentTest test){
        logInfo=test.createNode("Validating cart");
        clickCartButton();
        Log.info("Validating the cart");
        List<List<String>> itemsDataInOnTheHouse;
        List<List<String>> itemsDataInOnYou;

        try {
            itemsDataInOnTheHouse = HandleCSV.newFileOperation(pathOnTheHouse);
            itemsDataInOnYou = HandleCSV.newFileOperation(pathOnYou);
        }catch (IOException ioException){
            Log.error("Fail to read CSV file "+ioException.getMessage());
            logInfo.log(Status.FAIL,"Fail to read CSV file "+ioException.getMessage());
            return;
        }

        int total_cart_value=0,Onhouse_count=0,Onyou_count=0;
        int total_items=itemsDataInOnTheHouse.size()+itemsDataInOnYou.size();
        try {
            String cart_status = null;
            try{
                if((driver.findElement(place_order).isDisplayed())){
                    cart_status="Your cart is not empty";
                }
            }
            catch (Exception e){
                cart_status="Your cart is empty";
            }
            Assert.assertNotEquals(cart_status,"Your cart is empty");
            List<WebElement> items_incart = driver.findElements(items_cart);
            List<WebElement> items_count = driver.findElements(item_count_cart);
            List<WebElement> items_price_cart = driver.findElements(price_cart);
            Assert.assertEquals(total_items, items_incart.size());
            for (int i = 0; i < total_items; i++) {
                for (int j = 0; j < items_incart.size(); j++) {
                    if (Onhouse_count < itemsDataInOnTheHouse.size() && Onhouse_count <= itemsDataInOnTheHouse.size() & items_incart.get(j).getText().equals(itemsDataInOnTheHouse.get(Onhouse_count).get(0))) {
                        Assert.assertEquals(itemsDataInOnTheHouse.get(Onhouse_count).get(1), items_count.get(j).getText());
                        Assert.assertEquals("Free", items_price_cart.get(j).getText());
                        Onhouse_count++;
                    }
                    if (Onyou_count < itemsDataInOnYou.size() && items_incart.get(j).getText().equals(itemsDataInOnYou.get(Onyou_count).get(0))) {
                        Integer Actual_count = new Integer(itemsDataInOnYou.get(Onyou_count).get(1));
                        Integer Cart_count = new Integer(items_count.get(j).getText());
                        Integer Actual_price = new Integer(itemsDataInOnYou.get(Onyou_count).get(2)) * Actual_count;
                        String Cart_price[] = items_price_cart.get(j).getText().split("₹");
                        Assert.assertEquals(Actual_count, Cart_count);
                        Assert.assertEquals(Actual_price, new Integer(Cart_price[1]));
                        Onyou_count++;
                        System.out.println(Onyou_count);
                        total_cart_value += new Integer(Cart_price[1]);
                    }
                }
            }
            String cart_toatal_price[] = driver.findElement(total_price_cart).getText().split("₹");
            Assert.assertEquals(cart_toatal_price[1], (String.valueOf(total_cart_value)));
            Utils.wait(500);
            logInfo.pass("cart is updated");
            Utils.extentScreenShotCapture(logInfo, "Cart validated", entire_cart);
        }
        catch (AssertionError e){
            Log.info("Cart is not updated properly");
            Utils.wait(500);
            logInfo.fail("Cart is not updated");
            Utils.extentScreenShotCapture(logInfo, "Cart not updated", entire_cart);
        }
    }


    public static void placeTheOrder(ExtentTest test){
        logInfo=test.createNode("Placing the order");
        Log.info("Clicking on place the order");
        clickonplaceorder();
        Log.info("Clicking yes on the pop up window");
        clickonyesinpopup();
        driver.findElement(order_saved);
        logInfo.pass("Order is saved");
        Utils.extentScreenShotCapture(logInfo,"Order saved",order_saved);
        Log.info("Order is saved");
    }

    public static String checkOrderStatusAndId(ExtentTest test){
        logInfo=test.createNode("Checking order is placed and getting order id");
        logInfo.pass("Order is placed");
        Utils.extentScreenShotCapture(logInfo,"Order Placed",orderplaced);
        Log.info("Order is placed");
        Utils.wait(3000);
        Utils.scrollUp();
        String id = driver.findElement(get_order_id).getText();
        Log.info("Storing the Order Id for further refrence");
        HandleCSV.writeJobData(order_id_file,id);
        logInfo.pass("Order id is saved");
        Utils.extentScreenShotCapture(logInfo,"Getting order id",order_status);

        return id;
    }



    public static void add_items(String location){
        List<List<String>> itemsData = new ArrayList<>();
        try {
            itemsData = HandleCSV.newFileOperation(location);
        }catch (IOException ioException){
            Log.error("Fail to read CSV file "+ioException.getMessage());
            logInfo.log(Status.FAIL,"Fail to read CSV file "+ioException.getMessage());
            return;
        }
        List<WebElement> fooditems = driver.findElements(AllItems);
        List<WebElement> Add = driver.findElements(Add_button);
        Log.info("Adding the items to cart");
        for (int i = 0;i<itemsData.size();i++){
            for(int j=0;j<fooditems.size();j++){
                if(fooditems.get(j).getText().equals(itemsData.get(i).get(0))){
                    int item_count = Integer.parseInt(itemsData.get(i).get(1));
                    int Food_item_place = j + 1;
                    while (item_count > 0){
                        try{
                            List<WebElement> Plus = driver.findElements(By.xpath("//mat-tab-body/div[1]/div[1]/div[" + Food_item_place + "]/div/div/div/div/div[2]/div/button"));
                            Utils.searchandclick(Plus.get(1));
                            item_count--;
                        }
                        catch (Exception e){
                            Utils.searchandclick(Add.get(j));
                            item_count--;
                        }
                    }
                }
            }
        }
    }


}
