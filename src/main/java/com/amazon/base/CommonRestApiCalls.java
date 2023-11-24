package com.amazon.base;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonRestApiCalls {

	public void createPostRequestForApiWithBodyWithHeader(String baseURI, String basePath, HashMap reqHeaders,
			JSONObject requestParams, String json_schema, int statusCode) {
		RestAssured.baseURI = baseURI;

		// Creating an object of RequestSpecBuilder
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		// Setting Base URI
		reqBuilder.setBaseUri(baseURI);
		// Setting Base Path
		reqBuilder.setBasePath(basePath);
		// Setting demo headers
		reqBuilder.addHeaders(reqHeaders);

		// Setting body
		reqBuilder.setBody(requestParams.toJSONString());

		// Getting RequestSpecification reference using builder() method
		RequestSpecification reqSpec = reqBuilder.build();

		RequestSpecification reqSpecHit = RestAssured.given(reqSpec);

		// Logging everything
		reqSpecHit.log().all();

		// Response response=reqSpecHit.when()
		// .post()
		// .then()
		// .extract().response();

		reqSpecHit.when().post().then().body("data.messages[0].description",
				equalTo("Default Property was set successfully."));

		reqSpecHit.when().post().then().assertThat().body(matchesJsonSchemaInClasspath(json_schema))
				.statusCode(statusCode);

		// return response;
	}

	public Response createPostRequestWithBodyWithHeader(String baseURI, String basePath, HashMap reqHeaders,
			JSONObject requestParams) {
		RestAssured.baseURI = baseURI;

		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setBaseUri(baseURI);
		reqBuilder.setBasePath(basePath);
		reqBuilder.addHeaders(reqHeaders);

		reqBuilder.setBody(requestParams.toJSONString());

		RequestSpecification reqSpec = reqBuilder.build();

		RequestSpecification reqSpecHit = RestAssured.given(reqSpec);

		reqSpecHit.log().all();

		Response response = reqSpecHit.when().post().then().extract().response();

		return response;
	}

	public Response createGetRequestForApiWithHeader(String baseURI, String basePath, HashMap reqHeaders) {
		RestAssured.baseURI = baseURI;

		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setBaseUri(baseURI);
		reqBuilder.setBasePath(basePath);
		reqBuilder.addHeaders(reqHeaders);

		RequestSpecification reqSpec = reqBuilder.build();

		RequestSpecification reqSpecHit = RestAssured.given(reqSpec);

		reqSpecHit.log().all();

		Response response = reqSpecHit.when().get().then().extract().response();

		return response;
	}

	public Response createPutRequestWithBodyWithHeader(String baseURI, String basePath, HashMap reqHeaders,
			JSONObject requestParams) {
		RestAssured.baseURI = baseURI;

		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setBaseUri(baseURI);
		reqBuilder.setBasePath(basePath);
		reqBuilder.addHeaders(reqHeaders);

		reqBuilder.setBody(requestParams.toJSONString());

		RequestSpecification reqSpec = reqBuilder.build();

		RequestSpecification reqSpecHit = RestAssured.given(reqSpec);

		reqSpecHit.log().all();

		Response response = reqSpecHit.when().put().then().extract().response();

		return response;
	}

	public Response createDeleteRequestWithHeader(String baseURI, String basePath, HashMap reqHeaders) {
		RestAssured.baseURI = baseURI;

		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setBaseUri(baseURI);
		reqBuilder.setBasePath(basePath);
		reqBuilder.addHeaders(reqHeaders);

		RequestSpecification reqSpec = reqBuilder.build();

		RequestSpecification reqSpecHit = RestAssured.given(reqSpec);

		reqSpecHit.log().all();

		Response response = reqSpecHit.when().delete().then().extract().response();

		return response;
	}

}
