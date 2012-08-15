package org.sagebionetworks.smokeTest;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

/*
 *	This is the page we land on originally 
*/
public class HomePage extends Page {
	
	public HomePage(WebDriver driver) throws RuntimeException {
		super(driver);
	}
	
	public EntityPage gotoSCR() {
		EntityPage p;
		WebElement el;
		// TODO: Fix id and change xpath
		el = driver.findElement(By.xpath("//div/h5/a[@href='#Synapse:syn150935']"));
		el.click();
		
		p = PageFactory.initElements(driver, EntityPage.class);
		return p;
	}
	
	// Can't get the xpath to work...
	public SearchResultsPage doSearch(String searchTerm) {
		return null;
//		WebElement btn = driver.findElement(By.xpath("//a[class='gwt-Anchor x-component'][text()='Search']"));
//		WebElement inp = driver.findElement(By.xpath("//input[class='text'][class='homesearchbox']"));
//		SearchResultsPage p;
//		inp.sendKeys(searchTerm);
//		btn.click();
//		p = PageFactory.initElements(driver, SearchResultsPage.class);
//		return p;
	}
}
