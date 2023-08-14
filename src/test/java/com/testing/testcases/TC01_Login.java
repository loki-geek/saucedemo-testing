package com.testing.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.testing.pageobjects.LoginPage;
import com.testing.pageobjects.ProductsPage;
import com.testing.utils.JsonFileUtils;
import com.testing.utils.ReportingSD;

public class TC01_Login extends BaseClassSD {
	//test flow: login with different set of username and password -> if logged in, then logout. else fail the testcase.
	LoginPage loginPage;
	ProductsPage productPage;
	
	
	@BeforeClass
	public void initialize() {//creating the objects of the PageObject classes
		loginPage = new LoginPage(driver);
		productPage = new ProductsPage(driver);
		ReportingSD.extentTest =ReportingSD.extentReport.createTest(this.getClass().getSimpleName());//creating a test in extentreport
	}
	
	@Test(dataProvider = "loginData", dataProviderClass = JsonFileUtils.class) //passing the values thru dataprovider.
	public void loginTest(String username, String password){
		driver.navigate().refresh();
		loginPage.login(username, password);//calling login method in LoginPage class.
		try {
			String loggedInUrl=driver.getCurrentUrl();
			Assert.assertEquals(loggedInUrl, config.readData("productPageUrl"));
			logger.info("ProductPage url verified. Login successful!");
			productPage.logout();
		} catch(AssertionError e) {
			ReportingSD.exceptn = e.getMessage();
			loginPage.loginError();//checking whether login error msg is present.
			logger.info("Login error. Invalid username or password");
			Assert.fail();
		}
	}
}
