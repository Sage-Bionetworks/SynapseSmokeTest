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
public class PasswordResetPage extends Page {
	
	private static final String newPasswordInputXpath = "//input[@id='"+ UiConstants.ID_INP_NEW_PASSWORD + "-input"+"']";
	private static final String confirmPasswordInputXpath = "//input[@id='"+ UiConstants.ID_INP_CONFIRM_PASSWORD + "-input"+"']";
	private static final String btnSubmitXpath = "//table[@id='" + UiConstants.ID_BTN_SUBMIT +"']/tbody/tr[2]/td[2]/em/button";
	
	@FindBy(xpath=newPasswordInputXpath)
	WebElement inputNewPassword;
	@FindBy(xpath=confirmPasswordInputXpath)
	WebElement inputConfirmPassword;
	@FindBy(xpath=btnSubmitXpath)
	WebElement btnSubmit;
	
	public PasswordResetPage (WebDriver driver) {
		super(driver);
	}
	
	public LoginPage resetPassword(String password) {
		inputNewPassword.clear();
		inputNewPassword.sendKeys(password);
		inputConfirmPassword.clear();
		inputConfirmPassword.sendKeys(password);
		btnSubmit.click();
		LoginPage p = PageFactory.initElements(driver, LoginPage.class);
		return p;
	}
	
	public boolean isPasswordResetPage() {
		// TODO: Check for Reset Password panel
		return true;
	}
}