package com.testing.testcases;

import org.testng.annotations.*;

import com.testing.pageobjects.CartPage;
import com.testing.pageobjects.LoginPage;
import com.testing.pageobjects.ProductsPage;
import com.testing.utils.JsonFileUtils;
import com.testing.utils.ReportingSD;

public class TC03_AddToCart extends BaseClassSD {
	//test flow: login -> add_product_to_cart -> remove_product_from_cart -> logout.

	ProductsPage productPage;
	LoginPage loginPage;
	CartPage cartPage;
	
	
	@BeforeClass
	public void initialize() {//creating objects of pageObject classes
		loginPage = new LoginPage(driver);
		productPage = new ProductsPage(driver);
		cartPage = new CartPage(driver);
		ReportingSD.extentTest = ReportingSD.extentReport.createTest(this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "validLogin", dataProviderClass = JsonFileUtils.class)
	public void login(String uname , String password) {//logging in with valid username and password
		try {
			loginPage.login(uname, password);
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
		}
	}
	
	@Test(dependsOnMethods = "login")
	public void addProductToCart() {//adding the product to the cart
		try {
			productPage.addToCart();
			productPage.goToCart();
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
		}
	}
	
	@Test(dependsOnMethods = "addProductToCart")
	public void removeProductFromCart() {//removing the product from the cart
		try {
			cartPage.removeProducts();
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
		}
	}
	
	@AfterClass
	public void logOut() {
		productPage.logout();
	}
}
