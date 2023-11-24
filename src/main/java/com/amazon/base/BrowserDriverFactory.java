package com.amazon.base;

import java.util.Optional;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v114.emulation.Emulation;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;

public class BrowserDriverFactory {
	
	private ThreadLocal<WebDriver> driver= new ThreadLocal<WebDriver>();
	private String browser;
	private 	Logger log;


	public BrowserDriverFactory(String browser, Logger log){
		if(browser!=null) {
			this.browser=browser.toLowerCase();
			}
	this.log=log;
}

	public WebDriver createDriver() {
	
			log.info("Creating: "+browser+" driver");
	
			if(browser==null) {
				
				log.info("Browser is null");
				
//				System.out.println("*** Browser driver is empty***");
//				log.info("Creating: Firefox  driver");
//				System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
//				driver.set(new FirefoxDriver());
				
				ChromeOptions chromeoptions = new ChromeOptions();
				chromeoptions.addArguments("--window-size=1920,1080");
				chromeoptions.addArguments("start-maximized"); // open Browser in maximized mode
//				chromeoptions.addArguments("disable-infobars"); // disabling infobars
				chromeoptions.addArguments("--disable-extensions"); // disabling extensions
				chromeoptions.addArguments("--disable-gpu"); // applicable to windows os only
				chromeoptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
				chromeoptions.addArguments("--no-sandbox"); // Bypass OS security model
				chromeoptions.addArguments("--remote-allow-origins=*");
//				chromeoptions.addArguments("--headless");
				chromeoptions.addArguments("--incognito");
				
				try {
					WebDriverManager wdm = WebDriverManager.chromedriver().capabilities(chromeoptions);
				
					
					wdm.setup();
					driver.set(wdm.create());
					
						//LoggerFactory.getLogger(wdm.toString());
					
					
					System.out.println("Downloaded driver path: " +wdm.getDownloadedDriverPath());
					System.out.println("Downloaded driver version: " +wdm.getDownloadedDriverVersion());
					System.out.println("Chrome driver has been set successfully using webdriver manager");
					System.out.println("*** Browser driver SET completed ***");
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.getClass();
					log.error("Error setting up Chrome driver: " + e.getMessage(), e);
				//	e.printStackTrace();
					
				}
			    
			    
			}
			else {
	
				switch(browser) {
				case "chrome":
					//Changed to relative path to run in pipeline
					try {						
						//********* for linux **********						
						System.out.println("*** Browser driver NOT empty - chrome ***");
						System.out.println(System.getProperty("user.dir")+"/src/main/resources/chromedriver");
						System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/resources/chromedriver.exe");
						
						ChromeOptions chromeoptions = new ChromeOptions();
					//	chromeoptions.setBrowserVersion("");
						chromeoptions.addArguments("incognito");
						
						WebDriver t= new ChromeDriver(chromeoptions);
//						driver.set(t);
						//driver.set(new ChromeDriver(chromeoptions));
						
						DevTools devTools = ((HasDevTools) t).getDevTools();
						devTools.createSession();
						//devTools.send(Emulation.setDeviceMetricsOverride(768, 709, 75, true,
					      devTools.send(Emulation.setDeviceMetricsOverride(
					    		  320, 709, 75, true,
					    		
//					    		Optional.of(5),
//					    		Optional.of(425),Optional.of(709),
					    		
					    		Optional.empty(),
								Optional.empty(), Optional.empty(),
								Optional.empty(),
								Optional.empty(), Optional.empty(), 
								Optional.empty(), Optional.empty(),
								Optional.empty()));
						driver.set(t);
						/*
						 * ChromeOptions chromeoptions = new ChromeOptions();
						 * chromeoptions.addArguments("--window-size=1920,1080");
						 * chromeoptions.addArguments("start-maximized"); // open Browser in maximized
						 * mode chromeoptions.addArguments("disable-infobars"); // disabling infobars
						 * chromeoptions.addArguments("--disable-extensions"); // disabling extensions
						 * chromeoptions.addArguments("--disable-gpu"); // applicable to windows os only
						 * chromeoptions.addArguments("--disable-dev-shm-usage"); // overcome limited
						 * resource problems chromeoptions.addArguments("--no-sandbox"); // Bypass OS
						 * security model //chromeoptions.addArguments("--headless");
						 * chromeoptions.addArguments("incognito");
						 * chromeoptions.addArguments("--remote-allow-origins=*"); // driver.set(new
						 * ChromeDriver(options)); log.info("Creating Chrome Incognito");
						 * 
						 * 
						 * WebDriverManager wdm =
						 * WebDriverManager.chromedriver().capabilities(chromeoptions); wdm.setup();
						 * driver.set(wdm.create());
						 */
					     
//						WebDriverManager.chromedriver().setup();
//						WebDriverManager.chromedriver().linux().setup();
//						WebDriverManager wdm = WebDriverManager.chromedriver().browserInDocker();
//						WebDriverManager wdm = WebDriverManager.chromedriver();
//						driver = wdm.create();
//	                    driver.set( wdm.create());	                
						
						System.out.println("Chrome driver has been set successfully using webdriver manager");
						System.out.println("*** Browser driver SET completed ***");
						
					} catch (Exception e) {
						System.out.println(e);
						e.printStackTrace();
					}
					break;
				case "edge":
					//Changed to relative path to run in pipeline
//					System.setProperty("webdriver.edge.driver",  System.getProperty("user.dir")+"/src/main/resources/msedgedriver.exe"); 
//					driver.set(new EdgeDriver());
					EdgeOptions edgeoptions = new EdgeOptions();
					edgeoptions.addArguments("--window-size=1920,1080");
					edgeoptions.addArguments("start-maximized"); // open Browser in maximized mode
					edgeoptions.addArguments("disable-infobars"); // disabling infobars
					edgeoptions.addArguments("--disable-extensions"); // disabling extensions
					edgeoptions.addArguments("--disable-gpu"); // applicable to windows os only
					edgeoptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
					edgeoptions.addArguments("--no-sandbox"); // Bypass OS security model
//					edgeoptions.addArguments("--headless");
					edgeoptions.addArguments("--remote-allow-origins=*");
					edgeoptions.addArguments("--incognito");
					
//					WebDriverManager wdm = WebDriverManager.edgedriver().capabilities(edgeoptions);
//				    wdm.setup();
//				    driver.set(wdm.create());
//				    
//				    System.out.println("Edge driver has been set successfully using webdriver manager");
//					System.out.println("*** Browser driver SET completed ***");	
					
					try {
						WebDriverManager wdm3 = WebDriverManager.edgedriver().capabilities(edgeoptions);
					
						
						wdm3.setup();
						driver.set(wdm3.create());
						
							//LoggerFactory.getLogger(wdm.toString());
						
						
						System.out.println("Downloaded driver path: " +wdm3.getDownloadedDriverPath());
						System.out.println("Downloaded driver version: " +wdm3.getDownloadedDriverVersion());
						System.out.println("Edge driver has been set successfully using webdriver manager");
						System.out.println("*** Browser driver SET completed ***");
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.getClass();
						log.error("Error setting up Chrome driver: " + e.getMessage(), e);
					//	e.printStackTrace();
						
					}
					break;
		  
				case "firefox":
//					WebDriverManager.firefoxdriver().setup();
//					WebDriver t= new FirefoxDriver ();
//					driver.set(t);
					
					FirefoxOptions firefoxoptions = new FirefoxOptions();
					firefoxoptions.addArguments("--window-size=1920,1080");
					firefoxoptions.addArguments("start-maximized"); 
//					firefoxoptions.addArguments("disable-infobars");
					firefoxoptions.addArguments("--disable-extensions");
					firefoxoptions.addArguments("--disable-gpu");
					firefoxoptions.addArguments("--disable-dev-shm-usage");
					firefoxoptions.addArguments("--no-sandbox");
//					firefoxoptions.addArguments("--headless");
					firefoxoptions.addArguments("--remote-allow-origins=*");
					firefoxoptions.addArguments("--incognito");
					
					
					try {
						WebDriverManager wdm2 = WebDriverManager.firefoxdriver().capabilities(firefoxoptions);
						wdm2.setup();
						driver.set(wdm2.create());
						System.out.println("Firefox driver has been set successfully using webdriver manager");
						System.out.println("*** Browser driver SET completed ***");		
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("Error setting up Geicko driver: \n" + e.getMessage(), e);
					}
				    
				    					  
					break;
		
				default:
//					System.setProperty("webdriver.chrome.driver",  "src/main/resources/chromedriver.exe");
//					driver.set(new ChromeDriver());
					
					ChromeOptions chromeoptions = new ChromeOptions();
					chromeoptions.addArguments("--window-size=1920,1080");
					chromeoptions.addArguments("start-maximized"); // open Browser in maximized mode
					chromeoptions.addArguments("disable-infobars"); // disabling infobars
					chromeoptions.addArguments("--disable-extensions"); // disabling extensions
					chromeoptions.addArguments("--disable-gpu"); // applicable to windows os only
					chromeoptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
					chromeoptions.addArguments("--no-sandbox"); // Bypass OS security model
					chromeoptions.addArguments("--headless");
					chromeoptions.addArguments("--remote-allow-origins=*");
//					driver.set(new ChromeDriver(options));
				
					WebDriverManager wdm4 = WebDriverManager.chromedriver().capabilities(chromeoptions);
				    wdm4.setup();
				    driver.set(wdm4.create());
				    
				    System.out.println("Chrome driver has been set successfully using webdriver manager");
					System.out.println("*** Browser driver SET completed ***");
					break;
			
				}
			}	
			return driver.get();	
	}


}
