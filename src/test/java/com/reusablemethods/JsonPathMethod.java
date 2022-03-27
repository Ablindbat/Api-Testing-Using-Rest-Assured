package com.reusablemethods;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonPathMethod {

	//returns response in JSON format
	public static JsonPath rawToJson(Response res) {
		String respon = (res).asString();
		JsonPath x = new JsonPath(respon);
		return x;
		}
}
