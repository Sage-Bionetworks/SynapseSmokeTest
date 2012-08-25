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
public class ProjectCreatePage extends Page {
	
	private static final String projectNameInputXpath = "//tboby/tr/td/input[@class='homesearchbox addProjectBox";
	private static final String createButtonXpath = "//a[contains(text(), 'Create')]";
	
	@FindBy(xpath = projectNameInputXpath)
	WebElement inputProjectName;
	@FindBy(xpath = createButtonXpath)
	WebElement btnCreate;
	
	public ProjectCreatePage(WebDriver driver) {
		super(driver);
	}
	
	// TODO: Should return ProjectPage
	public EntityPage createProject(String name) throws InterruptedException {
		inputProjectName.clear();
		inputProjectName.sendKeys(name);
		Thread.sleep(500);
		btnCreate.click();
		EntityPage p = PageFactory.initElements(driver, EntityPage.class);
		return p;
	}
	
}
