package com.herokuapp.crudoperation;

import com.reusablemethods.BaseMethod;

public class HealthCheck extends BaseMethod{

	public void ping() {
		//response
		response = httpreqeust
				.header("Content-Type","application/json")
				.when().get("/ping")
				.then().log().all().assertThat().statusCode(201).extract().response();

	}
}
