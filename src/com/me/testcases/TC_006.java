package com.me.testcases;

import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.me.base.BaseClass;
import com.me.utilities.MyUtilities;

@SuppressWarnings("unused")
public class TC_006 extends BaseClass{
	
	private final String loginSheetName = "Login.xlsx";
	private final int loginSheetIndex = 2;
	
	private final String addToCartSheetName = "AddToCart.xlsx";
	private final int addToCartSheetIndex = 4;
	
	private final String loginSheetName2 = "Billing.xlsx";
	private final int loginSheetIndex2 = 5;
	
	@Test
	public void addToCart() throws FileNotFoundException, IOException, InterruptedException, FindFailed, AWTException {
		
		
		driver.get(propertyReader.getValue("url"));
		driver.findElement(By.xpath("//li[@class=\'dropdown\']/a[contains(@href,\'index.php?route=account/account\')]")).click();
		driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[contains(@href,'index.php?route=account/login\')]")).click();
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(excelReader.readCell(loginSheetName, 1, 0));
		
		driver.findElement(By.id("input-password")).clear();
		driver.findElement(By.id("input-password")).sendKeys(excelReader.readCell(loginSheetName, 1, 1));
		
		driver.findElement(By.xpath("//input[@type=\'submit\' and @value=\'Login\']")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//aside[@id='column-right']//a[1][contains(text(),'My Account')]")).isDisplayed(), "Error : Not Succesfully Logged In");
		
		driver.findElement(By.xpath("//a[contains(@href, 'route=product/category&path=25')]")).click();
		driver.findElement(By.xpath("//a[contains(text(), 'Monitors')]")).click();
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		MyUtilities.saveScreenshot(screenshot);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("input-sort")));
		
		driver.findElement(By.id("input-sort")).click();
		Select select = new Select(driver.findElement(By.id("input-sort")));
		select.selectByVisibleText("Price (Low > High)");
		
		//driver.findElement(By.xpath("//div[@class='row']//div[@class='product-thumb']/div[@class='image']/a/img[@alt=\'Apple Cinema 30\"\' and @title=\'Apple Cinema 30\"\']")).click();
		driver.findElement(By.xpath("//div[@class='row']//div[@class='product-thumb']/div[@class='image']/a/img[@alt=\'Samsung SyncMaster 941BW\' and @title=\'Samsung SyncMaster 941BW\']")).click();	
		
		
		driver.findElement(By.id("input-quantity")).clear();
		driver.findElement(By.id("input-quantity")).sendKeys("1");
		
		driver.findElement(By.id("button-cart")).click();
		
		WebElement succ = driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']"));
		
		Assert.assertTrue(succ.isDisplayed(), "Error! Not Added To Cart Successfully");
		
		utils.saveIntoFile("TC_006_AddedToCartSucess", succ.getText());
		
//		new Actions(driver).moveToElement(driver.findElement(By.cssSelector("a[title='Shopping Cart']"))).click().perform();
		
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,0)");
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[4]/a")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='checkout-cart']//a[contains(@href,'/index.php?route=checkout/cart')]")));
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='checkout-cart']//a[contains(@href,'/index.php?route=checkout/cart')]")).isDisplayed(), "Error! Checkout Page Didn't Open");

		MyUtilities.saveScreenshot(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE));
		
		driver.findElement(By.xpath("//div[@id=\'content\']//tr[1]//input")).clear();
		driver.findElement(By.xpath("//div[@id=\'content\']//tr[1]//input")).sendKeys("1");
		
		driver.findElement(By.xpath("//div[@id='content']//tr[1]//button[1]")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']")).getText().contains("Success"), "Error! Cart Not Updated Successfully");
		
		driver.findElement(By.xpath("//a[contains(@href,'/index.php?route=checkout/checkout') and @class=\'btn btn-primary\']")).click();
		
		
		//If Checkout Page Doesn't Open
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='content']/h1[contains(text(),'Continue')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='content']/h1[contains(text(),'Continue')]")).isDisplayed(), "Error! Payment Page Didn't Open");
//		driver.findElement(By.xpath("//ul[@class=\'breadcrumb\']//a[contains(@href,'/opencart1/index.php?route=common/home')]")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='radio' and @value='new' and @name=\'payment_address\']")));
		driver.findElement(By.xpath("//input[@type='radio' and @value='new' and @name=\'payment_address\']")).click();
	//	new Actions(driver).moveToElement(driver.findElement(By.id("input-payment-firstname")));
		
		driver.findElement(By.id("input-payment-firstname")).clear();
		driver.findElement(By.id("input-payment-firstname")).sendKeys(excelReader.readCell(loginSheetName2, 1, 0));
		
		driver.findElement(By.id("input-payment-lastname")).clear();
		driver.findElement(By.id("input-payment-lastname")).sendKeys(excelReader.readCell(loginSheetName2, 1, 1));
		
		driver.findElement(By.id("input-payment-company")).clear();
		driver.findElement(By.id("input-payment-company")).sendKeys(excelReader.readCell(loginSheetName2, 1, 2));
		
		driver.findElement(By.id("input-payment-address-1")).clear();
		driver.findElement(By.id("input-payment-address-1")).sendKeys(excelReader.readCell(loginSheetName2, 1, 3));
		
		driver.findElement(By.id("input-payment-address-2")).clear();
		driver.findElement(By.id("input-payment-address-2")).sendKeys(excelReader.readCell(loginSheetName2, 1, 4));
		
		driver.findElement(By.id("input-payment-city")).clear();
		driver.findElement(By.id("input-payment-city")).sendKeys(excelReader.readCell(loginSheetName2, 1, 5));
		
		driver.findElement(By.id("input-payment-postcode")).clear();
		driver.findElement(By.id("input-payment-postcode")).sendKeys(excelReader.readCell(loginSheetName2, 1, 6));
		
		driver.findElement(By.id("input-payment-country")).click();
		new Select(driver.findElement(By.id("input-payment-country"))).selectByVisibleText(excelReader.readCell(loginSheetName2, 1, 7));
		
		driver.findElement(By.id("input-payment-zone")).click();
		new Select(driver.findElement(By.id("input-payment-zone"))).selectByVisibleText(excelReader.readCell(loginSheetName2, 1, 8));
		
		driver.findElement(By.id("button-payment-address")).click();
		
		
		Thread.sleep(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-shipping-address")));
		driver.findElement(By.id("button-shipping-address")).click();

		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-shipping-method")));
//		driver.findElement(By.xpath("//input[@type='radio' and @name='shipping_method' and @value='flat.flat']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='collapse-shipping-method']//textarea[@name='comment']")).clear();
		driver.findElement(By.xpath("//div[@id='collapse-shipping-method']//textarea[@name='comment']")).sendKeys(excelReader.readCell(loginSheetName2, 1, 9));
		driver.findElement(By.id("button-shipping-method")).click();
		
		
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-payment-method")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='collapse-payment-method']//textarea[@name='comment']")).clear();
		driver.findElement(By.xpath("//div[@id='collapse-payment-method']//textarea[@name='comment']")).sendKeys(excelReader.readCell(loginSheetName2, 1, 10));
		driver.findElement(By.xpath("//input[@type='checkbox' and @name='agree' and @value='1']")).click();
		driver.findElement(By.id("button-payment-method")).click();
		

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-confirm")));
		MyUtilities.saveScreenshot(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE));
		
		driver.findElement(By.id("button-confirm")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='content']/h1"))));
		String orderSuccess = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		Assert.assertEquals("Your order has been placed!", orderSuccess);
		
		WebElement orderSuccesse = driver.findElement(By.id("content"));
		
		String orderMsg = orderSuccesse.findElement(By.tagName("h1")).getText();
		StringBuilder completeOrderMsg = new StringBuilder(orderMsg+"\n");
			
		orderSuccesse.findElements(By.tagName("p")).forEach((web) -> completeOrderMsg.append(web.getText()+"\n"));
		utils.saveIntoFile("TC_006_OrderMsg.txt", completeOrderMsg.toString());
		
		
		//Logout
		driver.findElement(By.xpath("//div[@id=\'top-links\']//li[@class=\'dropdown\']//a[@title=\'My Account\']")).click();	
		driver.findElement(By.xpath("//ul[@class=\'dropdown-menu dropdown-menu-right\']//a[text()=\'Logout\']")).click();
		
		String logoutSucessMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
		Assert.assertEquals(logoutSucessMsg.trim(), "You have been logged off your account. It is now safe to leave the computer.","Error : Logout Failed");
		
		WebElement logoutMsg = driver.findElement(By.xpath("//div[@id=\'content\' and @class=\'col-sm-9\']"));
		
		String logoutMsg1 = logoutMsg.findElement(By.tagName("h1")).getText();
		StringBuilder completelogoutMsg = new StringBuilder(logoutMsg1+"\n");
			
		logoutMsg.findElements(By.tagName("p")).forEach((web) -> completelogoutMsg.append(web.getText()+"\n"));
		utils.saveIntoFile("TC_006_LogoutMsg.txt", completelogoutMsg.toString());

	}
	
	
}