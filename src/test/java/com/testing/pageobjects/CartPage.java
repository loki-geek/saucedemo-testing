package com.testing.pageobjects;


import java.util.List;

import org.openqa.selenium.*;

import com.testing.testcases.BaseClassSD;

public class CartPage {
	
	WebDriver driver;
	public CartPage(WebDriver driver) {//passing the webdriver to the pagefactory for finding the webelements.
		this.driver = driver;
	}
	
	By continueShop = By.id("continue-shopping");
	By checkout = By.cssSelector("#checkout");
	By removeBtn = By.xpath("//*[text()='Remove']");
	By productCount = By.className("shopping_cart_badge");
	
	
	public boolean isproductAdded() {//checking whether the product is added to cart..
		WebElement productsAddedNum = driver.findElement(productCount);
		BaseClassSD.logger.info("Checking whether products added to cart..");
		BaseClassSD.logger.info("@CartPage-Total products added: "+productsAddedNum.getText());
		return productsAddedNum.isDisplayed();
	}
	
	public void removeProducts() {//removing the product from the cart.
		List<WebElement> removeProducts = driver.findElements(removeBtn);
		for(WebElement rmvBtn : removeProducts) {
			rmvBtn.click();
			BaseClassSD.logger.info("@CartPage-Remove product button clicked");
		}
	}
	
	public void continueShopping() {//clicking the 'continue shopping' btn in the cart Page.
		driver.findElement(continueShop).click();
		BaseClassSD.logger.info("@CartPage-Continue shopping button clicked.");
	}
	public void checkoutClick() {//clicking the 'Checkout' btn
		driver.findElement(checkout).click();
		BaseClassSD.logger.info("@CartPage-Checkout button clicked.");
	}
}
