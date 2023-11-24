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


//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
//import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
//import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;	
//import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
//import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
//
//
//import software.amazon.awssdk.auth.*;

 
public class AmazonLandingPage extends BasePage {

//	private By dbc_logout = By.xpath("//*[@id='lbutton']");
//	private By user_header = By.xpath("//div/h1[contains(text(),'Users')]");
//
//	
//	private By page_loader = By.xpath("//*[contains(@class,'MuiCircularProgress-circleDisableShrink')]");
//	private By page_inline_loader = By.xpath("//*[contains(@class,'loadingIcon MuiCircularProgress')]");
//	//Breadcrumbs locator
//	private By breadcrumbs_first_element=By.xpath("//*[@class='MuiBreadcrumbs-li'][1]/span");
//	private By breadcrumbs_second_element=By.xpath("//*[@class='MuiBreadcrumbs-li'][2]/span");
//	//search related locator
//	private By search_icon_property=By.xpath("//div[@class='search-icon-btn']");	
//	private By search_textbox_properties = By.xpath("//input[contains(@placeholder,'Search by name/address/sitecode/account')]");
//	private By clearfilter = By.xpath("//div[@class='search-clear-icon']/img");
//	private By toggle = By.xpath("//input[contains(@class,'MuiSwitch-input')]");
//	
//	private By searchresult_location_container = By.xpath("//*[@class='location-content-container']");
//	private By searchresult_location_name_container = By.xpath("//div[@tabindex=1]//div[@class='location-name-container']");
//	private By searchresult_location_cardaddress_container = By.xpath("//div[@tabindex=1]//p[contains(@class,'location-card-address')]");
//	private By searchresult_location_cardaccountno_container = By.xpath("//div[@tabindex=1]//p[contains(@class,'location-card-acc-number')]");
//	private By searchresult_event_arrow_center = By.xpath("(//div[@class='event-arrow-center'])[1]");
//	
//	private By properties_record_count = By.xpath("//div[@class='records-count']");
//	private By menu_icon_fav = By.xpath("//div[@class='menuIconFavStar']");	
//	private By property_img = By.xpath("//div[@tabindex=1]//div[@class='circular-progress-container']/img");
//
//	private By defualt_property_name=By.xpath("//div[@class='propertyName']");
//	private By defualt_property_account_number=By.xpath("//div[@class='propertyAccnumber']");
//	private By property_element_locator=By.xpath("//*[@class='MuiGrid-root grid-width MuiGrid-item']");
//	private By property_name_element_locator=By.xpath("//*[@class='location-name-container']/p");

	//private By acc_link = By.xpath("//*[@id='nav-link-accountList']");
	private By acc_link = By.xpath("//span[contains(text(),'Account & Lists')]/span[@class='nav-icon nav-arrow']");
//	private By acc_link = By.xpath("//*[@class='nav-line-2']");
	//private By userName = By.xpath("//span[contains(text90,'Sign in')]");
	private By userName = By.xpath("//input[@id='ap_email']");
	//private By i_signIn = By.xpath("//*[@id='continue']");
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
	
	
 //	public void  fetchAwsSecret() {
//		System.out.println("^^^ fetch AWS secrets ^^^" );
//		 
////		 String secretName = "Test-credentials";
////		    Region region = Region.of("us-east-1");
//		    
////		    BasicAWSCredentials awsCreds= new BasicAWSCredentials("AKIAXK5ISNI5CKEAT2YC", "i060YzzQr49pAcxn7evUx7TlDuAYtboNxVMQ2nRW");
//		    String accessKey = "AKIAXK5ISNI5CKEAT2YC";
//		    String secretKey = "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";
//		    String secretArn = "arn:aws:secretsmanager:us-east-1:504475314746:secret:Test-credentials-LjDViq";
//
//		    
//
//
//		    // Create a Secrets Manager client
////		    SecretsManagerClient client = SecretsManagerClient.builder()
////		            .region(region)
////		            .build();
//
//		    
//		   
//
//		    try {
//		    	// Create a Secrets Manager client
//		    	AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
//		    	        .withRegion("us-east-1")
//		    	        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
//		    	        .build();
//		    	
//		    	String secretName = "Test-credentials"; // Replace with the name of your secret
//		    	 // Create a GetSecretValueRequest with the secret ARN
//		    	
//		      //  GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
//
//		    
////		    		GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
////		    	            .secretId(secretName)
////		    	            .build();
//		    		
//		    		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretArn);
//
//		    		
//			            String secret = client.getSecretValue(getSecretValueRequest).toString();
//			            System.out.println(secret);
//
//		        } 
//		    catch (Exception e) {
//	        	System.out.println(e.toString());
//		        }
//		    
//
//		    
//		    System.out.println("^^^  fetched secret: ^^^\n " ); 
//		  //  System.out.println("^^^ secret:^^^"+ secretValue ); 
//		}  
	
	/*** Enter Property Detail in Search Box ***/
	
	public String userText() {
		  waitForInvisibleOf(signIn, 10);
		 waitForVisiblityOf(successLoginMsg, 10); return
		 find(successLoginMsg).getText(); }
	
	public void signout() {
		System.out.println("Logging out ");

		mouseHover (acc_link);
		//clickClickable(acc_link);
		waitForVisiblityOf(signout, 10);
		clickClickable(signout);
	}
	
	
	public void mouseHover(By locator) {
		Actions actions = new Actions(driver);
        //Retrieve WebElement 'Music' to perform mouse hover 
    	WebElement menuOption = driver.findElement(acc_link);
    	//Mouse hover menuOption 'Music'
    	actions.moveToElement(menuOption).perform();
		
	}
	/*
	 * public void enterPropertyDetail(String propertydetail) {
	 * waitForInvisibleOf(page_loader, 30); waitForInvisibleOf(page_inline_loader,
	 * 30); waitForClickable(search_textbox_properties, 5);
	 * clickClickable(search_textbox_properties); if(isDisplayed(clearfilter)){
	 * waitForClickable(clearfilter); clickClickable(clearfilter);
	 * waitForInvisibleOf(page_loader, 30); }
	 * type(search_textbox_properties,propertydetail);
	 * clickClickable(search_icon_property); waitForInvisibleOf(page_loader, 30); }
	 * 
	 *//*** Click On Search Results ***/
	/*
	 * public void clickSearchResults() {
	 * waitForClickable(searchresult_location_name_container, 5);
	 * clickClickable(searchresult_location_name_container); }
	 * 
	 *//*** Get the record count ***/
	/*
	 * public String getRecordCount() { waitForInvisibleOf(page_loader, 10); return
	 * find(properties_record_count).getText(); }
	 * 
	 *//*** Get the property name ***/
	/*
	 * public String getPropertyName() { waitForInvisibleOf(page_loader, 10); return
	 * find(searchresult_location_name_container).getText(); }
	 * 
	 *//*** Get the property Address ***/
	/*
	 * public String getPropertyAddress() { waitForInvisibleOf(page_loader, 10);
	 * return find(searchresult_location_cardaddress_container).getText(); }
	 * 
	 *//*** Get the property card number ***/
	/*
	 * public String getPropertyAccountNo() { waitForInvisibleOf(page_loader, 10);
	 * return find(searchresult_location_cardaccountno_container).getText(); }
	 *//*** Get the text for breadcrumb 1st element ***/
	/*
	 * public String getBreadcrumbsTextFirstElement() {
	 * waitForInvisibleOf(page_loader, 20);
	 * waitForVisiblityOf(breadcrumbs_first_element, 20); return
	 * find(breadcrumbs_first_element).getText(); }
	 * 
	 *//*** Get the text for breadcrumb 2nd element ***/
	/*
	 * public String getBreadcrumbsTextSecondElement() {
	 * waitForInvisibleOf(page_loader, 20);
	 * waitForVisiblityOf(breadcrumbs_second_element, 20); return
	 * find(breadcrumbs_second_element).getText(); }
	 * 
	 *//*** Click on breadcrumbs ***/
	/*
	 * public void clickOnBreadcrumbsText() { waitForInvisibleOf(page_loader, 30);
	 * waitForVisiblityOf(breadcrumbs_first_element,20);
	 * waitForClickable(breadcrumbs_first_element);
	 * clickClickable(breadcrumbs_first_element); }
	 * 
	 *//*** Check for fav Icon ***/
	/*
	 * public boolean isDisplayedFavIcon() { waitForPresenceOf(menu_icon_fav,10);
	 * return find(menu_icon_fav).isDisplayed(); }
	 * 
	 *//*** Check for property image ***/
	/*
	 * public String getPropertyImgText() { waitForInvisibleOf(page_loader, 10);
	 * return find(property_img).getText(); }
	 * 
	 *//*** Check for Arrow ***/
	/*
	 * public boolean isDisplayedArrow() {
	 * waitForPresenceOf(searchresult_event_arrow_center,10); return
	 * find(searchresult_event_arrow_center).isDisplayed(); }
	 *//*** Check for property image ***/
	/*
	 * public boolean isDisplayedPropertyImage() {
	 * waitForPresenceOf(property_img,10); return find(property_img).isDisplayed();
	 * }
	 * 
	 *//*** Clear filter ***/
	/*
	 * public void clearFilter() { if(isDisplayed(clearfilter)){
	 * waitForClickable(clearfilter); clickClickable(clearfilter); }
	 * waitForInvisibleOf(page_loader, 10); }
	 * 
	 *//*** Click on Toggle ***/
	/*
	 * public void clickShowcancelledToggle() { waitForInvisibleOf(page_loader, 30);
	 * // waitForClickable(toggle, 5); clickClickable(toggle);
	 * waitForInvisibleOf(page_loader, 30); }
	 * 
	 *//*** Click on Property Details ***/
	/*
	 * public void clickOnPropertyDetails() { waitForInvisibleOf(page_loader, 30);
	 * waitForClickable(searchresult_location_container, 30);
	 * clickClickable(searchresult_location_container);
	 * waitForInvisibleOf(page_loader, 30); }
	 * 
	 *//**
		 * Get number of records in PropertyList table
		 * 
		 * @return
		 **/
	/*
	 * public String getNumberOfPropertyRecords() { waitForInvisibleOf(page_loader,
	 * 10); waitForPresenceOf(properties_record_count, 10); return
	 * find(properties_record_count).getText();
	 * 
	 * }
	 * 
	 *//*** Get the default property name ***/
	/*
	 * public String getDefaultPropertyName() { waitForInvisibleOf(page_loader, 10);
	 * return find(defualt_property_name).getText(); }
	 * 
	 *//*** Get the default property card number ***/
	/*
	 * public String getDefaultPropertyCardNo() { waitForInvisibleOf(page_loader,
	 * 10); return find(defualt_property_account_number).getText(); }
	 * 
	 * 
	 *//*** Get the number of property displayed ***/
	/*
	 * public int getNumberOfListedProperties() { waitForInvisibleOf(page_loader,
	 * 10); return findAll(property_element_locator).size(); }
	 * 
	 *//*** Get the number of property displayed ***/
	/*
	 * public List<String> getListedProperties() { waitForInvisibleOf(page_loader,
	 * 10); List<WebElement> listElem=findAll(property_name_element_locator);
	 * List<String> propertyNames = new LinkedList<String>(); for(int
	 * i=0;i<listElem.size();i++) { propertyNames.add(listElem.get(i).getText()) ; }
	 * return propertyNames; }
	 * 
	 *//*** Click on Signout Link ***//*
										 * public void clickSignoutLinkForInternalUser() {
										 * waitForInvisibleOf(page_loader, 40); scrollToTop(); //
										 * waitForClickable(user_profile, 40); // clickClickable(user_profile); //
										 * waitForClickable(signout_link, 40); // clickClickable(signout_link); //
										 * waitForInvisibleOf(signout_page_loader, 40); //
										 * waitForClickable(signout_account_locator, 40); //
										 * clickClickable(signout_account_locator); clickClickable(dbc_logout);
										 * waitForInvisibleOf(dbc_logout, 40); try { Thread.sleep(7000); } catch
										 * (InterruptedException e) { // TODO Auto-generated catch block
										 * e.printStackTrace(); } }
										 */
}
