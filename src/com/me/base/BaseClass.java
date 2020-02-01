package com.me.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.me.utilities.ExcelReader;
import com.me.utilities.MyUtilities;
import com.me.utilities.PropertyReader;

/**
 * 1. By Default, Text Required To Be Saved In A File Will Get Saved Into A New File.
 *    If You Want To Save In A Single File Then Just Remove The 'File Names' When saveIntoFile() Method Is Called In The Test Cases.
 * 
 * 2. Generated Text Files Can Be Found In src/Resources/outputFiles
 * 
 * 3. Don't Change Folder Structure.
 * 
 * 4. Excel Sheet Must Be Present In src/Resources/testdata Folder And Save The Data Inside Sheet In text Format Only. All The Numbers, Dates Type
 * 	  Values Will Be Read As Text Only. Hence, Data Must Be Saved In Each Cell As Text Format Only.
 * 
 * 5. Browser Drivers Must Be Present In The src/Resources/drivers
 * 
 * 6. All The Required Compatible Jar Files Are Already Present In src/Resources/Jars
 * 
 * 7. For Uploading Files, AutoIT Is Used. .exe File Has Been Used Which Is Present At src/Resources/drivers/UploadFile.exe. (May Work On All Windows OS Only)
 * 
 * 8. properties File Is Present At src/Resources/config. 
 *
 */

public class BaseClass {
	
	protected static PropertyReader propertyReader;
	protected static WebDriver driver;
	protected static ExcelReader excelReader;
	protected static MyUtilities utils;
	protected static WebDriverWait wait;
	
	private static String browserToUse="chrome"; // By Default Chrome Browser To Be Used
	
	@BeforeTest
	public void setUp() throws FileNotFoundException, IOException {
		
		
		propertyReader = new PropertyReader("config.properties");
		excelReader = new ExcelReader(propertyReader.getValue("excel"));
		utils = new MyUtilities();	
		
		String driverPath = System.getProperty("user.dir")+"/Resources/drivers/";
		browserToUse = propertyReader.getValue("browser");
		
		if(browserToUse.equalsIgnoreCase("firefox")||browserToUse.equalsIgnoreCase("mozilla")||browserToUse.equalsIgnoreCase("gecko")) {
			
			System.setProperty("webdriver.gecko.marionette", driverPath+"geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		else if(browserToUse.equalsIgnoreCase("edge")) {
			
			System.setProperty("webdriver.edge.driver", driverPath+"MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}
		
		else if(browserToUse.equalsIgnoreCase("ie")) {
			
			System.setProperty("webdriver.ie.driver", driverPath+"IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
		else {
			
			System.setProperty("webdriver.chrome.driver", driverPath+"chromedriverold.exe");
			driver = new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, 15);
	}
	
	@BeforeMethod
	public void removeCredentials() {
		
		driver.manage().deleteAllCookies();
		
		
	}
	
	@AfterTest
	public void tearDown(){
		
		driver.close();
	}

}
