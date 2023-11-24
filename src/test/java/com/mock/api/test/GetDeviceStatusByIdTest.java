package com.mock.api.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.amazon.base.APIUtilities;
import com.amazon.base.CommonRestApiCalls;
import com.amazon.base.ReadDataFromXLS;
import com.amazon.base.TestUtilities;
import com.mock.api.assertions.GetDeviceStatusByIdAssertion;

import io.restassured.response.Response;

public class GetDeviceStatusByIdTest extends TestUtilities {
	String networkId, x_secure_id, skipLogin;
	int expectedStatusCode;
	String strexpectedResult, testname;
	boolean skipTest = false;
	public static int numberofrows;

	APIUtilities apiutilities = new APIUtilities();

	@Factory(dataProvider = "readCSVFile1", dataProviderClass = ReadDataFromXLS.class)
	public GetDeviceStatusByIdTest(Map<String, String> testdataMap) {
		numberofrows++;
		this.x_secure_id = testdataMap.get("X-Secure-Key");
		this.networkId = testdataMap.get("networkId");

		this.expectedStatusCode = Integer.parseInt(testdataMap.get("expectedStatusCode"));
		this.strexpectedResult = testdataMap.get("expectedResult");
		this.testname = testdataMap.get("testscenarioName");
	}

	@Test(priority = 1, enabled = true)
	public void getDeviceStatusById() {
		test = extent.createTest("GetDeviceStatusByIdTest.getDeviceStatusById");
		test.assignCategory("DeviceStatusAPI_Get");
		rowCount = numberofrows;

		if (networkId.isEmpty()) {
			skipTest = true;
		} else if (x_secure_id.isEmpty()) {
			skipTest = true;
		}
		if (!skipTest) {
			Response response = null;
			try {
				HashMap<String, String> reqHeaders = new HashMap<String, String>();
				reqHeaders.put("X-Secure-Key", x_secure_id);
				reqHeaders.put("accept", "*/*");

				String basePath = apiutilities.apiUrlProperties.getString("GET_TOPOLOGY").replace("{networkId}",
						networkId);
				System.out.println("Base Path: " + basePath);
				response = new CommonRestApiCalls().createGetRequestForApiWithHeader(apiutilities.api_base_uri,
						basePath, reqHeaders);

				GetDeviceStatusByIdAssertion getDeviceStatusByIdAssertion = new GetDeviceStatusByIdAssertion();
				getDeviceStatusByIdAssertion.getDeviceStatusResponseAssertion(response, strexpectedResult,
						expectedStatusCode, testname);
			} catch (Exception e) {
				System.out.println("Inside Test catch:\n" + e);
				int a = e.toString().indexOf(':');
				String error = e.toString().substring(a + 1);
				System.out.println("Trimmed error:\n" + error);
			}
		}

	}

	@AfterTest
	public void endReport() {
		extent.flush();
	}

}
