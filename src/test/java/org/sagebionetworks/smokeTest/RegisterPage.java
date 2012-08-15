package org.sagebionetworks.smokeTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends Page {

	private static final String newUserEmailInputXpath = "//input[@id='"+ UiConstants.ID_INP_REG_EMAIL_ADDRESS + "-input"+"']";
	private static final String newUserFirstNameInputXpath = "//input[@id='"+ UiConstants.ID_INP_REG_FIRST_NAME + "-input"+"']";
	private static final String newUserLastNameInputXpath = "//input[@id='"+ UiConstants.ID_INP_REG_LAST_NAME + "-input"+"']";
	private static final String btnRegisterXpath = "//table[@id='"+UiConstants.ID_BTN_REGISTER2+"']/tbody/tr[2]/td[2]/em/button";

	@FindBy(xpath = newUserEmailInputXpath)
	WebElement inputNewUserEmail;
	@FindBy(xpath = newUserFirstNameInputXpath)
	WebElement inputNewUserFirstName;
	@FindBy(xpath = newUserLastNameInputXpath)
	WebElement inputNewUserLastName;
	@FindBy(xpath = btnRegisterXpath)
	WebElement btnRegister;
	
	public RegisterPage(WebDriver driver) {
		super(driver);
	}
	
	public RegisterPage register(String userEmail, String userFirstName, String userLastName) throws InterruptedException {
		inputNewUserEmail.clear();
		inputNewUserEmail.sendKeys(userEmail);
		Thread.sleep(100);
		inputNewUserFirstName.clear();
		inputNewUserFirstName.sendKeys(userFirstName);
		Thread.sleep(100);
		inputNewUserLastName.clear();
		inputNewUserLastName.sendKeys(userLastName);
		Thread.sleep(1000);
		btnRegister.click();
		Thread.sleep(1000);
		// TODO: Add check for "Register with Synapse" label
		return this;
	}
	
	public boolean isUserCreated() {
		// TODO: Check for panel "Register with Synapse"
		return true;
	}
}
