package com.testing.testcases;

import org.testng.annotations.*;

import com.testing.pageobjects.LoginPage;
import com.testing.pageobjects.ProductsPage;
import com.testing.utils.JsonFileUtils;
import com.testing.utils.ReportingSD;

public class TC02_SortProducts extends BaseClassSD {
	//test flow: login -> sort the products by name and price -> write the sorted product list to excel file -> logout.
	ProductsPage productPage;
	LoginPage loginPage;
	
	@BeforeClass
	public void initialize() {//creating the objects of pageObject classes
		loginPage = new LoginPage(driver);
		productPage = new ProductsPage(driver);
		productPage.jsonFileObj = new JsonFileUtils("write");
		ReportingSD.extentTest = ReportingSD.extentReport.createTest(this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "validLogin", dataProviderClass = JsonFileUtils.class)
	public void login(String uname , String password) {//logging in with valid userData
		try {
			loginPage.login(uname, password);
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
		}
	}
	
	@Test(dependsOnMethods = "login", dataProvider = "sortValues", dataProviderClass = JsonFileUtils.class)
	public void sorting(String sortvalue) {//sorting the products by sortOptionValues from excelFile.
		try {
			productPage.sortProducts(sortvalue);
			productPage.writeSortedProducts(sortvalue);
		}catch(Exception e) {
			ReportingSD.exceptn = e.getMessage();
		}
		
	}
	
	@AfterClass
	public void logOut() {
		productPage.jsonFileObj.jsonWriteAndClose();//writing the data to excelFIle and close it.
		productPage.logout();
	}
	
}
