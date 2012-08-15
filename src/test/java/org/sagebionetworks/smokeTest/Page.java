package org.sagebionetworks.smokeTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page {
	private static final String loginButtonXpath = "//table[@id='"+UiConstants.ID_BTN_LOGIN+"']/tbody/tr[2]/td[2]/em/button";
	private static final String registerButtonXpath = "//table[@id='"+UiConstants.ID_BTN_REGISTER+"']/tbody/tr[2]/td[2]/em/button";
	private static final String userButtonXpath = "//table[@id='"+UiConstants.ID_BTN_USER+"']/tbody/tr[2]/td[2]/em/button";
	private static final String menuItemLogoutXpath = "//a[@id='" + UiConstants.ID_MNU_USER_LOGOUT + "']";
	private static final String imgSynapseTitleXpath = "//a[href='#']";

	@FindBy(xpath=loginButtonXpath)
	private WebElement btnLogin;
	@FindBy(xpath=registerButtonXpath)
	private WebElement btnRegister;

	@FindBy(xpath=userButtonXpath)
	WebElement btnUser;
	@FindBy(xpath=menuItemLogoutXpath)
	WebElement mnuItemLogout;
	
	@FindBy(xpath=imgSynapseTitleXpath)
	WebElement imgSynapseTitle;
	
	
	WebDriver driver;
	private String baseUrl;
	
	public Page(WebDriver driver) {
		this.driver = driver;
	}
	public void setBaseUrl(String url) {
		baseUrl = url;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public LoginPage login() {
		btnLogin.click();
		LoginPage loginPage = PageFactory.initElements(this.driver, LoginPage.class);
		return loginPage;
	}
	
	public void logout() {
		WebElement e = driver.findElement(By.xpath(userButtonXpath));
		btnUser.click();
		mnuItemLogout.click();
//		Page p = PageFactory.initElements(this.driver, Page.class);
		return;
	}
	public RegisterPage register() {
		btnRegister.click();
		RegisterPage registerPage = PageFactory.initElements(this.driver, RegisterPage.class);
		return registerPage;
	}
	
	public UserHomePage goHome() {
		imgSynapseTitle.click();
		UserHomePage userHomePage = PageFactory.initElements(this.driver, UserHomePage.class);
		return userHomePage;
	}
	
	
	public boolean loggedIn() {
		boolean v = true;
		WebElement e;
		try {
			e = this.driver.findElement(By.xpath(userButtonXpath));
		} catch (NoSuchElementException ex) {
			v = false;
		} finally {
			return v;
		}
	}
	
		
	public String getDriverUrl() {
		return (this.driver.getCurrentUrl());
	}
	
	public String getTitle() {
		return (this.driver.getTitle());
	}

}
