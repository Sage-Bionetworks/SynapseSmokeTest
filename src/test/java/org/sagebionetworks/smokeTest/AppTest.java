package org.sagebionetworks.smokeTest;

import java.util.concurrent.TimeUnit;
import java.lang.String;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.*;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Store;
import javax.mail.Folder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AppTest {
	private static WebDriver driver;
	private static String baseUrl;
	private static String existingSynapseUserEmailName;
	private static String existingSynapseUserPassword;
	private static String existingSynapseUserFirstName;
	private static String existingSynapseUserLastName;
	private static String existingOpenIdUserEmailName;
	private static String existingOpenIdUserPassword;
	private static String existingOpenIdUserFirstName;
	private static String existingOpenIdUserLastName;
	private static String newUserEmailName;
	private static String newUserEmailPassword;
	private static String newUserSynapsePassword;
	private static String newUserFirstName;
	private static String newUserLastName;
	private static String crowdAdminUser;
	private static String crowdAdminPassword;
	private static String crowdConsoleUrl;
	
	private static final String synapseLoginInputXpath = "//div/div/input[@type='text']";
	private static final String synapsePasswordInputXpath = "//div/div/input[@type='password']";
	private static final String newUserEmailIputXpath = "x-auto-23-input";
	private static final String newUserFirstNameInputXpath = "x-auto-24-input";
	private static final String newUserLastNameInputXpath = "x-auto-25-input";
	
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		loadProperties("");
		driver = new FirefoxDriver();
		baseUrl = "https://synapse-staging.sagebase.org/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		driver.quit();
	}
	
	@Before
	public void setUp() throws Exception {
		driver.get(baseUrl);
		Thread.sleep(5000);
	}
	
	@Ignore
	@Test
	public void testAnonBrowse() throws Exception {
		WebElement el;
		String url;
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		el = driver.findElement(By.xpath("//div/h5/a[@href='#Synapse:syn150935']"));
		el.click();
		url = driver.getCurrentUrl();
		assertEquals(url, "https://synapse.sagebase.org/#Synapse:syn150935");
	}

	@Ignore
	@Test
	public void testSynapseLoginFailure() throws Exception {
		WebElement el;
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		el = driver.findElement(By.xpath("//button[contains(., 'Login')]"));
		el.click();
		el = driver.findElement(By.xpath("//h2[contains(., 'Login to Synapse')]"));
		assertEquals("Login to Synapse", el.getText().trim());
		el = driver.findElement(By.xpath(synapseLoginInputXpath));
		el.clear();
		el.sendKeys("abcde@xxx.org");
		el = driver.findElement(By.xpath(synapsePasswordInputXpath));
		el.clear();
		el.sendKeys("abcde");
		// TODO: Simplify xpath
		el = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div[3]/div[2]/div[3]/div/div/div/table/tbody/tr[2]/td/div/div[2]/div/div/div/div/form/fieldset/div/table/tbody/tr[2]/td[2]/em/button"));
		el.click();
		// TODO: Why does Firepath returns this xpath?
		el = driver.findElement(By.xpath("//*[@id='x-auto-33']"));
		assertTrue("Invalid username or password.".equals(el.getText().trim()));
	}
	
	@Test
	public void testSynapseLoginSuccess() throws Exception {
		WebElement el;
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		el = driver.findElement(By.xpath("//button[contains(., 'Login')]"));
		el.click();
		el = driver.findElement(By.xpath("//h2[contains(., 'Login to Synapse')]"));
		assertEquals("Login to Synapse", el.getText().trim());
		el = driver.findElement(By.xpath(synapseLoginInputXpath));
		el.clear();
		el.sendKeys(existingSynapseUserEmailName);
		el = driver.findElement(By.xpath(synapsePasswordInputXpath));
		el.clear();
		el.sendKeys(existingSynapseUserPassword);
		el.click();
		el = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div[3]/div[2]/div[3]/div/div/div/table/tbody/tr[2]/td/div/div[2]/div/div/div/div/form/fieldset/div/table/tbody/tr[2]/td[2]/em/button"));
		el.click();
		Thread.sleep(1000);
		el = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div[3]/div/div/div/table/tbody/tr/td/table/tbody/tr[2]/td[2]/em/button"));
		String txt = existingSynapseUserFirstName + " " + existingSynapseUserLastName;
		assertEquals(txt, el.getText().trim());
		el.click();
		el = driver.findElement(By.xpath("//div/div[2]/a[contains(., 'Logout')]"));
		el.click();
		el = driver.findElement(By.xpath("//div[3]/div/div[2]/div/div[2]"));
		txt = "You have been logged out of Synapse.";
		assertEquals(txt, el.getText().trim());
	}
	
	@Ignore
	@Test
	public void testOpenIdLoginNotLoggedIn() throws Exception {
		WebElement el;
		// TODO: Add check that we are not logged into Google
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		el = driver.findElement(By.xpath("//button[contains(., 'Login')]"));
		el.click();
		el = driver.findElement(By.xpath("//h2[contains(., 'Login to Synapse')]"));
		assertEquals("Login to Synapse", el.getText().trim());
		el = driver.findElement(By.xpath("//form[@id='gapp-openid-form']/button[@id='login-via-gapp-google'][@type='submit']"));
		assertEquals("Login with a Google Account", el.getText().trim());
		el.click();
		// TODO: Move sign in at Google to own fct
		// Should be taken to Google login page
		assertEquals("Google Accounts", driver.getTitle().trim());
		// Login
		el = driver.findElement(By.id("Email"));
		el.sendKeys(existingOpenIdUserEmailName);
		el = driver.findElement(By.id("Passwd"));
		el.sendKeys(existingOpenIdUserPassword);
		el = driver.findElement(By.id("signIn"));
		el.click();
		// TODO: Better way to handle redirections
		Thread.sleep(2000);
		// Should come back to main page
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		el = driver.findElement(By.cssSelector("h5"));
		String msg = "Welcome " + existingOpenIdUserFirstName + " " + existingOpenIdUserLastName;
		assertEquals(msg, el.getText().trim());
		el = driver.findElement(By.linkText("LOGOUT"));
		el.click();
	}
	
	@Ignore
	@Test
	public void testOpenIdLoginLoggedIn() throws Exception {
		WebElement el;
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		// TODO: Add check that we are logged in
		el = driver.findElement(By.xpath("//button[contains(., 'Login')]"));
		el.click();
		el = driver.findElement(By.xpath("//h2[contains(., 'Login to Synapse')]"));
		assertEquals("Login to Synapse", el.getText().trim());
		el = driver.findElement(By.xpath("//form[@id='gapp-openid-form']/button[@id='login-via-gapp-google'][@type='submit']"));
		assertEquals("Login with a Google Account", el.getText().trim());
		el.click();
		// TODO: Better way to handle redirections
		Thread.sleep(2000);
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		el = driver.findElement(By.cssSelector("h5"));
		String msg = "Welcome " + existingOpenIdUserFirstName + " " + existingOpenIdUserLastName;
		assertEquals(msg, el.getText().trim());
		el = driver.findElement(By.linkText("LOGOUT"));
		el.click();
	}
	
	@Ignore
	@Test
	public void testRegisterUser() throws Exception {
		WebElement el;
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		Thread.sleep(1000L);
		// TODO: Delete test account if exists
		//	Register in UI
		el = driver.findElement(By.xpath("//button[contains(., 'Register')]"));
		el.click();
		el = driver.findElement(By.cssSelector("h2"));
		assertEquals("Register With Synapse", el.getText().trim());
		el = driver.findElement(By.id(newUserEmailIputXpath));
		el.clear();
		el.sendKeys(newUserEmailName);
		el = driver.findElement(By.id(newUserFirstNameInputXpath));
		el.clear();
		el.sendKeys(newUserFirstName);
		el = driver.findElement(By.id(newUserLastNameInputXpath));
		el.clear();
		el.sendKeys(newUserLastName);
		List<WebElement> l = driver.findElements(By.cssSelector("button.x-btn-text"));
		for (WebElement e : l) {
			if ("Register".equals(e.getText().trim())) {
				e.click();
				break;
			}
		}
		Thread.sleep(2000);
		el = driver.findElement(By.cssSelector("h2"));
		assertEquals("Register With Synapse", el.getText().trim());
		// Get link from mail msg
		String msg = getRegistrationMail(newUserEmailName, newUserEmailPassword);
		Pattern p = Pattern.compile("https://.+");
		Matcher m = p.matcher(msg);
		String url = null;
		int nMatches = 0;
		while (m.find()) {
			nMatches++;
			url = m.group();
		}
		assertEquals(1, nMatches);
		// Set new user password
		driver.get(url);
		Thread.sleep(1000);
		el = driver.findElement(By.cssSelector("h2"));
		assertEquals("Password Reset", el.getText().trim());
		// Enter and confirm password
		el = driver.findElement(By.id("x-auto-12-input"));
		el.clear();
		el.sendKeys(newUserSynapsePassword);
		el = driver.findElement(By.id("x-auto-13-input"));
		el.clear();
		el.sendKeys(newUserSynapsePassword);
		l = driver.findElements(By.cssSelector("button.x-btn-text"));
		for (WebElement e : l) {
			if ("Submit".equals(e.getText().trim())) {
				e.click();
				break;
			}
		}			
		// Back to login page
		el = driver.findElement(By.cssSelector("h5"));
		msg = "Welcome " + newUserFirstName + " " + newUserLastName;
		assertEquals(msg, el.getText().trim());

		// Logout
		el = driver.findElement(By.linkText("LOGOUT"));
		el.click();
		// Cleanup registered user
		deleteRegisteredUserInCrowd();
	}
	
	@Ignore
	@Test
	public void uploadDownloadFile() throws Exception {
		WebElement el = null;
		
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		Thread.sleep(1000);
		
		login();
		
		// Start project
		List<WebElement> l = driver.findElements(By.className("mega-button"));
		for (WebElement e : l) {
			if ("START A PROJECT".equals(e.getText().trim())) {
				el = e;
				break;
			}
		}
		el.click();
		el = driver.findElement(By.tagName("input"));
		assertEquals("homesearchbox addProjectBox", el.getAttribute("class").trim());

	}
	
	@Ignore
	@Test
	public void testGetEmail() throws Exception {
		String url;
		String msg = getRegistrationMail(newUserEmailName, newUserEmailPassword);
		Pattern p = Pattern.compile("https://.+");
		Matcher m = p.matcher(msg);
		int nMatches = 0;
		while (m.find()) {
			nMatches++;
			if (nMatches == 1) {
				url = m.group();
			} else {
				// Should never get here
			}
		}
	}
	
	@Ignore
	@Test
	public void testloadProperties() throws Exception {
		loadProperties("");
	}
	
	@Ignore
	@Test
	public void testDeleteRegisteredUser() throws Exception {
		deleteRegisteredUserInCrowd();
	}
	
	private static String getRegistrationMail(String user, String password) throws Exception {
		Session session = null;
		Store store = null;
		Message message;
		String msgContent;
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.imaps.host", "imap.gmail.com");
		props.setProperty("mail.imaps.port", "993");
		props.setProperty("mail.imaps.connectiontimeout", "10000");
		props.setProperty("mail.imaps.timeout", "10000");
		try {
			session = Session.getDefaultInstance(props, null);
			store = session.getStore("imaps");
			store.connect("imap.gmail.com", user, password);
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			int nMsgs = inbox.getMessageCount();
			// TODO: Add logic to look for msg if not last one
			message = inbox.getMessage(nMsgs);
			assertEquals("reset Synapse password", message.getSubject().trim());
			msgContent = (String)message.getContent();
		} finally {
			if (store != null)
				store.close();
		}
		return msgContent;
	}
	
	private static void loadProperties(String path) throws Exception {
		Properties props = new Properties();
		InputStream is = AppTest.class.getClassLoader().getResourceAsStream("smoke.properties");
		try {
			props.load(is);
			existingSynapseUserEmailName = props.getProperty("existingSynapseUserEmailName");
			existingSynapseUserPassword = props.getProperty("existingSynapseUserPassword");
			existingSynapseUserFirstName = props.getProperty("existingSynapseUserFirstName");
			existingSynapseUserLastName = props.getProperty("existingSynapseUserLastName");
			existingOpenIdUserEmailName = props.getProperty("existingOpenIdUserEmailName");
			existingOpenIdUserPassword = props.getProperty("existingOpenIdUserPassword");
			existingOpenIdUserFirstName = props.getProperty("existingOpenIdUserFirstName");
			existingOpenIdUserLastName = props.getProperty("existingOpenIdUserLastName");
			newUserEmailName = props.getProperty("newUserEmailName");
			newUserEmailPassword = props.getProperty("newUserEmailPassword");
			newUserFirstName = props.getProperty("newUserFirstName");
			newUserLastName = props.getProperty("newUserLastName");
			crowdAdminUser = props.getProperty("crowdAdminUser");
			crowdAdminPassword = props.getProperty("crowdAdminPassword");
			crowdConsoleUrl = props.getProperty("crowdConsoleUrl");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void login() throws Exception {
		WebElement el;
		
		el = driver.findElement(By.linkText("LOGIN"));
		el.click();
		el = driver.findElement(By.cssSelector("h2"));
		assertEquals("Login to Synapse", el.getText().trim());
		el = driver.findElement(By.id("x-auto-24-input"));
		el.clear();
		el.sendKeys(existingSynapseUserEmailName);
		el = driver.findElement(By.id("x-auto-25-input"));
		el.clear();
		el.sendKeys(existingSynapseUserPassword);
		List<WebElement> l = driver.findElements(By.cssSelector("button.x-btn-text"));
		for (WebElement e : l) {
			if ("Login".equals(e.getText())) {
				el = e;
				break;
			}
		}
		assertEquals("Login", el.getText().trim());
		el.click();
		Thread.sleep(1000);
		el = driver.findElement(By.cssSelector("h5"));
		String msg = "Welcome " + existingSynapseUserFirstName + " " + existingSynapseUserLastName;
		assertEquals(msg, el.getText().trim());
	
	}
	
	private static void deleteRegisteredUserInCrowd() throws Exception {
		WebElement el = null;
		// Login as crowd administrator
		driver.get(crowdConsoleUrl);
		assertEquals("Atlassian Crowd\u00A0-\u00A0Login", driver.getTitle().trim());
		el = driver.findElement(By.id("j_username"));
		el.clear();
		el.sendKeys(crowdAdminUser);
		el = driver.findElement(By.id("j_password"));
		el.clear();
		el.sendKeys(crowdAdminPassword);
		el = driver.findElement(By.cssSelector("input.button"));
		el.click();
		// Find user to delete
		assertEquals("Atlassian Crowd\u00A0-\u00A0Administration Console", driver.getTitle().trim());
		el = driver.findElement(By.id("topnavBrowseUsers"));
		el.click();
		el = driver.findElement(By.name("search"));
		el.clear();
		el.sendKeys(newUserEmailName);
		el = driver.findElement(By.name("submit-search"));
		el.click();
		String lt = newUserFirstName + " " + newUserLastName;
		el = driver.findElement(By.linkText(lt));
		el.click();
		// Delete user
		assertEquals("Atlassian Crowd\u00A0-\u00A0View User", driver.getTitle().trim());
		el = driver.findElement(By.id("remove-principal"));
		el.click();
		assertEquals("Atlassian Crowd\u00A0-\u00A0Remove User", driver.getTitle().trim());
		List<WebElement> l = driver.findElements(By.cssSelector("input.button"));
		for (WebElement e : l) {
			System.out.println(e.getAttribute("value"));
			if ("Continue »".equals(e.getAttribute("value"))) {
				el = e;
				break;
			}
		}
		el.click();
		// Logout
		l = driver.findElements(By.tagName("a"));
		for (WebElement e : l) {
			if (e.getText().equals("Log Out")) {
				el = e;
				break;
			}
		}
		assertEquals("Log Out", el.getText().trim());
		el.click();
	}
}
