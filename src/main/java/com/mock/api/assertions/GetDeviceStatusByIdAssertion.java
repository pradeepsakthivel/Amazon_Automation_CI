package com.mock.api.assertions;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;

import com.amazon.base.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetDeviceStatusByIdAssertion extends BaseTest {

	/** Validating the url */
	public void getDeviceStatusResponseAssertion(Response response, String strexpectedResult, int expectedStatusCode,
			String testcaseName) {
		String t_res_code, t_response;
		String tempResult = "FAIL";

		t_res_code = String.valueOf(response.statusCode());
		// System.out.println("GetDeviceStatustByID Response Status:"+t_res_code);
		t_response = response.asString();
		// System.out.println("GetDeviceStatustByID Response String: "+t_response);

		JsonPath getDeviceStatusResponse = new JsonPath(response.asString());

		String stractualResult = response.asString();
		//
		// System.out.println("GetDeviceStatustByIDResponse:\n"+stractualResult);

		if (response.getStatusCode() == expectedStatusCode) {
			//
			switch (expectedStatusCode) {
			case 200:
				try {
					// System.out.println("strexpectedResult:"+strexpectedResult);

					if (!strexpectedResult.isEmpty()) {

						try {
							JSONAssert.assertEquals(strexpectedResult, stractualResult, JSONCompareMode.LENIENT);
							System.out.println("GetDeviceStstusByID JsonAssert Success");
							tempResult = "PASS";
							results.put(testcaseName, new String[] { t_res_code, t_response, tempResult });
						} catch (java.lang.AssertionError ase) {
							System.out.println("Assertion error caught");
							// System.out.println("t_response,tresponse_code,tempResult: "+t_response
							// +","+t_res_code+","+tempResult);
							results.put(testcaseName, new String[] { t_res_code, t_response, tempResult });

							// // Iterating and printing the map
							// for (Map.Entry<String, String[]> entry : results.entrySet()) {
							// String key = entry.getKey();
							// String[] values = entry.getValue();
							// System.out.println(key + " => " + Arrays.toString(values));
							Assert.assertEquals(true, false);
							// }
							// ase.printStackTrace();
						}

						catch (JSONException e) {
//							System.out.println("t_response,tresponse_code,tempResult: " + t_response + "," + t_res_code
//									+ "," + tempResult);
							results.put(testcaseName, new String[] { t_res_code, t_response, tempResult });
							Assert.assertEquals(true, false);
						}
					} else {
						tempResult = "FAIL";
					}
				} catch (java.lang.NullPointerException npe) {

					t_response = "Null Pointer Exception has occured";
					tempResult = "FAIL";
					Assert.assertEquals(true, false);
				}
				break;
			case 204:
				if (t_response.isEmpty()) {
					tempResult = "pass";
				}
				break;
			case 400:
				try {
//					System.out.println("strexpectedResult:" + strexpectedResult);
					if (!strexpectedResult.isEmpty()) {

						JSONAssert.assertEquals(strexpectedResult, stractualResult, JSONCompareMode.STRICT);
//						System.out.println("GetDeviceStstusByID JsonAssert Success");
						tempResult = "PASS";
					} else {
						tempResult = "FAIL";
					}
				} catch (java.lang.AssertionError | JSONException e) {
					System.out.println("Inside catch:\n" + e);
					int a = e.toString().indexOf(':');
					String error = e.toString().substring(a + 1);
					System.out.println("Trimmed error:\n" + error);
					tempResult = "FAIL";
					Assert.assertEquals(true, false);
				} catch (java.lang.NullPointerException npe) {
					System.out.println("Inside null pointer catch!!!");
					t_response = "Null Pointer Exception has occured";
					tempResult = "FAIL";
					Assert.assertEquals(true, false);
				}

				break;
			}

			// System.out.println("t_response,tresponse_code,tempResult: "+t_response
			// +","+t_res_code+","+tempResult);

		} else {

			results.put(testcaseName, new String[] { t_res_code, t_response, tempResult });
		}
	}

}
