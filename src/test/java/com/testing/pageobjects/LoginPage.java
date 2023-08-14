package com.testing.pageobjects;

import org.openqa.selenium.*;
import com.testing.testcases.BaseClassSD;


public class LoginPage {

	WebDriver driver;
	//passing the webdriver.
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	By uname = By.cssSelector("#user-name");
	By pwd = By.name("password");
	By loginBtn = By.id("login-button");
	By loginErrorMsg = By.cssSelector("[data-test='error']");
	
	
	//entering the username and password.
	public void login(String UsrName, String Passwrd){
		driver.findElement(uname).sendKeys(UsrName);
		BaseClassSD.logger.info("@LoginPage-Username entered");
		driver.findElement(pwd).sendKeys(Passwrd);
		BaseClassSD.logger.info("@LoginPage-Password entered");
		driver.findElement(loginBtn).click();
		BaseClassSD.logger.info("@LoginPage-login button clicked");
		
	}
	
	public boolean loginError() {
		return driver.findElement(loginErrorMsg).isDisplayed();
	}
	


}
