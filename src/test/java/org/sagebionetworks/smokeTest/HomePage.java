package org.sagebionetworks.smokeTest;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

/*
 *	This is the page we land on originally 
*/
public class HomePage extends Page {
	
	private static final String loginButtonXpath = "//table[@id='"+UiConstants.ID_BTN_LOGIN+"']/tbody/tr[2]/td[2]/em/button";
	private static final String registerButtonXpath = "//table[@id='"+UiConstants.ID_BTN_REGISTER+"']/tbody/tr[2]/td[2]/em/button";
	
	public HomePage(WebDriver driver) throws RuntimeException {
		super(driver);
	}
	
	@FindBy(xpath=loginButtonXpath)
	private WebElement btnLogin;
	@FindBy(xpath=registerButtonXpath)
	private WebElement btnRegister;
	
	public LoginPage login() {
		btnLogin.click();
		LoginPage loginPage = PageFactory.initElements(this.driver, LoginPage.class);
		return loginPage;
	}
	
	public RegisterPage register() {
		btnRegister.click();
		RegisterPage registerPage = PageFactory.initElements(this.driver, RegisterPage.class);
		return registerPage;
	}
	
}
