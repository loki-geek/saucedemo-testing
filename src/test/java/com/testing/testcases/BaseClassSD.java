package com.testing.testcases;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import org.testng.annotations.*;

import com.testing.utils.JsonFileUtils;
import com.testing.utils.ReadConfig;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClassSD {
	
	public static WebDriver driver;
	public JsonFileUtils jsonUtils;
	public ReadConfig config;
	public static Logger logger;
	
	
	@Parameters("browser")//passing the browser name from the testng.xml file
	@BeforeClass
	public void setup(String browserName) {
		//initializing the logger using log4j.properties file
		logger = Logger.getLogger("SauceDemoWebTesting");
		PropertyConfigurator.configure("log4j.properties");
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		
		switch(browserName.toLowerCase()) {//invoking the browser
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			logger.info("Edge Browser opened");
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			logger.info("Firefox Browser opened");
			break;
		default:
			//WebDriverManager.chromedriver().setup();
			System.setProperty("webdriver.chrome.driver", "/bin/chromedriver");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setHeadless(true);
			chromeOptions.add_argument('--disable-dev-shm-usage');
			chromeOptions.add_argument('--ignore-ssl-errors=yes');
			chromeOptions.add_argument('--ignore-certificate-errors');
			driver = new ChromeDriver(chromeOptions);
			logger.info("Chrome Browser opened");
		
		}
		//creating the object for ReadConfig class
		config = new ReadConfig();
		
		driver.manage().window().maximize();//maximizing the window
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(numConvert(config.readData("implicitWait(sec)"))));
		//getting implicitwait and pageloadtimeout values from config file.
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(numConvert(config.readData("pageLoadtimeout(sec)"))));
		
		//getting the url value from config file.
		System.out.println("trying to open url");
		driver.get(config.readData("homeUrl"));
		System.out.println("url opened");
		logger.info("URL opened");
		jsonUtils = new JsonFileUtils();
		//creating object for JsonFileUtils class
			
	}	
	
	public static String screenShot(String imgName) {//taking screenshot
		String dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String filePath =System.getProperty("user.dir")+"/screenshots/"+imgName+"_"+dateFormat+".png";
		try {
			TakesScreenshot screenshot = (TakesScreenshot)driver;
			File src = screenshot.getScreenshotAs(OutputType.FILE);
			File dest = new File(filePath);
			FileHandler.copy(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Screenshot taken.");
		return filePath;
	}
	
	
	
	public static void scroll(WebElement element) {//scrolling action
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", element);
		logger.info("scrolling to a element");
	}
	
	public long numConvert(String value) {
		return Long.parseLong(value);
	}
	
	public void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void closeBrowser() {//closing the browser after each testclass.
		driver.close();
		logger.info("Browser closed");
	}
	
}
