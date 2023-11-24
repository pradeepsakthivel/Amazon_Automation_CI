package com.amazon.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class BaseTest {
	protected static WebDriver driver;
	protected WebDriverWait wait, wait2;
	protected Logger log;

	protected String browser;
	protected static String environment, testcaseType, fileName, resultFileName, className, runType;
	protected static String userAPIVersion, propertyAPIVersion, utilityAPIVersion, statisticsAPIVersion, uiVersion,
			registrationVersion, mvcVersion, git_test_repo, git_report_url;
	protected static String reportFileName, apireportFileName;
	protected static ArrayList<String> tesResult = new ArrayList<String>();
	protected static String input_file_path, output_file_path, input_file_name, localTime, output_folder;

	public static Map<String, String[]> results = new HashMap<String, String[]>();
	public static ExtentReports extent;
	public static ExtentTest test;

	protected String skipBrowserInstantiation, testcaseName, message = "@TestResult";
	protected int rowCount;

	static int testPass = 0, testFail = 0, testSkip = 0;
	static int classTestPass = 0, classTestFail = 0, classTestSkip = 0;
	static String status;

	String testTagname = null, zipDirName;
	List<String> filesListInDir = new ArrayList<String>();

	public BaseTest(String browserValue) {
		this.browser = browserValue;
		System.out.println(" *********************** Base Class Constructor **********************");
		System.out.println(" Base Class Constructor Browser value:" + browserValue);
	}

	public BaseTest() {
		System.out.println("BaseTest Null Constructor");
	}

	@BeforeSuite
	public void reportData(ITestContext ctx) {
		System.out.println(" *********************** Base Class BeforeSuite **********************");
		System.out.println("Base Class ");
		environment = ctx.getCurrentXmlTest().getParameter("ENV");
		testcaseType = ctx.getCurrentXmlTest().getParameter("TESTCASETYPE");
//		

		// Changing Report file name as constant to fetch the most recent report from
		// ResultRepo always
		// reportFileName="TestAutomationReport_"+environment+"_"+localTime+".html";
		reportFileName = "TestAutomationReport_" + testcaseType + "_latest" + ".html";
		initializeReport();
	}

	public void addColumnHeader() {

		System.out.println("Inside AddColumnHeader:");
		if (testcaseType.equalsIgnoreCase("UI")) {
			if (!results.containsKey("testscenarioName")) {
				results.put("testscenarioName", new String[] { "TestStatus", "Message" });
			}
		} else {
			if (!results.containsKey("testscenarioName")) {
				results.put("testscenarioName", new String[] { "ActualResponseCode", "ActualResponse", "TestStatus" });
			}
		}
		System.out.println("Added Column Header");
	}

	public void initializeReport() {
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
				System.getProperty("user.dir") + "/" + output_file_path + reportFileName).viewConfigurer().viewOrder()
				.as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY }).apply();
		System.out.println("Extent Report: " + sparkReporter);
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Testcases Execution Report");
		sparkReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();

		extent.attachReporter(sparkReporter);
		addColumnHeader();
	}

	@BeforeClass(alwaysRun = true)
	public void login(ITestContext ctx) {

		System.out.println(" *********************** Base Class Before Class **********************");
		runType = ctx.getCurrentXmlTest().getParameter("runType");
		className = this.getClass().getSimpleName();
		environment = ctx.getCurrentXmlTest().getParameter("ENV");
		String suitename = ctx.getCurrentXmlTest().getSuite().getName();
		testTagname = ctx.getCurrentXmlTest().getName();
		System.out.println("**** Suite Name ****" + suitename);
		System.out.println("**** Test Tag Name ****" + testTagname);
		System.out.println("**** className ****" + className);

		log = LogManager.getLogger(suitename);

		if ((testcaseType.equalsIgnoreCase("UI"))) {

			BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);
			this.driver = factory.createDriver();
			// Launch application
//			driver.manage().window().maximize();	
		}

		// initialize variable to track test pass,fail & skip count
		classTestPass = 0;
		classTestFail = 0;
		classTestSkip = 0;
	}

	public void teardown() {
		try {
			driver.quit();
			log.info("Closing the browser - PASS");
			test.log(Status.PASS, "Closing the browser - PASS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		//
		System.out.println(" *********************** Base Class AfterMethod **********************");
		// Check if the Throwable within ITestResult is an AssertionError
//	    if ((result.getThrowable() instanceof AssertionError) || (result.getThrowable() instanceof JSONException)){
//	        test.log(Status.FAIL, "Test failed due to assertion error.");
//	        result.setStatus(ITestResult.FAILURE); // Setting the result status to FAILURE
//	    }

		String className = result.getMethod().getRealClass().getSimpleName();
		String methodName = className + "." + result.getName();
		String simplemethodName = result.getName();
		if (result.getStatus() == ITestResult.FAILURE) {
			testFail++;
			classTestFail++;
			test.log(Status.FAIL, MarkupHelper.createLabel(methodName + " FAILED ", ExtentColor.RED));
			test.fail(result.getThrowable());
			Collection<ITestNGMethod> listObj = result.getTestContext().getFailedConfigurations().getAllMethods();
			System.out.println("String: " + listObj + " and :" + listObj.toString());
			message = message + "\n\n" + "MethodName:" + simplemethodName + "\t Result:" + result.getThrowable();
		}
		if (result.getStatus() == ITestResult.SUCCESS) {
			testPass++;
			classTestPass++;
			test.log(Status.PASS, MarkupHelper.createLabel(methodName + " PASSED ", ExtentColor.GREEN));
			message = message + "\n\n " + "MethodName:" + simplemethodName + "\t Result:" + "Pass";

		}
		if (result.getStatus() == ITestResult.SKIP) {
			testSkip++;
			classTestSkip++;
			test.log(Status.SKIP, MarkupHelper.createLabel(methodName + " SKIPPED ", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
			message = message + "\n\n" + "MethodName:" + simplemethodName + "\t Result:" + result.getThrowable();
		}

		if (classTestFail > 0) {
			status = "FAIL";
		} else if (classTestSkip > 0) {
			status = "SKIP";
		} else {
			status = "PASS";
		}
		System.out.println("Message inside after method: " + message);
	}

	@AfterClass
	public void statusupdate() {
		System.out.println(" *********************** Base Class Afterclass **********************");

		fileName = runType + "_" + className + ".xlsx";
		resultFileName = runType + "_" + className + "Result" + ".xlsx";
		System.out.println("Base After class filename: " + fileName);
		System.out.println("Base After class result filename: " + resultFileName);
		System.out.println("Result Map: " + results.toString());
		rowCount = ReadDataFromXLS.inputRowCount;
		System.out.println("Size of the map inside after class:" + results.size()
				+ " Number of Input Rows with heading:" + (rowCount + 1));
		results.size();

//		System.out.println("Printing result Map");
//
//     //Iterating and printing the map
//		for (Map.Entry<String, String[]> entry : results.entrySet()) {
//			String key = entry.getKey();
//			String[] values = entry.getValue();
//			System.out.println(key + " => " + Arrays.toString(values));
//		}

	}

	public String getLocalTime() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);
		return formattedDate;
	}

	public String getLocalTimeToRenameFile() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedDate = myDateObj.format(myFormatObj);
		return formattedDate;
	}

	/*** execution summary content **/
	public String executionSummaryContent(String reportFilename) {
		String subject = "Automation Test Result : " + testTagname + " - " + environment;
		String buildInfo = "UI Version: " + uiVersion + "\r\nUser API Version: " + userAPIVersion
				+ "\r\nProperty API Version: " + propertyAPIVersion + "\r\nUtility API Version: " + utilityAPIVersion
				+ "\r\nStatistics API Version: " + statisticsAPIVersion + "\r\nRegistration Version: "
				+ registrationVersion + "\r\nMVC Version: " + mvcVersion;
		String buildDetailsHeading = "Component Details:\r\n";

		int totalTest = testPass + testFail + testSkip;
		String msg = "Execution Summary\r\n\r\n";
		String execStatus = "Total tests run: " + totalTest + ", Passes: " + testPass + ", Failures: " + testFail
				+ ", Skips: " + testSkip;

		String formattedDate = getLocalTime();
		String content = msg + execStatus + "\r\n\r\nRun Time: " + formattedDate + "\r\n" + "\r\nReport URL: "
				+ git_report_url + reportFilename;

		return content;
	}

	@AfterSuite
	public void afterSuiteMethod() {
		System.out.println(" *********************** Base Class AfterSuite **********************");
//		 if(results.size()==(rowCount+1))

		copyAndWrtieOutputfile();
		// sendUpdatesToTeams();
		publishResultToGit();
//		cleanTestOutpuDir();

	}

	public void copyAndWrtieOutputfile() {
		System.out.println("Copy fileName: " + fileName);
		String outputfile = output_file_path + resultFileName;
		String inputfile = input_file_path + fileName;
		System.out.println("copy output: " + outputfile);
		System.out.println("copy input file path: " + inputfile);

		File d1 = new File(outputfile);
		File s1 = new File(input_file_path + fileName);
		try {
			new TestUtilities().copyXLSFile(s1, d1);
			System.out.println("File copied succesfully");
			writeOutput();
//			results.clear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeOutput() {
		System.out.println("Inside Write output");
		WriteDataToXLS s1 = new WriteDataToXLS();
		System.out.println("output:" + output_file_path + resultFileName);
		s1.write(output_file_path + resultFileName, results);
		// write(Map < String, String[] > a)
	}

//	public void sendUpdates() {
//		//Get the version for all api
//		//Send updates to teams
//		sendUpdatesToTeams();	
//		System.out.println("Sent the updates to teams");
//	}

	public void getVersion() {
		// Get the version for all api
		BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);
		this.driver = factory.createDriver();
		// Maximize the browser
		driver.manage().window().maximize();

//		new TestUtilities().getVersionForAllAPIAndUI();		
		driver.quit();
	}

	public void sendmail() {

		System.out.println("Inside Send Mail");
		// File file = new File("target\\ExecutionSummary.txt");
		File file = new File("ExecutionSummary.txt");
		String content;
		try {
			if (testcaseType.equalsIgnoreCase("UI")) {
				content = executionSummaryContent(reportFileName);
				FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
			}
			if (testcaseType.equalsIgnoreCase("API")) {
				zipOutputFile();
				apireportFileName = zipDirName;
				content = executionSummaryContent(apireportFileName);
				FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// SendMailWithAttachment sendObject = new SendMailWithAttachment();
		// sendObject.sendMail(testPass,testFail,testSkip,subject,buildInfo);
	}

	public void publishResultToGit() {

		System.out.println("Publishing folder to git");
		// File file = new File("target\\ExecutionSummary.txt");
		File file = new File("ExecutionSummary.txt");
		PushToGit ptg = new PushToGit();
		String content;
		try {
			if (testcaseType.equalsIgnoreCase("UI")) {
				content = executionSummaryContent(reportFileName);
				FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
				ptg.pushtogit(reportFileName, testcaseType);
			}
			if (testcaseType.equalsIgnoreCase("API")) {
				zipOutputFile();
				apireportFileName = zipDirName;
				content = executionSummaryContent(apireportFileName);
				FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
				ptg.pushtogit(apireportFileName, testcaseType);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cleanTestOutpuDir() {
		// Creating a File object for directory
		File directoryPath = new File(output_folder);
		// List of all files and directories
		String contents[] = directoryPath.list();
		System.out.println("List of files and directories in the specified directory:");
		for (int i = 0; i < contents.length; i++) {
			System.out.println(contents[i]);
			int underscoreIndex = contents[i].indexOf("_");
			String extractedString = contents[i].substring(underscoreIndex + 1, underscoreIndex + 9);
			contents[i] = extractedString;
			System.out.println(contents[i]);
		}
	}

	public void sendUpdatesToTeams() {

		System.out.println("Send Updates to Teams");
		// File file = new File("target\\ExecutionSummary.txt");
		File file = new File("ExecutionSummary.txt");
		PushToGit ptg = new PushToGit();
		String content;
		try {
			if (testcaseType.equalsIgnoreCase("UI")) {
				content = executionSummaryContent(reportFileName);
				FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
				// ptg.pushtogit(reportFileName);
			}
			if (testcaseType.equalsIgnoreCase("API")) {
				zipOutputFile();
				apireportFileName = zipDirName;
				content = executionSummaryContent(apireportFileName);
				FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
				// ptg.pushtogit(apireportFileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ptg.postMessageTest();
	}

	public void zipOutputFile() {
		File dir = new File(output_file_path);
		String zipDirNameFilePath = output_file_path + dir.getName() + ".zip";
		zipDirName = dir.getName() + ".zip";
		System.out.println("Dir: " + dir);
		System.out.println("File Name:" + dir.getName());
		System.out.println("zipDirName: " + zipDirName);
		try {
			populateFilesList(dir);

			FileOutputStream fos = new FileOutputStream(zipDirNameFilePath);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String filePath : filesListInDir) {
				System.out.println("Zipping " + filePath);
				ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);
				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateFilesList(File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			System.out.println("Files inside directory: " + file.getAbsolutePath());
			if (file.isFile())
				filesListInDir.add(file.getAbsolutePath());
			else
				populateFilesList(file);
		}
	}

}
