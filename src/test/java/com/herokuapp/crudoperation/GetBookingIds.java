package com.herokuapp.crudoperation;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.reusablemethods.BaseMethod;
import com.reusablemethods.JsonPathMethod;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetBookingIds extends BaseMethod {

	@Test
	public void getBookingId() {

		logger = LogManager.getLogger(GetBookingIds.class);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/resources/log4j2.properties");
		//ping 
		logger.info("Start ping.");
		HealthCheck ping = new HealthCheck();
		ping.ping();
		logger.info("ping complete.");
		//reporting
		logger.info("Start extent report.");
		extenttest = extentreport.startTest("Get booking IDs.");		
		//Response 
		logger.info("Sending GetBooking id response");
		Response response = httpreqeust.
				header("Content-Type","application/json").
				when().get("/booking").
				then().log().all().assertThat().statusCode(200).extract().response();
		logger.info("Response complete.");
		//Json path locator
		JsonPath path = JsonPathMethod.rawToJson(response);
		logger.info("Retrieving data from test body.");
		String id = path.get("bookingid").toString();
		System.out.println(id);
		
		extenttest.log(LogStatus.PASS, "Get booking ids pass successfully.");
		extentreport.endTest(extenttest);
		logger.info("End extent report.");
	}
}
