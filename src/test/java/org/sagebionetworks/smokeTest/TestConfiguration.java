package org.sagebionetworks.smokeTest;

import java.io.IOException;
import java.util.Properties;

public class TestConfiguration {
	private Properties properties;
	
	public TestConfiguration(Properties testConfigProps) throws IOException {
		properties = testConfigProps;
	}
	
	public String getExistingSynapseUserEmailName() {
		return properties.getProperty("existingSynapseUserEmailName");
	}	
	public String getExistingSynapseUserPassword() {
		return properties.getProperty("existingSynapseUserPassword");
	}
	public String getExistingSynapseUserFirstName() {
		return properties.getProperty("existingSynapseUserFirstName");
	}
	public String getExistingSynapseUserLastName() {
		return properties.getProperty("existingSynapseUserLastName");
	}
	public String getExistingOpenIdUserEmailName() {
		return properties.getProperty("existingOpenIdUserEmailName");
	}
	public String getExistingOpenIdUserPassword() {
		return properties.getProperty("existingOpenIdUserPassword");
	}
	public String getExistingOpenIdUserFirstName() {
		return properties.getProperty("existingOpenIdUserFirstName");
	}
	public String getExistingOpenIdUserLastName() {
		return properties.getProperty("existingOpenIdUserLastName");
	}
	public String getNewUserEmailName() {
		return properties.getProperty("newUserEmailName");
	}
	public String getNewUserEmailPassword() {
		return properties.getProperty("newUserEmailPassword");
	}
	public String getNewUserSynapsePassword() {
		return properties.getProperty("newUserSynapsePassword");
	}
	public String getNewUserFirstName() {
		return properties.getProperty("newUserFirstName");
	}
	public String getNewUserLastName() {
		return properties.getProperty("newUserLastName");
	}
	public String getCrowdAdminUser() {
		return properties.getProperty("crowdAdminUser");
	}
	public String getCrowdAdminPassword() {
		return properties.getProperty("crowdAdminPassword");
	}
	public String getCrowdConsoleUrl() {
		return properties.getProperty("crowdConsoleUrl");
	}
	public String getSynapseHomepageUrl() {
		return properties.getProperty("synapseHomepageUrl");
	}
}
