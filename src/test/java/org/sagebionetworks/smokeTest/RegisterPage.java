package org.sagebionetworks.smokeTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends Page {

	private static final String newUserEmailInputXpath = "x-auto-23-input";
	private static final String newUserFirstNameInputXpath = "x-auto-24-input";
	private static final String newUserLastNameInputXpath = "x-auto-25-input";
	private static final String btnRegisterXpath = "";

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
	
	public void register(String userEmail, String userFirstName, String userLastName) {
		inputNewUserEmail.clear();
		inputNewUserEmail.sendKeys(userEmail);
		inputNewUserFirstName.clear();
		inputNewUserFirstName.sendKeys(userFirstName);
		inputNewUserLastName.clear();
		inputNewUserLastName.sendKeys(userLastName);
		btnRegister.click();
		
	}
}