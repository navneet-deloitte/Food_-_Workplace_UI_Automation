package PageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import resources.objects.Item;
import testAutomationListner.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateMenuPage extends BaseClass {

    public static By updateMenuBtn = By.xpath("//a[contains(@class,'history ng-star-inserted')and text()='Update Menu']");
    public static By addItemBtn = By.xpath("//button[contains(@class,'btn button')]");
    public static String editItemBtnXpath = "//button[contains(@class,'btn edit')]";
    public static By addItemFrame = By.xpath("//div[contains(@class,'cdk-overlay-pane')]");
    public static By name = By.xpath("//input[@placeholder ='Enter Item Name']");
    public static By price = By.xpath("//input[@placeholder= 'Enter Item Price']");
    public static By details = By.xpath("//input[@placeholder = 'Enter Item Details']");
    public static By duration = By.xpath("//input[@placeholder = 'Enter Item Duration']");
    public static By imageUrl = By.xpath("//input[@placeholder = 'Enter Image URL']");
    public static By addBtn = By.xpath("//button[contains(@class,'btn add ng-star-inserted')]");
    public static By cancelBtn = By.xpath("//button[contains(@class,'btn') and text() = 'Cancel']");
    public static By categoryDropDown = By.xpath("(//SELECT[@formcontrolname = 'category'])");
    public static By vegNonVegDropDown = By.xpath("(//SELECT[@formcontrolname = 'veg'])");
    public static By availabilityDropDown = By.xpath("(//SELECT[@formcontrolname = 'available'])");
    public static By searchBox = By.xpath("//input[@placeholder = 'Search Items']");
    public static By availabilityToggle = By.xpath("//label[contains(@class,'mat-slide-toggle-label')]");
    public static By itemsXpath = By.xpath("//mat-row[contains(@class,'mat-row cdk-row')]");
    public static By itemTitleXpath = By.xpath("//mat-table[1]/mat-row/mat-cell[2]");
    public static By toastMsg = By.xpath("//div[contains(@class,'toast-bottom-right toast-container')]");
    public static By errorMsg = By.xpath("//div[contains(@class,'add-item-error ng-star-inserted')]");


    public static ExtentTest extentTest = null;
    public static List<WebElement> itemList;
    public static List<WebElement> itemTitleList;
    public static ArrayList<String> usedItemList = new ArrayList<>();

    public static String editedIteName = "";


    private static int unUsedItem(){

        itemList = driver.findElements(itemsXpath);
        itemTitleList = driver.findElements(itemTitleXpath);


        try {
            List<List<String>> onTheHouse_items = HandleCSV.newFileOperation(properties.getProperty("itemsData_OnTheHouse"));
            List<List<String>> onYou_items = HandleCSV.newFileOperation(properties.getProperty("itemsData_OnYou"));
            if(onTheHouse_items != null && onYou_items != null) {
                addItemInList(onTheHouse_items);
                addItemInList(onYou_items);
                System.out.println(usedItemList);
            }else{
                return -1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0 ;i<itemTitleList.size();i++){
            String itemName = itemTitleList.get(i).getText();
            if(!usedItemList.contains(itemName)) {
                Log.info(itemName+"is to be edited at index "+i);
                editedIteName = "Edited " + itemName;
                return i;
            }
        }

        return -1;
    }

    private static void addItemInList(List<List<String>> list){

        for(int i =0;i<list.size();i++){
            usedItemList.add(list.get(i).get(0));
        }
    }

    public static void clickUpdateMenuBtn(){
        driver.findElement(updateMenuBtn).click();
    }

    public static void clickAddItemBtn() {
        driver.findElement(addItemBtn).click();
    }

    public static void clickAddBtn(){
        driver.findElement(addBtn).click();
    }

    public static void clickCancelBtn(){
        driver.findElement(cancelBtn).click();
    }

    public static void clickEditItemBtn(int index) {
        WebElement editItemBtn = driver.findElements(By.xpath(editItemBtnXpath)).get(index);
        Utils.scrollUpTo(editItemBtn);
        Utils.wait(500);
        Log.info("clicking edit item button");
        editItemBtn.click();
    }

    public static void switchAddItemFrame(){

        WebElement frame = driver.findElement(addItemFrame);

//        Utils.switchFrame(frame);
    }

    public static void fillCompleteData(ExtentTest test){
        fillItemData(test,true);
    }

    public static void fillInCompleteData(ExtentTest test){
        fillItemData(test,false);
    }

    private static void fillItemData(ExtentTest test, boolean fillAllData) {

        String testTile = "Add new Item Test with ";

        if(fillAllData)
            testTile += "all details";
        else
            testTile += "incomplete details";

        extentTest = test.createNode(testTile);

        Item addItem = HandleCSV.getItemsDetails(properties.getProperty("new_items_data_csv")).get(0);
        System.out.println("name "+addItem.getName());
        fillFiled(name,addItem.getName());
        fillFiled(price,addItem.getPrice());
        fillFiled(imageUrl,addItem.getImgUrl());

        Utils.select_from_dropdown(categoryDropDown,addItem.getCategory());
        Utils.select_from_dropdown(vegNonVegDropDown,addItem.getVeg_nonVeg());

        if(fillAllData){
            fillFiled(details,addItem.getDetails());
            fillFiled(duration,addItem.getDuration());
            Utils.select_from_dropdown(availabilityDropDown,addItem.getAvailability());
            extentTest.pass("Item's All details added");
            Utils.highlightElement(addItemFrame,"green");
            Utils.extentScreenShotCapture(extentTest, "Adding new item");

        }else{
            extentTest.pass("Item's some details are not added");
        }

        clickAddBtn();
        Log.info("clicking add item button after filling details");

        if(!fillAllData) {
            extentTest.pass("Failed to Add item with incomplete details");
            Utils.highlightElement(errorMsg,"blue");
            Utils.extentScreenShotCapture(extentTest, "Adding new item");
            return;
        }

        Utils.waitForVisibilityOfElements(toastMsg,1);
        Utils.extentScreenShotCapture(extentTest, "Adding new item successfully", toastMsg);

        Utils.wait(1500);
        searchItem(extentTest,addItem.getName());


    }

    private static void fillFiled(By field, String value) {
        Utils.wait(1000);
        driver.findElement(field).sendKeys(value);

    }

    public static void searchItem(ExtentTest test, String searchKeyword){
        extentTest = test.createNode("Searching item");
        driver.findElement(searchBox).sendKeys(searchKeyword);
        extentTest.log(Status.PASS,"searching " + searchKeyword);
        Log.info("searching " + searchKeyword);
        Utils.wait(1000);
        Utils.extentScreenShotCapture(extentTest,"Searching item",searchBox);
        itemList = driver.findElements(itemsXpath);

        if(itemList.size() == 0){
            test.fail("There is no item matching with " + searchKeyword);
        }else{
            Utils.extentScreenShotCapture(extentTest,"Searching item result",itemList.get(0));

        }
    }

    public static void changeVisibility(int indexOfItem){
        if(indexOfItem<itemList.size()) {
            itemList.get(indexOfItem).findElement(availabilityToggle).click();
        }
        else{
            Log.error("Invalid index");
        }
    }

    public static void editItemDetails(ExtentTest test){

        extentTest = test.createNode("Edit Item Test");

        int editableIndex = unUsedItem();

        if(editableIndex == -1)
            return;

        Item editItem = HandleCSV.getItemsDetails(properties.getProperty("item_edit_data_csv")).get(0);

        clickEditItemBtn(editableIndex);
        Log.info("Click on edit button");

        driver.switchTo().activeElement();

        driver.findElement(price).clear();
        driver.findElement(price).sendKeys(editItem.getPrice());
        driver.findElement(imageUrl).clear();
        driver.findElement(imageUrl).sendKeys(editItem.getImgUrl());

        Log.info("Sending new values");

        Utils.highlightElement(addItemFrame,"blue");
        Utils.extentScreenShotCapture(extentTest,"Edit item successfully");
        clickAddBtn();
//        clickCancelBtn();
        Log.info("Item's price and img edited");

        Utils.wait(1000);

        Utils.scrollUpTo(itemList.get(editableIndex));

        Utils.extentScreenShotCapture(extentTest,"Updated value in Admin Dashboard", itemList.get(editableIndex));

    }

    public static String getEditedIteName() {
        return editedIteName;
    }

}
