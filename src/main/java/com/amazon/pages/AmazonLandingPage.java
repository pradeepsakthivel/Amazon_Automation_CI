 package com.amazon.pages;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.interactions.Actions;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
//Make sure to import the following packages in your code
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;


//
//

 
public class AmazonLandingPage extends BasePage {


	private By acc_link = By.xpath("//span[contains(text(),'Account & Lists')]/span[@class='nav-icon nav-arrow']");
	private By userName = By.xpath("//input[@id='ap_email']");
	private By continueBtn = By.xpath("//*[@id='continue']");
	private By signIn = By.xpath("//*[@id='auth-signin-button']");
	private By password = By.xpath("//*[@id='ap_password']");
	private By successLoginMsg = By.xpath("//*[@id='nav-link-accountList-nav-line-1']");
	private By signout = By.xpath("//a/span[contains(text(),'Sign Out')]");
	public AmazonLandingPage(WebDriver driver, Logger log, ExtentTest test) {
		super(driver, log, test);
	}
	
	public void openPageUrl(String pageurl) {		
		openUrl(pageurl);
		System.out.println("Application opened successfully");	
	}

	public void loginAZUser(String loginUsername, String loginPassword) {
		System.out.println("Loggin with creds");

		waitForVisiblityOf(acc_link, 10);
		clickClickable(acc_link);
		enterUserName(loginUsername);
		enterPassword(loginPassword);
	}

	public void enterUserName(String loginUsername) {
		waitForVisiblityOf(userName, 10);
		find(userName);
		type(userName, loginUsername);
		 waitForVisiblityOf(continueBtn);
		 clickClickable(continueBtn);
	}

	public void enterPassword(String loginPassword) {
		waitForVisiblityOf(password);
		waitForPresenceOf(password, 10);
		find(password);
		type(password, loginPassword);
		waitForClickable(signIn);
		clickClickable(signIn);
	}
	
	
 
	
	/*** Enter Property Detail in Search Box ***/
	
	public String userText() {
		  waitForInvisibleOf(signIn, 10);
		 waitForVisiblityOf(successLoginMsg, 10); return
		 find(successLoginMsg).getText(); }
	
	public void signout() {
		System.out.println("Logging out ");

		mouseHover (acc_link);
		waitForVisiblityOf(signout, 10);
		clickClickable(signout);
	}
	
	
	public void mouseHover(By locator) {
		Actions actions = new Actions(driver);
    	WebElement menuOption = driver.findElement(acc_link);
    	actions.moveToElement(menuOption).perform();
		
	}
}
