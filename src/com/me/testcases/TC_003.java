package com.me.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.me.base.BaseClass;
import com.me.utilities.ExcelReader;

@SuppressWarnings("unused")
public class TC_003 extends BaseClass {
	
	private final String sheetName = "Password.xlsx";
	private final int loginSheetIndex = 3;
	
	private final String pwordSheet = "Password";
	
	@Test
	public void passwordChange() throws FileNotFoundException, IOException  {
		
		ExcelReader pword = new ExcelReader("Password.xlsx");
		
		
		driver.get(propertyReader.getValue("url"));
		driver.findElement(By.xpath("//li[@class=\'dropdown\']/a[contains(@href,\'index.php?route=account/account\')]")).click();
		driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[contains(@href,'index.php?route=account/login\')]")).click();
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(excelReader.readCell(sheetName, 1, 0));
		
		driver.findElement(By.id("input-password")).clear();
		driver.findElement(By.id("input-password")).sendKeys(excelReader.readCell(sheetName, 1, 1));
		
		driver.findElement(By.xpath("//input[@type=\'submit\' and @value=\'Login\']")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//aside[@id='column-right']//a[1][contains(text(),'My Account')]")).isDisplayed(), "Error : Not Succesfully Logged In");
		
		driver.findElement(By.xpath("//aside[@id='column-right']/div[@class='list-group']/a[contains(@href,'password')]")).click();
		
		String accnt = driver.findElement(By.xpath("//div[@id='content']/form/fieldset/legend")).getText();
		Assert.assertEquals(accnt, "Your Password", "Error : Account Page Didn't Open");
		
		
		driver.findElement(By.id("input-password")).clear();
		driver.findElement(By.id("input-password")).sendKeys(pword.readCell(pwordSheet, 1, 0));
		
		driver.findElement(By.id("input-confirm")).clear();
		driver.findElement(By.id("input-confirm")).sendKeys(pword.readCell(pwordSheet, 1, 1));
		driver.findElement(By.xpath("//input[@type=\'submit\' and @value=\'Continue\']")).click();
		
		
		WebElement pwordChangeSuccess = driver.findElement(By.xpath("//div[@id='account-account']//div[@class='alert alert-success alert-dismissible']"));
		
		Assert.assertTrue(pwordChangeSuccess.isDisplayed(), "Error : Password Change Unsuccessful");
		
		String successMsg = pwordChangeSuccess.getText();
		utils.saveIntoFile("TC_003_PasswordChangeSucess", successMsg);
		
		
		driver.findElement(By.xpath("//div[@id=\'top-links\']//li[@class=\'dropdown\']//a[@title=\'My Account\']")).click();	
		driver.findElement(By.xpath("//ul[@class=\'dropdown-menu dropdown-menu-right\']//a[text()=\'Logout\']")).click();
		
		String logoutSucessMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
		Assert.assertEquals(logoutSucessMsg.trim(), "You have been logged off your account. It is now safe to leave the computer.","Error : Logout Failed");
	}
}
