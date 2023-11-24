package com.amazon.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;

public class APIUtilities {

	public ResourceBundle apiUrlProperties = ResourceBundle.getBundle("Properties.api_url");
	public String api_base_uri = apiUrlProperties.getString("API_BASE_URL");

	public static String getRootDirectory() {
		String rootDirectory = System.getProperty("user.dir");
		return rootDirectory;
	}

	public static String getRequestTemplatesDirectory() {

		String requestTemplateDirectory = getRootDirectory() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "RequestTemplates";
		return requestTemplateDirectory;
	}

	public static JSONObject readJsonFile(String filepath) {
		ObjectMapper mapper = new ObjectMapper();

		// Read from file
		JSONObject root = null;
		try {
			System.out.println("File Path inside readJsonFile: " + filepath);
			root = mapper.readValue(new File(filepath), JSONObject.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return root;
	}

	public JSONObject updateRequestPostUsersList(String fileName, HashMap requestParameter) {
		System.out.println("fileName inside update req fn:" + fileName);
		JSONObject root = readJsonFile(fileName);

		root.put("includeloggedInUser", requestParameter.get("includeloggedInUser"));
		root.put("queryString", requestParameter.get("queryString"));
		root.put("page", requestParameter.get("page"));
		root.put("pageSize", requestParameter.get("pageSize"));

		return root;
	}

//	public JSONObject updateRequestPostMsgToTeams(String fileName,HashMap<String,String> requestParameter) {
//		System.out.println("fileName inside update req fn:"+ fileName);
//		JSONObject root = readJsonFile(fileName);
//
//		ArrayList<HashMap> attachments = (ArrayList<HashMap>) root.get("attachments");
//		//		System.out.println("ArrayList attachment: "+attachments);
//
//		HashMap<String,ArrayList> contentDetails=(HashMap<String, ArrayList>) attachments.get(0).get("content");
//		//		System.out.println("Content Details"+contentDetails.toString());
//
//		ArrayList<HashMap> body= (ArrayList<HashMap>)contentDetails.get("body");
//		//		System.out.println("Body Details"+body);
//
//		//Runtime�
//		HashMap<String,String> runtime =body.get(2);
//
//		//parse the json to get the versions related parameter
//		HashMap<String,ArrayList> five = body.get(5);
//		ArrayList<HashMap> facts =  five.get("facts");
//
//		HashMap<String,String> ui = facts.get(0);
//		HashMap<String,String> userApi = facts.get(1);
//		HashMap<String,String> propertyApi = facts.get(2);
//		HashMap<String,String> utilityApi = facts.get(3);
//		HashMap<String,String> StatisticsApi = facts.get(4);
//		HashMap<String,String> Registration = facts.get(5);
//		HashMap<String,String> mvcVersion = facts.get(6);
//
//		//update the runtime value� from the input
//		runtime.put("text", requestParameter.get("Run Time"));
//
//		//update the version value� from the input
//		ui.put("value", requestParameter.get("UI Version"));
//		userApi.put("value", requestParameter.get("User API Version"));
//		propertyApi.put("value", requestParameter.get("Property API Version"));
//		utilityApi.put("value", requestParameter.get("Utility API Version"));
//		StatisticsApi.put("value", requestParameter.get("Statistics API Version"));
//		Registration.put("value", requestParameter.get("Registration Version"));
//		mvcVersion.put("value", requestParameter.get("MVC Version"));
//
//		//Test Results
//		HashMap three =(HashMap) body.get(3);
//		//		System.out.println("HashMap Three: "+three);
//		ArrayList<HashMap> scriptCountcolumns = (ArrayList) three.get("columns");
//
//		HashMap<String,ArrayList> totalScriptColumn = scriptCountcolumns.get(0);
//		ArrayList<HashMap> totalScriptItems = totalScriptColumn.get("items");
//		HashMap totalScripts = totalScriptItems.get(0);
//
//		HashMap<String,ArrayList> passCountColumns = scriptCountcolumns.get(1);
//		ArrayList<HashMap> passCountItems =  passCountColumns.get("items");
//		HashMap passCount =(HashMap) passCountItems.get(0);
//
//		HashMap<String,ArrayList> failCountColumns = scriptCountcolumns.get(2);
//		ArrayList<HashMap> failCountItems =  failCountColumns.get("items");
//		HashMap failCount =(HashMap) failCountItems.get(0);
//
//		HashMap<String,ArrayList> skipCountColumns = scriptCountcolumns.get(3);
//		ArrayList<HashMap> skipCountItems = skipCountColumns.get("items");
//		HashMap skipCount = skipCountItems.get(0);
//
//		HashMap<String,ArrayList> six = body.get(6);
//		ArrayList<HashMap> actions = six.get("actions");
//		HashMap url =actions.get(0);
//
//		//Update the pass,fail,skip count
//		totalScripts.put("text", requestParameter.get("Total tests run"));
//		passCount.put("text", requestParameter.get("Passes"));
//		failCount.put("text", requestParameter.get("Failures"));
//		skipCount.put("text", requestParameter.get("Skips"));
//		url.put("url", requestParameter.get("Report URL"));
//
//		return root;
//	}

	public static HashMap<String, String> readDataFromFile(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		HashMap<String, String> map = new HashMap<>();
		System.out.println("Inside Read Data File");
		// Read the data line by line
		String line;
		while ((line = reader.readLine()) != null) {
			// Split the line into an array of substrings
			String[] parts = line.split("\\s*,\\s*");
			// Iterate over the substrings and store them in the HashMap
			for (String part : parts) {
				String[] pair = part.split(": ");
				try {
					map.put(pair[0], pair[1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					// Handle the error here for out of bound indexes
				}
			}
		}
		reader.close();
		System.out.println("Inside Read Data File");
		return map;
	}

	public JSONObject updateRequestPutSitecontactById(String fileName, HashMap requestParameter) {
		System.out.println("fileName inside update req fn:" + fileName);
		JSONObject root = readJsonFile(fileName);

		System.out.println("file before Read\n" + root.toString());

		root.put("id", requestParameter.get("propertyId"));

		Object objProp = null, objBrand = null;

		if (requestParameter.get("siteContactRequestList") != null) {
			try {
				objProp = new JSONParser().parse((String) requestParameter.get("siteContactRequestList"));
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			root.put("siteContacts", objProp);
		}
		System.out.println("Request: " + root.toString());
		return root;
	}

}
