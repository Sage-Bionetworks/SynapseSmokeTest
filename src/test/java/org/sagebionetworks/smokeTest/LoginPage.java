package org.sagebionetworks.smokeTest;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {
	
	private static final String synapseLoginInputXpath = "//div/div/input[@type='text']";
	private static final String synapsePasswordInputXpath = "//div/div/input[@type='password']";
	private static final String synapseLoginButtonXpath = "/html/body/div/div[2]/div/div/div[3]/div[2]/div[3]/div/div/div/table/tbody/tr[2]/td/div/div[2]/div/div/div/div/form/fieldset/div/table/tbody/tr[2]/td[2]/em/button";
	private static final String openIdLoginButtonXpath = "//form[@id='gapp-openid-form']/button[@id='login-via-gapp-google'][@type='submit']";
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath=synapseLoginInputXpath)
	private WebElement inputSynapseLogin;
	
	@FindBy(xpath=synapsePasswordInputXpath)
	private WebElement inputSynapsePassword;
	
	@FindBy(xpath=synapseLoginButtonXpath)
	private WebElement btnSynapseLogin;
	
	@FindBy(xpath=openIdLoginButtonXpath)
	private WebElement btnOpenIdLogin;
	
	public void check() {
		WebElement el = driver.findElement(By.xpath("//h2[contains(., 'Login to Synapse')]"));
		assertEquals("Login to Synapse", el.getText().trim());
	}
	
	public Page synapseLogin(String user, String password) {
		WebElement el;
		el = driver.findElement(By.xpath(synapseLoginInputXpath));
		el.clear();
		el.sendKeys(user);
		el = driver.findElement(By.xpath(synapsePasswordInputXpath));
		el.clear();
		el.sendKeys(password);
		el = driver.findElement(By.xpath(synapseLoginButtonXpath));
		el.click();

		return new Page(this.driver);
	}
	
	public Page openIdLogin() {
		return new Page(this.driver);
	}
	
}