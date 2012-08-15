package org.sagebionetworks.smokeTest;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author xavier
 */
public class UserHomePage extends Page {
	
	private static final String startProjectBtnXpath = "//a[@id='id_btn_start_project']";
	
	@FindBy(xpath=startProjectBtnXpath)
	WebElement btnStartProject;
	
	public UserHomePage(WebDriver driver) {
		super(driver);
	}
	
	public ProjectCreatePage startProject() throws InterruptedException {

		btnStartProject.click();
		Thread.sleep(500);
		ProjectCreatePage p = PageFactory.initElements(driver, ProjectCreatePage.class);
		return p;
	}
}
