package com.testing.pageobjects;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.gson.JsonArray;
import com.testing.testcases.BaseClassSD;
import com.testing.utils.JsonFileUtils;


public class ProductsPage {
	
	public JsonFileUtils jsonFileObj;
	WebDriver driver;
	public ProductsPage(WebDriver driver) {//passing the webdriver.
		this.driver = driver;
	}
	
	By moreOptions = By.cssSelector("#react-burger-menu-btn");
	By allItems = By.id("inventory_sidebar_link");
	By aboutBtn = By.id("about_sidebar_link");
	By logOut = By.id("logout_sidebar_link");
	By resetAppState = By.id("reset_sidebar_link");
	By closeMenu = By.cssSelector("#react-burger-cross-btn");
	By cartBtn = By.cssSelector(".shopping_cart_link");
	By productName = By.cssSelector(".inventory_item_name");
	By productPrice = By.cssSelector("div.inventory_item_price");
	By addToCart = By.cssSelector(".btn");
	By sortBtn = By.cssSelector("[data-test='product_sort_container']");
	By linkedInBtn = By.linkText("LinkedIn");
	By fbBtn = By.linkText("Facebook");
	By twtrBtn = By.linkText("Twitter");
	
	//sorting the products based on sortByOption values from the excel.
	public void sortProducts(String sortValue) {
		Select select = new Select(driver.findElement(sortBtn));
		BaseClassSD.logger.info("@InventoryPage-SortBy dropdown clicked.");
		select.selectByVisibleText(sortValue);
		BaseClassSD.logger.info("@InventoryPage-"+sortValue+" button clicked");
	}
	//writing the sorted product list to the json file.
	public void writeSortedProducts(String sortBy) {
		List<WebElement> productNames =  driver.findElements(productName);
		List<WebElement> productPrices = driver.findElements(productPrice);
		int productCount=productNames.size();
		JsonArray sortedArr = new JsonArray(productCount);
		for(int i=0;i<productCount;i++) {
			String prodStr=productNames.get(i).getText();
			for(int j=i;j<productCount;) {
				sortedArr.add(prodStr+" - "+productPrices.get(j).getText());
				break;
			}
		}
		jsonFileObj.jsonObjWrite.add(sortBy.toLowerCase(), sortedArr);
	}
	
	
	//adding random product to the cart.
	public void addToCart() {
		List<WebElement> addToCartBtn = driver.findElements(addToCart);
		int numOfProd = randomNum();
		for(int i=0;i<numOfProd;i++) {
			try {
				WebElement randProd =addToCartBtn.get(randomNum());
				BaseClassSD.scroll(randProd);
				randProd.click();
			}catch(Exception e) {
				continue;
			}
		}
	}
	
	//clicking the 'Cart' btn
	public void goToCart() {
		BaseClassSD.scroll(driver.findElement(cartBtn));
		driver.findElement(cartBtn).click();
		BaseClassSD.logger.info("@InventoryPage-Cart button clicked.");
	}
	//logging out
	public void logout() {
		driver.findElement(moreOptions).click();
		BaseClassSD.logger.info("@InventoryPage-MoreOptions button clicked");
		driver.findElement(logOut).click();
		BaseClassSD.logger.info("@InventoryPage-Logout button clicked");
	}
	//random number generating method
	public int randomNum() {
		Random randNum = new Random();
		int max=driver.findElements(addToCart).size();
		int num=randNum.nextInt(max);
		return num+1;
	}
}
