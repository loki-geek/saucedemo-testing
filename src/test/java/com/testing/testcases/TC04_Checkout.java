package com.testing.testcases;

import org.testng.Assert;
import org.testng.annotations.*;


import com.testing.pageobjects.*;
import com.testing.utils.JsonFileUtils;
import com.testing.utils.ReportingSD;

public class TC04_Checkout extends BaseClassSD {
//test flow: 
//login -> add_product_to_cart -> click_checkoutBtn -> enter_billingInfo_and_continue -> click_finishBtn_to_complete_checkout -> back_to_homePage -> logout.
	
	ProductsPage productPage;
	LoginPage loginPage;
	CartPage cartPage;
	CheckoutInfoPage chckoutInfoPage;
	ChckoutOverviewPage chckoutOverviewPage;
	ChckoutCompletePage chckoutCompletePage;
	
	@BeforeClass
	public void initialize() {//creating objects of PageObject classes
		loginPage = new LoginPage(driver);
		productPage = new ProductsPage(driver);
		cartPage = new CartPage(driver);
		chckoutInfoPage = new CheckoutInfoPage(driver);
		chckoutOverviewPage = new ChckoutOverviewPage(driver);
		chckoutCompletePage = new ChckoutCompletePage(driver);
		ReportingSD.extentTest = ReportingSD.extentReport.createTest(this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "validLogin", dataProviderClass = JsonFileUtils.class )
	public void login(String uname , String password) {//logging in with valid username and password.
		try {
			loginPage.login(uname, password);
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
			Assert.fail();
		}
	}
	
	@Test(dependsOnMethods = "login")
	public void addProductToCart() {// adding the product to the cart
		try {
			System.out.println("adding product to cart");
			productPage.addToCart();
			System.out.println("product added to cart..");
			productPage.goToCart();
			while (!cartPage.isproductAdded()) {
				cartPage.continueShopping();
				productPage.addToCart();
				productPage.goToCart();
			}
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
			Assert.fail();
		}
	}

	@Test(dependsOnMethods = "addProductToCart", dataProvider = "billingInfo", dataProviderClass = JsonFileUtils.class)
	public void checkoutTheProduct(String firstName, String lastName, String zipCode ) {
		try {
			//after product is added to cart, checkout btn is clicked.
			cartPage.checkoutClick();
			//Now, billing info must be entered.
			chckoutInfoPage.enterBillingInfo(firstName, lastName, zipCode);
			chckoutInfoPage.continueCheckout();//finally, continue btn is clicked
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
			Assert.fail();
		}
	}
	
	@Test(dependsOnMethods = "checkoutTheProduct")
	public void finishCheckout() {
		try {
			//this is the order summary page.
			chckoutOverviewPage.finishOrder();
			//finish btn is clicked.
			chckoutCompletePage.orderPlaced();
			//checking whether the order is placed.
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
			Assert.fail();
		}
	}
	
	@AfterClass
	public void logOut() {
		productPage.logout();
	}
}
