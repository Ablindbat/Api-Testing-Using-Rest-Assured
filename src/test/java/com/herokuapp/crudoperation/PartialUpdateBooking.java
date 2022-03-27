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

public class PartialUpdateBooking extends BaseMethod {

	@Test
	public void partialUpdateBooking() {
		
		logger = LogManager.getLogger(PartialUpdateBooking.class);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/resources/log4j2.properties");
		//calling booking class
		CreateBooking book = new CreateBooking();
		book.createBooking();
		
		//reporting 
		logger.info("Start extent report.");
		extenttest = extentreport.startTest("Partial Update booking data.");
		//Retrieving values from properties file 
		logger.info("Retrieving data from property file");
		String updatedfirstname = prop.getProperty("updatedfirstname");
		String updatedlastname =prop.getProperty("updatedlastname");
		logger.info("Data fetched");
		//response body data
		logger.info("Sending property file data to body. ");
		String body = "{\r\n"
				+ "    \"firstname\" : \""+updatedfirstname+"\",\r\n"
				+ "    \"lastname\" : \""+updatedlastname+"\"\r\n"
				+ "}";
		
		//response
		logger.info("Sending partial update booking response.");
		response = httpreqeust.header("Content-Type","application/json")
				.header("cookie","token="+mytoken).body(body)
				.when().patch("/booking/"+id)
				.then().log().all().assertThat().statusCode(200).extract().response();
		logger.info("Response complete.");
		//Json path method call
				JsonPath path = JsonPathMethod.rawToJson(response);
				//retrieve body data
				logger.info("Retrieving data from test body.");
				String body_firstname =path.get("firstname");
				String body_lastname = path.get("lastname");
				String body_totalprice = path.get("totalprice").toString();
				String body_depositpaid = path.get("depositpaid").toString();
				String body_checkin = path.get("bookingdates.checkin");
				String body_checkout =path.get("bookingdates.checkout");
				String body_additionalneeds = path.get("additionalneeds");
				logger.info("Data retrieved.");
				//assertion
				logger.info("Assertion start.");
				Assert.assertEquals(updatedfirstname, body_firstname);
				Assert.assertEquals(updatedlastname, body_lastname);
				Assert.assertEquals(totalprice, body_totalprice);
				Assert.assertEquals(depositpaid, body_depositpaid);
				Assert.assertEquals(checkin, body_checkin);
				Assert.assertEquals(checkout, body_checkout);
				Assert.assertEquals(additionalneeds, body_additionalneeds);
				logger.info("Assertion complete");
				
				extenttest.log(LogStatus.PASS, "Partial update booking pass successfully.");
				extentreport.endTest(extenttest);
				logger.info("End extent report.");
	}
}
