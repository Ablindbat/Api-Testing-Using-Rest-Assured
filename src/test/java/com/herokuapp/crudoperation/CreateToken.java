package com.herokuapp.crudoperation;

import com.reusablemethods.BaseMethod;
import com.reusablemethods.JsonPathMethod;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateToken extends BaseMethod  {
	public static String generateToken() {
		//calling basicInfo method
		BaseMethod base = new BaseMethod();
		base.basicInfo();
		//Retrieve data from properties file
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		//write body in Json format
		String body = "{\r\n"
				+ "    \"username\" : \""+username+"\",\r\n"
				+ "    \"password\" : \""+password+"\"\r\n"
				+ "}";
		//Response
		Response response = httpreqeust
				.header("Content-Type","application/json")
				.body(body)
				.when().post("/auth")
				.then().log().body().extract().response();
		//calling json path class
		JsonPath gettoken = JsonPathMethod.rawToJson(response);
		//Retrieving data from response body
		mytoken = gettoken.get("token");
		
		return mytoken;
	}

}
