package org.sagebionetworks.smokeTest;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page {
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//button[contains(., 'Login')]")
	private WebElement login; // Login button
	@FindBy(xpath="//button[contains(., 'Register')]")
	private WebElement register; // Register button
	
	public void check() {
		// TODO: Get better test
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
	}
	
	public LoginPage login() {
		login.click();
		LoginPage loginPage = PageFactory.initElements(this.driver, LoginPage.class);
		return loginPage;
	}
	
	public RegisterPage register() {
		register.click();
		RegisterPage registerPage = PageFactory.initElements(this.driver, RegisterPage.class);
		return registerPage;
	}
	
	// TODO: Add anon browse here
	
}
