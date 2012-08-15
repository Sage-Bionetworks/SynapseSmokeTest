package org.sagebionetworks.smokeTest;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {
	
	private static final String synapseLoginInputXpath = "//input[@id='" + UiConstants.ID_INP_EMAIL_NAME + "-input']";
	private static final String synapseLoginInputId = UiConstants.ID_INP_EMAIL_NAME + "-input";
	private static final String synapsePasswordInputXpath = "//input[@id='" + UiConstants.ID_INP_EMAIL_PASSWORD + "-input']";
	private static final String synapsePasswordInputId = UiConstants.ID_INP_EMAIL_PASSWORD + "-input";
	private static final String synapseLoginButtonXpath = "//table[@id='" + UiConstants.ID_BTN_LOGIN2 + "']/tbody/tr[2]/td[2]/em/button";
	private static final String openIdLoginButtonXpath = "//form[@id='gapp-openid-form']//button[@id='"+ UiConstants.ID_BTN_GOOGLE_LOGIN + "'][@type='submit']";
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id=synapseLoginInputId)
	private WebElement inputSynapseLogin;
	
	@FindBy(id=synapsePasswordInputId)
	private WebElement inputSynapsePassword;
	
	@FindBy(xpath=synapseLoginButtonXpath)
	private WebElement btnSynapseLogin;
	
	@FindBy(xpath=openIdLoginButtonXpath)
	private WebElement btnOpenIdLogin;
	
	public UserHomePage synapseLogin(String user, String password) throws InterruptedException {
		inputSynapseLogin.clear();
		inputSynapseLogin.sendKeys(user);
		inputSynapsePassword.clear();
		inputSynapsePassword.sendKeys(password);
		Thread.sleep(500);
		btnSynapseLogin.click();
		Thread.sleep(2000);
		if (this.loggedIn()) {
			UserHomePage p = PageFactory.initElements(driver, UserHomePage.class);
			return p;
		} else {
			return null;
		}
		// TODO: Throw exception if login fails
	}
	
	public UserHomePage synapseLoginWithTOS(String user, String password) throws InterruptedException {
		inputSynapseLogin.clear();
		inputSynapseLogin.sendKeys(user);
		inputSynapsePassword.clear();
		inputSynapsePassword.sendKeys(password);
		Thread.sleep(500);
		btnSynapseLogin.click();
		Thread.sleep(2000);
		// The TOS should show up
		WebElement e = driver.findElement(By.xpath("//*[contains(text(),'I Agree')]"));
		e.click();
		UserHomePage p = PageFactory.initElements(driver, UserHomePage.class);
		return p;
	}
	
	public UserHomePage openIdLogin(String user, String password) throws InterruptedException {
		UserHomePage p;
		btnOpenIdLogin.click();
		// Handle logged in / logged out of Google cases
		Thread.sleep(2000);	// to handle indirections if already logged in
		if ("Google Accounts".equals(driver.getTitle().trim())) { // Not logged in
			// We need to log into Google
			WebElement el;
			el = driver.findElement(By.id("Email"));
			el.sendKeys(user);
			el = driver.findElement(By.id("Passwd"));
			el.sendKeys(password);
			el = driver.findElement(By.id("signIn"));
			el.click();
			Thread.sleep(2000);
		}
		// We should be back at user homepage
		// TODO: Check
		p = PageFactory.initElements(driver, UserHomePage.class);
		return p;
	}
	
}
