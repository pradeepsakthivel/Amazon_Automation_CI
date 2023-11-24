package com.amazon.user.regression;

import java.util.Collections;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.amazon.assertions.AmazonLandingPageAssertion;
import com.amazon.base.ReadDataFromXLS;
import com.amazon.base.TestUtilities;
import com.aventstack.extentreports.Status;

public class AmazonLoginTest extends TestUtilities {

	String loginUsername, loginPassword, userProfileName,testname;

	String testStatus, message;

	@Factory(dataProvider = "readCSVFile1", dataProviderClass = ReadDataFromXLS.class)
	public AmazonLoginTest(Map<String, String> testdataMap) {
		super(testdataMap.get("browser"));

		System.out.println(" *********************** Test Class Constructor **********************");

		this.loginUsername = testdataMap.get("loginEmail");
		this.loginPassword = fetchAwsSecret(loginUsername);

		this.userProfileName = testdataMap.get("userProfileName");
		this.testname = testdataMap.get("testscenarioName");
		testcaseName = testname;

		testdataMap.values().removeAll(Collections.singleton(null));
		testdataMap.entrySet().removeIf(ent -> ent.getValue().isEmpty());

	}

	/**
	 * Login into the application using internal users
	 */
	@Test(priority = 1, enabled = true)
	public void loginUser() {
		test = extent.createTest("Login to Amazon Website");
		test.assignCategory("Amazon Login Test");

		// login into the application
		
		try {
			loginAmazon(loginUsername, loginPassword, false);// false for UI related test class
			AmazonLandingPageAssertion asserpg = new AmazonLandingPageAssertion(driver, log, test);
			asserpg.userTextAssertion(userProfileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 2, enabled = true, dependsOnMethods = { "loginUser" })
	public void logoutUser() {
		test = extent.createTest("Logout from Amazon Website");
		test.assignCategory("Amazon Login Test");
		try {
			logoutFromAmazon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void endReport() {
		extent.flush();
	}

}
