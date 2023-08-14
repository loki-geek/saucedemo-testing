package com.testing.pageobjects;


import org.openqa.selenium.*;

import com.testing.testcases.BaseClassSD;

public class ChckoutCompletePage {

	WebDriver driver;
	
	public ChckoutCompletePage(WebDriver driver) {//passing the webdriver.
		this.driver = driver;
	}
	
	By chckoutCompleteTxt = By.xpath("//span[contains(text(),'Complete')]");
	By backHome = By.id("back-to-products");

	
	public void orderPlaced() {//checking whether the checkout process is complete..
		boolean flag = driver.findElement(chckoutCompleteTxt).isDisplayed();
		if(flag) {
			BaseClassSD.logger.info("@CheckoutCompletePage-Order placed successfully!! :)");
			driver.findElement(backHome).click();
			BaseClassSD.logger.info("@CheckoutCompletePage-BackHome button clicked");
		}
	}
	
	
}
