package org.sagebionetworks.smokeTest;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

/*
 *	This is the page we land on originally 
*/
public class UserHomePage extends Page {
	
	public UserHomePage(WebDriver driver) throws RuntimeException {
		super(driver);
	}
	
}
