package com.me.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.me.base.BaseClass;

@SuppressWarnings("unused")
public class TC_005 extends BaseClass{
		
	private final String loginSheetName = "Login.xlsx";
	private final int loginSheetIndex = 2;
	
	@Test
	public void newsLetter() throws FileNotFoundException, IOException{
		
		
		driver.get(propertyReader.getValue("url"));
		driver.findElement(By.xpath("//li[@class=\'dropdown\']/a[contains(@href,\'index.php?route=account/account\')]")).click();
		driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[contains(@href,'index.php?route=account/login\')]")).click();
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(excelReader.readCell(loginSheetName, 1, 0));
		
		driver.findElement(By.id("input-password")).clear();
		driver.findElement(By.id("input-password")).sendKeys(excelReader.readCell(loginSheetName, 1, 1));
		
		driver.findElement(By.xpath("//input[@type=\'submit\' and @value=\'Login\']")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//aside[@id='column-right']//a[1][contains(text(),'My Account')]")).isDisplayed(), "Error : Not Succesfully Logged In");
		
		driver.findElement(By.xpath("//aside[@id='column-right']/div[@class='list-group']/a[contains(@href,'newsletter')]")).click();
		
		driver.findElement(By.xpath("//input[@value='0' and @name='newsletter']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='0' and @name='newsletter']")).isSelected());
		
		driver.findElement(By.xpath("//input[@type='submit' and @value='Continue']")).click();
		
		WebElement newsLetterSuccess = driver.findElement(By.xpath("//div[@id='account-account']//div[@class='alert alert-success alert-dismissible']"));
		
		Assert.assertTrue(newsLetterSuccess.isDisplayed(), "Error : Password Change Unsuccessful");
		
		String successMsg = newsLetterSuccess.getText();
		utils.saveIntoFile("TC_005_NewsletterChangeSucess", successMsg);

		driver.findElement(By.xpath("//div[@id=\'top-links\']//li[@class=\'dropdown\']//a[@title=\'My Account\']")).click();	
		driver.findElement(By.xpath("//ul[@class=\'dropdown-menu dropdown-menu-right\']//a[text()=\'Logout\']")).click();
		
		String logoutSucessMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
		
		Assert.assertEquals(logoutSucessMsg.trim(), "You have been logged off your account. It is now safe to leave the computer.","Error : Logout Failed");
		
		WebElement logoutMsg = driver.findElement(By.xpath("//div[@id=\'content\' and @class=\'col-sm-9\']"));
			
		String logoutMsg1 = logoutMsg.findElement(By.tagName("h1")).getText();
		StringBuilder completelogoutMsg = new StringBuilder(logoutMsg1+"\n");
			
		logoutMsg.findElements(By.tagName("p")).forEach((web) -> completelogoutMsg.append(web.getText()+"\n"));
		utils.saveIntoFile("TC_005_LogoutMsg.txt", completelogoutMsg.toString());
		
	}

}
