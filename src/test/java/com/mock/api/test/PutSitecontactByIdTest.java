//package com.mock.api.test;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import org.json.JSONException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.skyscreamer.jsonassert.JSONCompareMode;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Factory;
//import org.testng.annotations.Test;
//
//import com.aventstack.extentreports.Status;
//import com.mock.api.assertions.GetDeviceStatusByIdAssertion;
//import com.mock.api.assertions.PutSitecontactByIdAssertion;
//import com.mydeepblue.lower.env.assertions.SignOutPageAssertion;
//import com.mydeepblue.lower.env.base.APIUtilities;
//import com.mydeepblue.lower.env.base.BaseTest;
//import com.mydeepblue.lower.env.base.CommonRestApiCalls;
//import com.mydeepblue.lower.env.base.ReadDataFromExcel;
//import com.mydeepblue.lower.env.base.ReadDataFromXLS;
//import com.mydeepblue.lower.env.base.TestUtilities;
//
//
//import io.restassured.RestAssured;
//import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.internal.path.json.JSONAssertion;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.skyscreamer.jsonassert.JSONCompareMode;
//import org.json.JSONException;
//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
//import static org.hamcrest.Matchers.equalTo;
//
//public class PutSitecontactByIdTest extends TestUtilities {
//	//	String networkId=apiProperties.getString("CB_AUTH_VALUE");
//	String cb_auth_value,auth_value,skipLogin;
//	String loginUsername,loginPassword;
//	int expectedStatusCode;
//	String firstName,lastName,email,role,phone,extension,contactpriority,propertyId;
//	String strexpectedResult,testname,siteContact;
//	boolean skipTest=false;
//	public static int numberofrows;
//
//	APIUtilities apiutilities = new APIUtilities();
//
//	@Factory(dataProvider = "readCSVFile1",dataProviderClass=ReadDataFromXLS.class)	
//	public PutSitecontactByIdTest(Map<String,String> testdataMap) { 
//		super(testdataMap.get("browser"));
//		numberofrows++;
//		this.loginUsername = testdataMap.get("loginEmail");
//		this.loginPassword = testdataMap.get("loginPassword");
//		this.skipLogin=testdataMap.get("skipLogin");
//		this.auth_value=testdataMap.get("authorization");
//		this.cb_auth_value=testdataMap.get("cb-authorization");
//
//		this.propertyId=testdataMap.get("propertyId");
//		this.siteContact=testdataMap.get("siteContactRequestList");
//		//		this.firstName = testdataMap.get("firstName");
//		//		this.lastName = testdataMap.get("lastName"); 
//		//		this.email = testdataMap.get("email");	
//		//		this.role = testdataMap.get("role");
//		//		this.phone = testdataMap.get("phone");
//		//		this.extension=testdataMap.get("extension");
//		//		this.contactpriority=testdataMap.get("contactPriority");
//
//		this.strexpectedResult=testdataMap.get("expectedResponse");
//		this.expectedStatusCode=Integer.parseInt(testdataMap.get("expectedStatusCode"));
//		this.testname=testdataMap.get("testscenarioName");
//		skipBrowserInstantiation=skipLogin;
//		testcaseName=testname;
//
//
//		//		if( !loginUsername.isEmpty() ) { 
//		if( !loginUsername.isEmpty() ) { 
//			if(loginPassword==null) {
//				switch(loginUsername) {
//				case "svc-mdbautouser@cable.comcast.com":
//					this.loginPassword = getUserDetailsFromPropertyFile("serviceAutoUser.password");
//					break;
//				case "svc-mdbautospuser@cable.comcast.com":
//					this.loginPassword = getUserDetailsFromPropertyFile("serviceAutoSPUser.password");
//					break;
//				}
//			}
//		}
//	}
//
//	@Test(priority = 3, enabled = true) 
//	public void putSitecontactById() {
//		test = extent.createTest("PutUserByIdTest.putUserById:"+testname);
//		test.assignCategory("UserAPI_Put_UserById");
//		rowCount=numberofrows;
//		PutSitecontactByIdAssertion putsitecontactassertion = new PutSitecontactByIdAssertion();
//
//		//		if(!results.containsKey("testscenarioName")) {
//		//			super.results.put( "testscenarioName", new String[] {"ActualResponseCode", "ActualResponse", "TestStatus" });
//		//		}
//
//		getTokenBasedOnSkipLoginAttributeAndvalidatingTokens();
//
//		if(!skipTest) {
//			Response response = null;
//			try {
//			HashMap reqHeaders = new HashMap();
//			reqHeaders.put("cb-authorization", cb_auth_value);
//			reqHeaders.put("accept","*/*");
//			reqHeaders.put("Content-Type","application/json");
//			if(!auth_value.isEmpty()) {
//				reqHeaders.put("Authorization", auth_value);
//			}
//
//			HashMap requestParameter = new HashMap();
//			requestParameter.put("propertyId", propertyId);
//			requestParameter.put("siteContactRequestList", siteContact);
//			//			requestParameter.put("lastName", lastName);
//			//			requestParameter.put("email", email);
//			//			requestParameter.put("role", role);
//			//			requestParameter.put("phone", phone);
//			//			requestParameter.put("extension", extension);
//			//			requestParameter.put("contactpriority", contactpriority);
//
//			String requestFilePath=null;
//			String basePath=apiutilities.apiUrlProperties.getString("MANAGEPROPERTYAPI_SITECONTACT_PUT").replace("{id}", propertyId);
//			
//			requestFilePath  = apiutilities.getRequestTemplatesDirectory() + File.separator + "PutSitecontactById.json";
//			JSONObject PutUserByIdRequest = apiutilities.updateRequestPutSitecontactById(requestFilePath,requestParameter);
//
//			System.out.println("PutUserByIdRequest"+ PutUserByIdRequest.toString());
//
//			response = new CommonRestApiCalls().createPutRequestWithBodyWithHeader(api_base_uri, basePath, reqHeaders, PutUserByIdRequest);
//
//			putsitecontactassertion.putSitecontactByIDResponseAssertion(response,strexpectedResult,expectedStatusCode,testname,propertyId,reqHeaders);
//			}catch(Exception e) {
//				System.out.println("Inside catch:\n"+e);
//				int a=e.toString().indexOf(':');
//				String error=e.toString().substring(a+1);
//				System.out.println("Trimmed error:\n"+error);
//				
//				new BaseTest().results.put(testcaseName, new String[] { "null", "Null Response - Exception Occured /n"+error,"FAIL" });
//			}
//		}
//	}	
//
//	public void getTokenBasedOnSkipLoginAttributeAndvalidatingTokens() {
//		System.out.println("*************************** "+"InsideToken"+"*************************** ");
//		System.out.println("*************************** "+testcaseName+"*************************** ");
//		if(skipLogin.equalsIgnoreCase("TRUE")) {
//			if(cb_auth_value==null) {
//				String[] token=getAuthValueFromPropertyFile();
//				cb_auth_value=token[0];
//				auth_value=token[1];
//			}
//
//			if(cb_auth_value.isEmpty()) {				
//				super.results.put(testname, new String[] { "400", "Missing_CB_Auth_Token","Fail" });
//				skipTest=true;
//			}else if(auth_value.isEmpty() && api_env.contains("CODEBIG")) {				
//				super.results.put(testname, new String[] { "400", "Missing_Auth_Token_For_CodeBig_Env","Fail" });
//				skipTest=true;
//			}
//		}
//
//		//super.results=new TreeMap < String, Object[] >();		
//		if(skipLogin.equalsIgnoreCase("false")) {
//			System.out.println("SKIP LOGIN FALSE"+skipBrowserInstantiation);
//
//			try {
//				String[] token=getTokenFromDOM(loginUsername,loginPassword);
//				cb_auth_value=token[0];
//				auth_value=token[1];
//			}catch(Exception e) {
//				cb_auth_value="";
//				auth_value="";
//			}
//
//
//			if(cb_auth_value.isEmpty()) {				
//				super.results.put(testname, new String[] { "400", "Missing_CB_Auth_Token","Fail" });
//				skipTest=true;
//			}else if(auth_value.isEmpty() && api_env.contains("CODEBIG")) {				
//				super.results.put(testname, new String[] { "400", "Missing_Auth_Token_For_CodeBig_Env","Fail" });
//				skipTest=true;
//			}
//
//		}
//	}
//
//	@AfterTest
//	public void endReport(){
//		extent.flush();	 
//	}
//
//}
