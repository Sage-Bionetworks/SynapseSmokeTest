package org.sagebionetworks.smokeTest;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
		String baseUrl = "http://synapse.sagebase.org";
		StringBuffer verificationErrors = new StringBuffer();
		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface, 
		// not the implementation.
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// And now use this to visit Synapse
		driver.get(baseUrl);

		System.out.println("Page title is: " + driver.getTitle());

		//Close the browser
		driver.quit();
	}
	
}
