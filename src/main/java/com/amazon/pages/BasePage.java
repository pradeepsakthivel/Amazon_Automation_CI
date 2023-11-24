package com.amazon.pages;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;


import com.aventstack.extentreports.ExtentTest;

public class BasePage {
	protected WebDriver driver;
	protected Logger log;
	protected ExtentTest test;

	public BasePage(WebDriver driver, Logger log, ExtentTest test) {
		this.driver = driver;
		this.log = log;
		this.test=test;
	}

	/** Open a given url */
	public void openUrl(String url) {
		driver.get(url);
	}

	/** Find a specific element with a given locator */
	public WebElement find(By locator) {
		return driver.findElement(locator);
	}

	/** Find a all element with a given locator */
	public List<WebElement> findAll(By locator) {
		return driver.findElements(locator);
	}

	/** Check a specific element is displayed with a given locator */
	public boolean isDisplayed(By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block

			//e.printStackTrace();
			return false;
		}
	}

	/** Check a specific element is selected with a given locator */
	public boolean isSelected(By locator) {
		try {
			return driver.findElement(locator).isSelected();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}
	}

	/** Check a specific element is enabled with a given locator */
	public boolean isEnabled(By locator) {
		try {
			return driver.findElement(locator).isEnabled();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}
	}

	/** Click a specific element with a given locator */
	public void clickVisible(By locator) {
		waitForVisiblityOf(locator,5);
		find(locator).click();
	}

	/** Click a specific element with a given locator */
	public void clickClickable(By locator) {
		waitForClickable(locator,5);
		find(locator).click();
	}

	/** Get visible text of specific element with a given locator */
	public String getText(By locator) {
		waitForVisiblityOf(locator,10);
		return find(locator).getText();
	}

	/** Clear the existing text of a field for the given locator */
	public void clear(By locator) {
		waitForVisiblityOf(locator,5);
		find(locator).clear();
	}

	/** Type a String to element with a given locator */
	public void type(By locator, String text) {
		waitForVisiblityOf(locator,5);
		find(locator).sendKeys(text);
	}

	/** Backspace and type to element with a given locator */
	public void backspaceNType(By locator, String text) {
		waitForVisiblityOf(locator,5);
		find(locator).sendKeys(Keys.BACK_SPACE,text);
	}

	/**
	 * Wait for specific ExpectedCondition for the given amount of time in seconds
	 */
	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 40;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds.longValue()));
		wait.until(condition);
	}

	/** Wait for given number of  seconds for element with a given locator  to be visible*/
	public void waitForVisiblityOf(By locator, Integer ... timeOutinSeconds) {
		int attempts=0;
		while (attempts <2) {
			try {
				waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
						(timeOutinSeconds.length > 0 ? timeOutinSeconds[0] :null));
				break;
			}catch(StaleElementReferenceException e) {};

			attempts++;
		}
	}

	/** Wait for given number of  seconds for element with a given locator  to be present*/
	public void waitForPresenceOf(By locator, Integer ... timeOutinSeconds) {
		int attempts=0;
		while (attempts <2) {
			try {
				waitFor(ExpectedConditions.presenceOfElementLocated(locator),
						(timeOutinSeconds.length > 0 ? timeOutinSeconds[0] :null));
				break;
			}catch(NoSuchElementException e) {};

			attempts++;
		}
	}

	/** Wait for given number of  seconds for element with a given locator  to become clickable*/
	public void waitForClickable(By locator, Integer ... timeOutinSeconds) {
		int attempts=0;
		while (attempts <2) {
			try {
				waitFor(ExpectedConditions.elementToBeClickable(locator),
						(timeOutinSeconds.length > 0 ? timeOutinSeconds[0] :null));
				break;
			}catch(Exception e) {};

			attempts++;
		}
	}

	/** Wait for given number of  seconds for element with a given locator  to become clickable*/
	public void waitForInvisibleOf(By locator, Integer ... timeOutinSeconds) {
		int attempts=0;
		while (attempts <2) {
			try {
				timeOutinSeconds[0] = timeOutinSeconds != null ? timeOutinSeconds.length : 10;
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutinSeconds.length));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
				break;
			}catch(Exception e) {};

			attempts++;
		}
	}

	/** Perform scroll to the bottom */
	public void scrollToBottom() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/** Perform scroll to the Top */
	public void scrollToTop() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0,0)");
	}

	/** Perform Scroll to the middle */
	public void scrollToMiddle() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0,200)");
	}	

	/** Perform Scroll Element to the middle */
	public void scrollElementToMiddle(WebElement e) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript ("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", e);
	}

	/** Get attribute value */
	public String getAttributeValue(By locator) {
		return find(locator).getAttribute("value");
	}

	/** Get the window handle for all the opened window */
	public ArrayList<String> getWindowHandles(){
		// hold all window handles in array list
		ArrayList<String> windowhandle = new ArrayList<String>(driver.getWindowHandles());
		return  windowhandle;	      
	}



	/** select the dropdown by visible text */
	public void selectDropdownByText(By locator,String text){
		WebElement selectElem=driver.findElement(locator);     
		Select selectObj=new Select(selectElem); 
		selectObj.selectByVisibleText(text);	      	      
	}

	/** open new window using javascript executor */
	public void openNewWindow() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript ("window.open('');");
	}


	//	/**Action class to perform click */
	//	public void clickUsingAction(By locator){
	//		WebElement webElement =driver.findElement(locator);
	//		 Actions builder = new Actions(driver);
	//		 builder.moveToElement(webElement).click(webElement);
	//		 builder.perform();	  
	////		driver.findElement(locator).sendKeys(Keys.RETURN);
	//	}


	/** Perform Scroll horizontally */
	public void scrollHorizontally(WebElement e) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript ("arguments[0].scrollIntoView()", e);
	}

	/** Get img src */
	public String getImageSrc(By locator) {
		return find(locator).getAttribute("src");
	}

	/** Click a toggle button */
	public void clickToggle(By locator) {
		find(locator).click();
	}

	/**Action class to move element */
	public void dragElement(By locator){
		WebElement element=driver.findElement(locator);
		Actions action = new Actions(driver);
		Point point = element.getLocation();
		int x = point.getX();
		int y = point.getY();

		System.out.println("Co-ordinates: "+x+","+y);
		action.clickAndHold(element).moveByOffset(325, 140).pause(1000).perform();
		action.contextClick().perform();
		element.click();		
	}

	public boolean isAttribtuePresent(By locator, String attribute) {
		Boolean result = false;
		try {
			String value = find(locator).getAttribute(attribute);
			if (value != null){
				result = true;
			}
		} catch (Exception e) {}

		return result;
	}

	/** Get attribute value */
	public String getAttributeValue(String attribute,By locator) {
		return find(locator).getAttribute(attribute);
	}

	/**execute script to change date */
	public void setDate(By locator,String date) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].value = '"+date+"';", find(locator));
	}	

	public Object getDom() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript("return window.__PRELOADED_STATE__");
	}
}
