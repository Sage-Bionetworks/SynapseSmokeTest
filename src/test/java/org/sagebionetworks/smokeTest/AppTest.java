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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AppTest {
	// TODO: Implement DriverFactory to handle different types and singleton
	private static WebDriver driver;
	private static HomePage homePage;
	private static TestConfiguration testConfiguration;
	
	private static String baseUrl;
	
	private static final String synapseLoginInputXpath = "//div/div/input[@type='text']";
	private static final String synapsePasswordInputXpath = "//div/div/input[@type='password']";
	private static final String newUserEmailIputXpath = "x-auto-23-input";
	private static final String newUserFirstNameInputXpath = "x-auto-24-input";
	private static final String newUserLastNameInputXpath = "x-auto-25-input";
	
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		loadProperties("");
		driver = new FirefoxDriver();
		baseUrl = testConfiguration.getSynapseHomepageUrl();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		driver.quit();
	}
	
	@Before
	public void setUp() throws Exception {
		driver.get(baseUrl);
		Thread.sleep(5000);
		assertEquals(baseUrl, driver.getCurrentUrl());
		homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.setBaseUrl(baseUrl);
	}
	
	@Ignore
	@Test
	public void testAnonBrowse() throws Exception {
		WebElement el;
		String url;
		
		assertFalse(AppTest.homePage.loggedIn());
		EntityPage scrPage = AppTest.homePage.gotoSCR();
		assertEquals(scrPage.getDriverUrl(), baseUrl+"#Synapse:syn150935");
		
	}
	
	@Ignore
	@Test
	public void testAnonSearch() throws Exception {
		assertFalse(AppTest.homePage.loggedIn());
		SearchResultsPage p = AppTest.homePage.doSearch("cancer");
		assertEquals(p.getDriverUrl(), baseUrl + "#Search:cancer");
	}

	@Ignore
	@Test
	public void testSynapseLoginFailure() throws Exception {
		LoginPage loginPage = AppTest.homePage.login();
		assertEquals(loginPage.getDriverUrl(), baseUrl + UiConstants.STR_LOGIN_PAGE);
		UserHomePage p = loginPage.synapseLogin("abcde@xxx.org", "abcde");
		assertNull(p);
		// TODO: check for error message on login page >> change API
	}
	
	@Ignore
	@Test
	public void testSynapseLoginSuccess() throws Exception {
		LoginPage loginPage = AppTest.homePage.login();
		assertEquals(loginPage.getDriverUrl(), baseUrl + UiConstants.STR_LOGIN_PAGE);
		UserHomePage p = loginPage.synapseLogin(testConfiguration.getExistingSynapseUserEmailName(), testConfiguration.getExistingSynapseUserPassword());
		assertNotNull(p);
		assertTrue(p.loggedIn());
		
		// Technically, should get a LogoutPage here...
		p.logout();
		assertFalse(p.loggedIn());
		assertEquals(p.getDriverUrl(), baseUrl+UiConstants.STR_LOGOUT_PAGE);
	}
	
	@Ignore
	@Test
	public void testOpenIdLoginNotLoggedIn() throws Exception {
		// TODO: Logout of Google if logged in
		LoginPage loginPage = AppTest.homePage.login();
		assertEquals(loginPage.getDriverUrl(), baseUrl + UiConstants.STR_LOGIN_PAGE);
		UserHomePage p = loginPage.openIdLogin(testConfiguration.getExistingSynapseUserEmailName(), testConfiguration.getExistingSynapseUserPassword());
		assertNotNull(p);
		assertTrue(p.loggedIn());
		
		p.logout();
		assertFalse(p.loggedIn());
		assertEquals(p.getDriverUrl(), baseUrl+UiConstants.STR_LOGOUT_PAGE);

	}
	
	@Ignore
	@Test
	public void testOpenIdLoginLoggedIn() throws Exception {
		LoginPage loginPage = AppTest.homePage.login();
		assertEquals(loginPage.getDriverUrl(), baseUrl + UiConstants.STR_LOGIN_PAGE);
		UserHomePage p = loginPage.openIdLogin("", "");
		assertNotNull(p);
		assertTrue(p.loggedIn());
		
		p.logout();
		assertFalse(p.loggedIn());
	}
	
	
	@Test
	public void testRegisterUser() throws Exception {
		WebElement el;
		assertEquals("Sage Synapse : Contribute to the Cure", driver.getTitle());
		Thread.sleep(1000L);
		// TODO: Delete test account if exists
		//	Register in UI
		RegisterPage registerPage = AppTest.homePage.register();
		registerPage = registerPage.register(testConfiguration.getNewUserEmailName(), testConfiguration.getNewUserFirstName(), testConfiguration.getNewUserLastName());
		assertTrue(registerPage.isUserCreated());
		
		// TODO: Put in utility function
		// Get link from mail msg
		Thread.sleep(10000);	// Give some time for mail to arrive
		String msg = Helpers.getRegistrationMail(testConfiguration.getNewUserEmailName(), testConfiguration.getNewUserEmailPassword());
		Pattern p = Pattern.compile("https://.+");
		Matcher m = p.matcher(msg);
		String url = null;
		int nMatches = 0;
		while (m.find()) {
			nMatches++;
			url = m.group();
		}
		assertEquals(1, nMatches);
		
		// This should be equivalent to Reset Password method
		// Set new user password
		driver.get(url);
		Thread.sleep(1000);
		PasswordResetPage passwordResetPage = PageFactory.initElements(driver, PasswordResetPage.class);
		LoginPage loginPage = passwordResetPage.resetPassword(testConfiguration.getNewUserSynapsePassword());
		UserHomePage userHomePage = loginPage.synapseLoginWithTOS(testConfiguration.getNewUserEmailName(), testConfiguration.getNewUserSynapsePassword());
		// TODO: Check that we're really on user home page
		assertTrue(userHomePage.loggedIn());
		
		// Logout
		userHomePage.logout();
		assertFalse(userHomePage.loggedIn());
		
		// Cleanup registered user
		Helpers.deleteRegisteredUserInCrowd(
				driver,
				testConfiguration.getCrowdConsoleUrl(),
				testConfiguration.getCrowdAdminUser(),
				testConfiguration.getCrowdAdminPassword(),
				testConfiguration.getNewUserEmailName(),
				testConfiguration.getNewUserFirstName(),
				testConfiguration.getNewUserLastName());
	}
	
	@Ignore
	@Test
	public void testGetEmail() throws Exception {
		String url;
		String msg = Helpers.getRegistrationMail(testConfiguration.getNewUserEmailName(), testConfiguration.getNewUserEmailPassword());
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
		assertNotNull(testConfiguration.getNewUserEmailName());
	}
	
	@Ignore
	@Test
	public void testDeleteRegisteredUser() throws Exception {
		Helpers.deleteRegisteredUserInCrowd(
				driver,
				testConfiguration.getCrowdConsoleUrl(),
				testConfiguration.getCrowdAdminUser(),
				testConfiguration.getCrowdAdminPassword(),
				testConfiguration.getNewUserEmailName(),
				testConfiguration.getNewUserFirstName(),
				testConfiguration.getNewUserLastName());
	}
	
	private static void loadProperties(String path) throws Exception {
		Properties props = new Properties();
		InputStream is = AppTest.class.getClassLoader().getResourceAsStream("smoke.properties");
		try {
			props.load(is);
			testConfiguration = new TestConfiguration(props);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testChromeDriver() {
//	  // Optional, if not specified, WebDriver will search your path for chromedriver.
//	  System.setProperty("webdriver.chrome.driver", "/usr/local/chromedriver");

	  WebDriver driver = new ChromeDriver();
	  driver.get("http://www.google.com/xhtml");
	  WebElement searchBox = driver.findElement(By.name("q"));
	  searchBox.sendKeys("ChromeDriver");
	  searchBox.submit();
	  driver.quit();
	}
	

}
