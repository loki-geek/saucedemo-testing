package com.testing.pageobjects;

import org.openqa.selenium.*;
import com.testing.testcases.BaseClassSD;

public class CheckoutInfoPage {
	
	WebDriver driver;
	
	public CheckoutInfoPage(WebDriver driver) {//passing the webdriver.
		this.driver = driver;
	}

	
	By fNameTxt = By.id("first-name");
	By lNameTxt = By.cssSelector("#last-name");
	By zipCode = By.name("postalCode");
	By cancelBtn = By.id("cancel");
	By continueChckout = By.id("continue");
	
	
	//entering the billing data like firstname, lastname and zipcode
	public void enterBillingInfo(String fname, String lname, String zipcode) {
		driver.findElement(fNameTxt).sendKeys(fname);
		BaseClassSD.logger.info("@CheckoutInfoPage-FirstName entered");
		driver.findElement(lNameTxt).sendKeys(lname);
		BaseClassSD.logger.info("@CheckoutInfoPage-LastName entered");
		driver.findElement(zipCode).sendKeys(zipcode);
		BaseClassSD.logger.info("@CheckoutInfoPage-ZipCode entered");
		
	}
	
	public void continueCheckout() {//clicking the 'continue' btn
		driver.findElement(continueChckout).click();
		BaseClassSD.logger.info("@CheckoutInfoPage-Continue button clicked");
	}
	
}


