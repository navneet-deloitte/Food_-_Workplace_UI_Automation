package FoodAtWorkplace;
import PageObjects.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.qameta.allure.*;
import org.testng.annotations.*;
import resources.baseClass.BaseClass;
import resources.helperClasses.Utils;
import testAutomationListner.TestAllureListener;

import java.io.IOException;

@Listeners({TestAllureListener.class})
public class TestScenarios extends BaseClass {

    ExtentTest test;
    LaunchUrl launchUrl = new LaunchUrl();

    //function to invoke driver
    @BeforeClass(groups = {"smoke","regression","functional"})
    public void launchUrl() throws IOException {

        test=extent.createTest("Launch Url");
        launchUrl.getUrl(test);
        Utils.maximizePage();
        Utils.deleteAllCookies();
        Utils.implicitWait(30);

    }


//    @Test(priority = 1, groups = {"smoke"})
    @Epic("EP001")
    @Story("Story: To verifying switching between tabs and visibility of item cards")
    @Feature("Feature1: Switching between tabs")
    @Step("Home page overview")
    @Description("Verifying basic functionalities of home page")
    @Severity(SeverityLevel.MINOR)
    public void websiteOverview(){

        UserLoginPage.verify_logo(test);

        Menu_Page.hoverCardItem();
        Utils.wait(1000);
        Menu_Page.clickOnYou();
        Utils.wait(1000);
        Menu_Page.hoverCardItem();
        Utils.wait(1000);
        Utils.scrollDown();
        Utils.wait(1000);
        Menu_Page.scrollToTop();
        Menu_Page.clickOnTheHouseFunctionality();
        Utils.wait(1000);
        Menu_Page.hoverCardItem();
        Utils.wait(1000);

    }

    
//  @Test(priority = 2,groups = {"re-test","regression","functional"})
    @Epic("EP002")
    @Story("Story: To verifying adding items in cart without login")
    @Feature("Feature2: Adding items in cart")
    @Step("Add items in cart")
    @Description("Verifying the adding item in cart functionality without login and then refresh the page")
    @Severity(SeverityLevel.NORMAL)
    public void addingItemInCartTest(){
        
        ExtentTest addingItemInCartTest = extent.createTest("Adding Item on Cart");
        
        Menu_Page.addToCartFunctionality(addingItemInCartTest);

        ExtentTest emptyCartOnRefreshTest = addingItemInCartTest.createNode("Refresh page after adding items in cart");
        
        Utils.refreshPage();
        Menu_Page.cart_validation(emptyCartOnRefreshTest);
        emptyCartOnRefreshTest.log(Status.FAIL,"Items are deleted after refreshing the page");


    }

//  @Test(priority = 3,groups = {"sanity ","regression","functional"})
    @Epic("EP003")
    @Story("Story: To verifying new user registration via Email")
    @Feature("Feature3: New User Registration")
    @Step("New User Registration")
    @Description("Verifying new user registration via Email")
    @Severity(SeverityLevel.CRITICAL)
    public void userRegistrationTest(){

        ExtentTest useRegistrationTest = extent.createTest("Registering the new user");

        UserLoginPage.goto_login_page();

        UserRegister.register(useRegistrationTest);

        Menu_Page.logoutFunctionality();

    }

//  @Test(priority = 4,groups = {"sanity","regression","functional"})
    @Epic("EP004")
    @Story("Story: To verifying exiting user login")
    @Feature("Feature4: Exiting User Login")
    @Step("Exiting User Login")
    @Description("Verifying exiting user login via Email and and Password")
    @Severity(SeverityLevel.CRITICAL)
    public void UserLoginTest(){

        ExtentTest userLoginTest = extent.createTest("User Login Test");

        Utils.deleteAllCookies();

        UserLoginPage.goto_login_page();

        UserLoginPage.invalid_login(userLoginTest);

        UserLoginPage.valid_login(userLoginTest);

        Menu_Page.addToCartFunctionality(userLoginTest);

        Menu_Page.cart_validation(userLoginTest);

        Menu_Page.logoutFunctionality();

    }

//  @Test(priority = 5,groups = {"sanity","regression"})
    @Epic("EP005")
    @Story("Story: To verifying admin login via Email")
    @Feature("Feature5: Admin login")
    @Step("Admin login")
    @Description("Verifying Admin login via Email")
    @Severity(SeverityLevel.CRITICAL)
    public void adminLoginTest(){
        ExtentTest adminLoginTest = extent.createTest("Admin Login Test");

        UserLoginPage.logout();

        UserLoginPage.goto_login_page();

        AdminLogin.go_to_adminLoginPage();

        AdminLogin.invalid_login(adminLoginTest);

        Utils.wait(3000);

        AdminLogin.login(adminLoginTest);

        Menu_Page.logoutFunctionality();

    }

//    @Test(priority = 6,groups = {"regression","functional"})
    @Epic("EP006")
    @Story("Story: To verifying Placing Order functionality")
    @Feature("Feature6: Placing Order")
    @Step("Adding items in cart and place order")
    @Description("Verifying user can place order after adding items in cart.")
    @Severity(SeverityLevel.CRITICAL)
    public void orderItemTest(){

        ExtentTest orderItemTest = extent.createTest("Order Item Test");

        Utils.deleteAllCookies();

        Menu_Page.scrollToTop();

        UserLoginPage.goto_login_page();

        UserLoginPage.valid_login(orderItemTest);

        Menu_Page.addToCartFunctionality(orderItemTest);

        Menu_Page.cart_validation(orderItemTest);

        Menu_Page.placeTheOrder(orderItemTest);

        String oderId = Menu_Page.checkOrderStatusAndId(orderItemTest);

        UserOrderStatus.clickOrderHistoryBtn();

        UserOrderHistory.validatingViewStatusFunctionality(orderItemTest);

        UserOrderHistory.validatingOrderedFoodItems(orderItemTest);

        // order placed now we have to go for admin login and validate that order received or not.

        Menu_Page.logoutFunctionality();

        UserLoginPage.goto_login_page();

        AdminLogin.go_to_adminLoginPage();

        AdminLogin.login(orderItemTest);

//        TODO
        // validate the order in user dashboard

        // card high ( id )

        Menu_Page.logoutFunctionality();

    }


//    @Test(priority = 7,groups = {"sanity"})
    @Epic("EP003")
    @Story("Story: To verifying filter operation on Admin Dashboard")
    @Feature("Feature7: Filtering on Admin Dashboard")
    @Step("Filtering on Admin Dashboard")
    @Description("Verifying filter operation on Admin Dashboard")
    @Severity(SeverityLevel.NORMAL)
    public void adminDashBoardTest(){

        ExtentTest adminDashBoardTest = extent.createTest("Admin DashBoard Test");

        Menu_Page.scrollToTop();

        UserLoginPage.goto_login_page();

        UserLoginPage.goto_admin_login();

        AdminLogin.login(adminDashBoardTest);

        AdminDashboardPage.filterByAll(adminDashBoardTest,"Cheese Balls");

        AdminDashboardPage.filterByEmailId(adminDashBoardTest);

        AdminDashboardPage.filterByOrderId(adminDashBoardTest);

//        Menu_Page.logoutFunctionality();

    }

  @Test(priority = 8,groups = {"sanity","regression"})
    @Epic("EP008")
    @Story("Story: To verifying filter operation on order history")
    @Feature("Feature7: User Registration")
    @Step("Filtering on  order history")
    @Description("Verifying filter operations on order history")
    @Severity(SeverityLevel.NORMAL)
    public void oderHistoryValidationTest(){
        ExtentTest oderHistoryValidationTest = extent.createTest("Oder History Validation Test");

        Utils.deleteAllCookies();
        Utils.wait(500);
        UserLoginPage.goto_login_page();
        UserLoginPage.goto_admin_login();
        AdminLogin.login(oderHistoryValidationTest);

        // dashboard order id cart highlight

        AdminDashboardPage.goToOrderHistory();

        OrderHistory.filterByItemName(oderHistoryValidationTest);

        OrderHistory.filterByFoodStatus(oderHistoryValidationTest);

        OrderHistory.sortByDate(oderHistoryValidationTest);

        OrderHistory.filterByOrderID(oderHistoryValidationTest);

    }

//    @Test(priority = 9,groups = {"sanity","regression","functional"})
    @Epic("EP009")
    @Story("Story: To verifying changes of order status reflected or not.")
    @Feature("Feature9: Changing Order Status")
    @Step("Changing Order Status")
    @Description("Verifying the changes of order status via Admin dashboard and reflected on user order status")
    @Severity(SeverityLevel.CRITICAL)
    public void changeOrderStatusTest(){

        ExtentTest changeOrderStatusTest = extent.createTest("Change Order Status Test");

        Utils.deleteAllCookies();

        Menu_Page.scrollToTop();

        UserLoginPage.goto_login_page();

        UserLoginPage.valid_login(changeOrderStatusTest);

        Menu_Page.addToCartFunctionality(changeOrderStatusTest);

        Menu_Page.placeTheOrder(changeOrderStatusTest);

        // keep the order id
        String oderId = Menu_Page.checkOrderStatusAndId(changeOrderStatusTest);

        Menu_Page.logoutFunctionality();

        UserLoginPage.goto_login_page();

        UserLoginPage.goto_admin_login();

        AdminLogin.login(changeOrderStatusTest);

        AdminDashboardPage.sendToCooking(changeOrderStatusTest,oderId);

        Menu_Page.logoutFunctionality();

        UserLoginPage.goto_login_page();

        UserLoginPage.valid_login(changeOrderStatusTest);

        Menu_Page.clickOrderHistoryButton();

        UserOrderHistory.validatingViewStatusFunctionality(changeOrderStatusTest);

        UserOrderStatus.validatingFoodStatusCooking(oderId,changeOrderStatusTest);

    }





   @AfterClass
   public void tearDown()
   {
       driver.quit();
   }

}