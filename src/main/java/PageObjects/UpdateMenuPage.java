package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import resources.objects.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateMenuPage extends BaseClass {

    public static By updateMenuBtn = By.xpath("//a[contains(@class,'history ng-star-inserted')and text()='Update Menu']");
    public static By addItemBtn = By.xpath("//a[contains(@class,'btn button')]");
    public static String editItemBtn = "//button[contains(@class,'btn edit')]";
    public static By addItemFrame = By.xpath("//div[contains(@class,'cdk-overlay-pane')]");

    public static By name = By.xpath("//input[@placeholder  = 'Enter Item Name']");
    public static By price = By.xpath("//input[@placeholder  = 'Enter Item Price']");
    public static By details = By.xpath("//input[@placeholder  = 'Enter Item Details']");
    public static By duration = By.xpath("//input[@placeholder  = 'Enter Item Duration']");
    public static By imageUrl = By.xpath("//input[@placeholder  = 'Enter Image URL']");

    public static By addBtn = By.xpath("//button[contains(@class,'btn add ng-star-inserted')]");
    public static By cancelBtn = By.xpath("//button[contains(@class,'btn') and text() = 'Cancel']");

    public static By categoryDropDown = By.xpath("//select[@class,'category']");
    public static By vegNonVegDropDown = By.xpath("//select[@class,'veg']");
    public static By availabilityDropDown = By.xpath("//select[@class,'available']");

    public static By searchBox = By.xpath("//input[@placeholder = 'Search Items]");
    public static By availabilityToggle = By.xpath("//label[contains(@class,'mat-slide-toggle-label')]");


    public static List<WebElement> itemList;
    public static List<WebElement> itemTitleList;

    public static ArrayList<String> usedItemList = new ArrayList<>();


    public static void usedItem(){

        itemList = driver.findElements(By.xpath("//div[contains(@class,'mat-row cdk-row')]"));
        itemTitleList = driver.findElements(By.xpath("//mat-table[1]/mat-row/mat-cell[2]"));

        try {
            List<List<String>> OnTheHouse_items = HandleCSV.newFileOperation("src/main/java/resources/datasheets/ItemsData-OnTheHouse.csv");
            List<List<String>> OnYou_items = HandleCSV.newFileOperation("src/main/java/resources/datasheets/ItemsData-OnYou.csv");
            addItemInList(OnTheHouse_items);
            addItemInList(OnYou_items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addItemInList(List<List<String>> list){

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

    public static void clickEditItemBtn() {
        int index = selectEditItemIndex(0);

        driver.findElement(By.xpath(editItemBtn+"["+index+"]")).click();
    }

    public static int selectEditItemIndex(int index){

        if(usedItemList.contains(itemTitleList.get(index).getText())){
            selectEditItemIndex(index+1);
        }

        return index;
    }

    public static void switchAddItemFrame(){

        WebElement frame = driver.findElement(addItemFrame);

        Utils.switchFrame(frame);
    }

    public static void fillCompleteData(){
        fillItemData(true);
    }

    public static void fillInCompleteData(){
        fillItemData(false);
    }

    private static void fillItemData(boolean fillAllData) {

        Item addItem = HandleCSV.getItemsDetails(properties.getProperty("newItemsDataFile")).get(1);

        driver.findElement(name).sendKeys(addItem.getName());
        driver.findElement(price).sendKeys(addItem.getPrice());
        driver.findElement(imageUrl).sendKeys(addItem.getImgUrl());

        Utils.select_from_dropdown(categoryDropDown,addItem.getCategory());
        Utils.select_from_dropdown(vegNonVegDropDown,addItem.getVeg_nonVeg());


        if(fillAllData){
            driver.findElement(details).sendKeys(addItem.getDetails());
            driver.findElement(duration).sendKeys(addItem.getDuration());
            Utils.select_from_dropdown(availabilityDropDown,addItem.getAvailability());
        }


    }

    public static void clickAddBtn(){
        driver.findElement(addBtn).click();
    }

    public static void clickCancelBtn(){
        driver.findElement(cancelBtn).click();
    }


    public static void searchItem(String searchKeyword){
        driver.findElement(searchBox).sendKeys(searchKeyword);
    }

    public static void changeVisibility(int indexOfItem){
        if(indexOfItem<itemList.size()) {
            itemList.get(indexOfItem).findElement(availabilityToggle).click();
        }
        else{
            // TODO log error

        }
    }

    public static void editItemDetails(){

        Item editItem = HandleCSV.getItemsDetails(properties.getProperty("itemEditDataFile")).get(0);

        editField(price,editItem.getPrice());
        editField(details,editItem.getDetails());

//        driver.findElement(addBtn);
    }


    public static void editField(By field, String data){
        driver.findElement(field).clear();
        driver.findElement(field).sendKeys(data);
    }





}
