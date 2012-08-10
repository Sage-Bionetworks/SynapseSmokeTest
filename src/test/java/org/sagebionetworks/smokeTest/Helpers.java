package org.sagebionetworks.smokeTest;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Helpers {

	public static String getRegistrationMail(String user, String password) throws Exception {
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
			msgContent = (String)message.getContent();
		} finally {
			if (store != null)
				store.close();
		}
		return msgContent;
	}
	
	public static void deleteRegisteredUserInCrowd(
			WebDriver driver,
			String crowdConsoleUrl,
			String crowdAdminUser,
			String crowdAdminPassword,
			String userToDeleteEmailName,
			String userToDeleteFirstName,
			String userToDeleteLastName) throws Exception {
		
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
		el.sendKeys(userToDeleteEmailName);
		el = driver.findElement(By.name("submit-search"));
		el.click();
		String lt = userToDeleteFirstName + " " + userToDeleteLastName;
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
