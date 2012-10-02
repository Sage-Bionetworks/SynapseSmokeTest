package org.sagebionetworks.smokeTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page {
	private static final String loginButtonXpath = "html/body/div[1]/div[2]/div/div/div[3]/div[1]/div/div/table/tbody/tr/td[3]/a";
	private static final String registerButtonXpath = "/html/body/div/div[2]/div/div/div[3]/div/div/div/table/tbody/tr/td[2]/a";
	private static final String supportButtonXpath = "/html/body/div/div[2]/div/div/div[3]/div/div/div/table/tbody/tr/td/a";
	private static final String userButtonXpath = "//*/a[@class='gwt-Anchor headerUsernameLink']";
	private static final String logoutButtonXpath = "//*[@id='sbn-tooltip-1']";
	private static final String settingsButtonXpath = "//*[@id='sbn-tooltip-0']";
	private static final String imgSynapseTitleXpath = "//a[href='#']";

	@FindBy(xpath=loginButtonXpath)
	private WebElement btnLogin;
	@FindBy(xpath=registerButtonXpath)
	private WebElement btnRegister;

	@FindBy(xpath=userButtonXpath)
	WebElement btnUser;
	@FindBy(xpath=logoutButtonXpath)
	WebElement btnLogout;
	
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
		btnLogout.click();
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
		boolean v = false;
		WebElement e;
		try {
			e = this.driver.findElement(By.xpath(loginButtonXpath));
		} catch (NoSuchElementException ex) {
			v = true;
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
