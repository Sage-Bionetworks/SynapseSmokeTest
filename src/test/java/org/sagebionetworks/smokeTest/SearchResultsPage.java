package org.sagebionetworks.smokeTest;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class SearchResultsPage extends Page {

	public SearchResultsPage(WebDriver driver) {
		super(driver);
	}

}