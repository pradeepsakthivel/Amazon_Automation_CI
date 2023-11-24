package com.amazon.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WindowType;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.amazon.pages.AmazonLandingPage;
import com.amazon.pages.BasePage;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.aventstack.extentreports.Status;

import com.fasterxml.jackson.databind.JsonNode;

import io.restassured.path.json.JsonPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class TestUtilities extends BaseTest {

	protected ResourceBundle qa_properties = ResourceBundle.getBundle("Properties.QA");
	protected ResourceBundle dev_properties = ResourceBundle.getBundle("Properties.DEV");
	protected ResourceBundle stage_properties = ResourceBundle.getBundle("Properties.STAGE");
	protected ResourceBundle prod_properties = ResourceBundle.getBundle("Properties.PROD");
	protected ResourceBundle aws_properties = ResourceBundle.getBundle("Properties.AWS");

	protected static String application_url;

	static int index = 1;
	//	public static String api_base_uri;

	public TestUtilities(String browserValue) {
		super(browserValue);
		System.out.println(" *********************** Test Utilities Constructor **********************");
	}

	public TestUtilities() {
		System.out.println("*********************** Test Utilities Null Constructor **********************");
	}

	protected void loginAmazon(String username, String password, boolean isAPI) {
		writeLog("Logging into the application as amazon user", true, true);
		loginAmazonUser(username, password, isAPI);

	}

	/** Login to the Amazon application Using InternalUser */
	protected void loginAmazonUser(String user, String pwd, boolean isAPI) {

		System.out.println("Application URl" + application_url);

		AmazonLandingPage az_SignInPage = new AmazonLandingPage(driver, log, test);
		az_SignInPage.openPageUrl(application_url);
		az_SignInPage.loginAZUser(user, pwd);
		System.out.print("*** Logged Into Amamzon page succesfully ****");

		//		if (!isAPI) {
		//			/** Assert Dashboard Breadcrumbs */
		//
		//			System.out.println("*** Logged Into Amamzon page succesfully ****");
		//
		//
		//		}

	}

	protected void logoutFromAmazon() {
		AmazonLandingPage az_SignInPage = new AmazonLandingPage(driver, log, test);

		try {
			// Signout from the Amazon account

			az_SignInPage.signout();
			writeLog("Logout from the application - PASS", true, true);
		} finally {
			// Quit all the browser
			teardown();
		}

	}

	@BeforeSuite
	// Return the environment URL
	protected String getEnvironmentURL() {
		System.out.println(" *********************** Test Utilities BeforeSuite **********************");
		if (environment.equalsIgnoreCase("DEV")) {
			application_url = dev_properties.getString("APPLICATION_URL");
			git_report_url = dev_properties.getString("GIT_AUTOMATION_REPORT_URL");
		}
		if (environment.equalsIgnoreCase("QA")) {
			application_url = qa_properties.getString("APPLICATION_URL");

		}
		if (environment.equalsIgnoreCase("STAGE")) {
			application_url = stage_properties.getString("APPLICATION_URL");

		}

		if (environment.equalsIgnoreCase("PROD")) {

		}
		return application_url;
	}

	protected String createEmail(String emailId) {
		System.out.println("Old Email Value: " + emailId);

		String domain = emailId.substring(emailId.indexOf("@") + 1);
		String emailValue = emailId.substring(0, emailId.indexOf("@"));
		System.out.println("Domain: " + domain);
		System.out.println("Email: " + emailValue);
		return getUniqueId(emailValue) + "@" + domain;
	}

	public String getUniqueId(String email) {
		return String.format("%s_%s", email, UUID.randomUUID().toString().substring(0, 5));
	}

	protected void sleep(long m) {
		try {
			Thread.sleep(m);
			log.info("Sleep invoked for " + (m / 1000) + " seconds");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Get the current system date */
	protected String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(date);
	}

	/** Get the current system time */
	protected String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mmaa");
		return timeFormat.format(cal.getTime());
	}

	/** Log function to write both console and extent report logs **/
	protected void writeLog(String logMessage, boolean isConsole, boolean isExtent) {
		// if isConsole is true then write log to print in the console
		if (isConsole) {
			log.info(logMessage);
		}
		// if isExtent is true then write log to the extent report
		if (isExtent) {
			test.log(Status.PASS, logMessage);
		}
	}

	public String fetchAwsSecret(String key) {

		String accessKey = aws_properties.getString("AWS_ACCESS_KEY");
		String secretKey = aws_properties.getString("AWS_SECRET_KEY");
		String secretArn = aws_properties.getString("AWS_SECRET_ARN");
		String region = aws_properties.getString("AWS_REGION");
		String secret = null;

		try {
			// Create a Secrets Manager client
			AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(region)
					.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
					.build();

			GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretArn);

			JSONObject jsonObject = new JSONObject(client.getSecretValue(getSecretValueRequest).getSecretString());
			secret = jsonObject.get(key).toString();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return secret;

	}

	/** Get Date in UTC format */
	protected String getDateTimeInISOFormat() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());

		return nowAsISO;
	}

	public void copyXLSFile(File source, File dest) throws IOException {
		try {
			String pathstring = dest.getAbsolutePath();
			System.out.println("File PATH is: " + pathstring);
			Path path = Paths.get(pathstring);

			Files.deleteIfExists(path);

			FileUtils.copyFile(source, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected String getInputFileNameForTestClass(String env, String runtype, String className) {
		String envPath = null;

		if (env.equalsIgnoreCase("Dev")) {
			envPath = "DEV";
		}
		if (env.equalsIgnoreCase("QA")) {
			envPath = "QA";
		}
		if (env.equalsIgnoreCase("STAGE")) {
			envPath = "STG";
		}
		if (env.equalsIgnoreCase("PROD")) {
			envPath = "PROD";
		}
		localTime = getLocalTimeToRenameFile().trim();
		if (runtype.equalsIgnoreCase("sanity")) {

			input_file_path = "src/test/resources/dataProviders/sanity/" + envPath + "/";
			output_folder = "target/test-output/ExecutionResult/sanity/";
			output_file_path = "target/test-output/ExecutionResult/sanity/" + envPath + "_" + localTime + "/";

			input_file_name = runtype + "_" + className + ".xlsx";
			resultFileName = runtype + "_" + className + "Result" + ".xlsx";
			fileName = input_file_name;
			System.out.println("Input: " + input_file_path);
			System.out.println("Input Name: " + input_file_name);
			System.out.println("Output: " + output_file_path);
			System.out.println("FileName BaseTest: " + fileName);
		}
		if (runtype.equalsIgnoreCase("regression")) {
			System.out.println("envPath: " + envPath);
			input_file_path = "src/test/resources/dataProviders/regression/" + envPath + "/";
			output_folder = "target/test-output/ExecutionResult/regression/";
			output_file_path = "target/test-output/ExecutionResult/regression/" + envPath + "_" + localTime + "/";
			input_file_name = runtype + "_" + className + ".xlsx";
			resultFileName = runtype + "_" + className + "Result" + ".xlsx";

			fileName = input_file_name;
			System.out.println("Input: " + input_file_path);
			System.out.println("Input Name: " + input_file_name);
			System.out.println("Output: " + output_file_path);
			System.out.println("FileName BaseTest: " + fileName);
		}
		return input_file_path + input_file_name;

	}

	//	public void JsonAssertAndUpdateResultMap(int actualStatusCode, int expectedStatusCode, String strexpectedResult,
	//			String stractualResult, String apiname, String t_response, String t_res_code) {
	//
	//		String tempResult = "FAIL";
	//		if (actualStatusCode == expectedStatusCode) {
	//			try {
	//				System.out.println("strexpectedResult:" + strexpectedResult);
	//
	//				if (strexpectedResult.isEmpty() == false) {
	//					JSONAssert.assertEquals(strexpectedResult, stractualResult, JSONCompareMode.LENIENT);
	//					System.out.println(apiname + " JsonAssert Success");
	//					tempResult = "PASS";
	//				} else {
	//					tempResult = "FAIL";
	//				}
	//			} catch (java.lang.AssertionError | JSONException e) {
	//				// TODO Auto-generated catch block
	//				System.out.println("Inside catch:\n" + e);
	//				int a = e.toString().indexOf(':');
	//				String error = e.toString().substring(a + 1);
	//				System.out.println("Trimmed error:\n" + error);
	//			} catch (java.lang.NullPointerException npe) {
	//				System.out.println("Inside null pointer catch!!!");
	//				t_response = "Null Pointer Exception has occured";
	//			}
	//			System.out.println(
	//					"t_response,tresponse_code,tempResult: " + t_response + "," + t_res_code + "," + tempResult);
	//		} else {
	//			new BaseTest().results.put(testcaseName, new String[] { t_res_code, t_response, "FAIL" });
	//		}
	//
	//		new BaseTest().results.put(testcaseName, new String[] { t_res_code, t_response, tempResult });
	//	}

}
