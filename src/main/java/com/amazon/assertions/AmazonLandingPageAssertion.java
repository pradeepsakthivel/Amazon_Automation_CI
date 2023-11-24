package com.amazon.assertions;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.amazon.pages.AmazonLandingPage;
import com.amazon.pages.BasePage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class AmazonLandingPageAssertion extends BasePage {

	public AmazonLandingPageAssertion(WebDriver driver, Logger log, ExtentTest test) {
		super(driver, log, test);
	}

	AmazonLandingPage amzlog = new AmazonLandingPage(driver, log, test);

	/** Validating the url */
	public void userTextAssertion(String name) {
		String expMessage = "Hello, " + name;

		if (amzlog.userText().contains(expMessage)) {
			log.info("User Assertion - PASS");
			test.log(Status.PASS, "Navigated to Home page, Url Assertion - PASS");
			
		} else {
			log.info("User Assertion - FAIL");
			test.log(Status.FAIL, "Navigated to Home Page, Url Assertion - FAIL");
			Assert.assertEquals(driver.getCurrentUrl(), expMessage);
		}
	}

}
