package com.testing.pageobjects;


import org.openqa.selenium.*;

import com.testing.testcases.BaseClassSD;

public class ChckoutOverviewPage {
	
	WebDriver driver;
	public ChckoutOverviewPage(WebDriver driver) {//passing the webdriver to the pagefactory for finding the webelements.
		this.driver = driver;
	}

	By cancelOrder = By.id("cancel");
	By finishCheckout = By.name("finish");

	
	public void finishOrder() {//clicking the 'finish' btn to complete the checkout process.
		driver.findElement(finishCheckout).click();
		BaseClassSD.logger.info("@CheckoutOverviewPage-Finish button clicked");
	}
	
	
}
