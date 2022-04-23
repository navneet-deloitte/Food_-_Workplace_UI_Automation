package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import resources.baseClass.BaseClass;
import resources.helperClasses.HandleCSV;
import resources.helperClasses.Utils;
import resources.objects.Item;

import java.util.List;

public class UpdateMenuPage extends BaseClass {

    public static By updateMenuBtn = By.xpath("//a[contains(@class,'history ng-star-inserted')and text()='Update Menu']");
    public static By addItemBtn = By.xpath("//a[contains(@class,'btn button')]");
    public static By editItemBtn = By.xpath("//button[contains(@class,'btn edit')]");
    public static By addItemFrame = By.xpath("//div[contains(@class,'cdk-overlay-pane')]");

    public static By name = By.xpath("//input[@formcontrolname,'name']");
    public static By price = By.xpath("//input[@formcontrolname,'price']");
    public static By details = By.xpath("//input[@formcontrolname,'details']");
    public static By duration = By.xpath("//input[@formcontrolname,'duration']");
    public static By imageUrl = By.xpath("//input[@formcontrolname,'image']");

    public static By addBtn = By.xpath("//button[contains(@class,'btn add ng-star-inserted')]");
    public static By cancelBtn = By.xpath("//button[contains(@class,'btn') and text() = 'Cancel']");

    public static By categoryDropDown = By.xpath("//select[@class,'category']");
    public static By vegNonVegDropDown = By.xpath("//select[@class,'veg']");
    public static By availabilityDropDown = By.xpath("//select[@class,'available']");

    public static By searchBox = By.xpath("//input[@placeholder = 'Search Items]");
    public static By availabilityToggle = By.xpath("//label[contains(@class,'mat-slide-toggle-label')]");


    public static List<WebElement> itemList = driver.findElements(By.xpath("//div[contains(@class,'mat-row cdk-row')]"));



    public static void clickUpdateMenuBtn(){
        driver.findElement(updateMenuBtn).click();
    }

    public static void clickAddItemBtn() {
        driver.findElement(addItemBtn).click();
    }
    public static void clickEditItemBtn() {
        driver.findElement(editItemBtn).click();
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

        Item addItem = HandleCSV.getItems().get(1);

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

    public static void editPrice(){
        driver.findElement(price).sendKeys("45");

    }

    public static void editItem(){

    }




}
