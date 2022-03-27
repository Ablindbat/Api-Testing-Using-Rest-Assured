package com.herokuapp.crudoperation;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.reusablemethods.BaseMethod;
import com.reusablemethods.JsonPathMethod;

import io.restassured.path.json.JsonPath;

public class GetBooking extends BaseMethod {

	@Test
	public void getBooking() {
		
		logger = LogManager.getLogger(GetBooking.class);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/resources/log4j2.properties");
		//calling creating class
		CreateBooking book = new CreateBooking();
		book.createBooking();
		//reporting 
		logger.info("Starting extent report.");
		extenttest = extentreport.startTest("Get booking.");
		
		//Response query
		logger.info("Sending getbooking response.");
		response = httpreqeust
				.header("Content-Type","application/json")
				.header("cookie",mytoken)
				.when().get("/booking/"+id).
				then().log().all().assertThat().statusCode(200).extract().response();
		logger.info("Getbooking response complete");
		//Json path method call
		JsonPath path = JsonPathMethod.rawToJson(response);
		//retrieve body data
		logger.info("Retrieving data from result body");
		String body_firstname =path.get("firstname");
		String body_lastname = path.get("lastname");
		String body_totalprice = path.get("totalprice").toString();
		String body_depositpaid = path.get("depositpaid").toString();
		String body_checkin = path.get("bookingdates.checkin");
		String body_checkout =path.get("bookingdates.checkout");
		String body_additionalneeds = path.get("additionalneeds");
		logger.info("from get all data body.");
		
		//assertion
		logger.info("Assertion start.");
		Assert.assertEquals(firstname, body_firstname);
		Assert.assertEquals(lastname, body_lastname);
		Assert.assertEquals(totalprice, body_totalprice);
		Assert.assertEquals(depositpaid, body_depositpaid);
		Assert.assertEquals(checkin, body_checkin);
		Assert.assertEquals(checkout, body_checkout);
		Assert.assertEquals(additionalneeds, body_additionalneeds);
		logger.info("Assertion complete");
		
		extenttest.log(LogStatus.PASS, "Get booking successfully.");
		extentreport.endTest(extenttest);
		logger.info("Extent report complete.");
	}
	
}
