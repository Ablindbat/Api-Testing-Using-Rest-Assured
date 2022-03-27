package com.herokuapp.crudoperation;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.reusablemethods.BaseMethod;
import com.reusablemethods.JsonPathMethod;

import io.restassured.path.json.JsonPath;

public class CreateBooking extends BaseMethod {
	//Generate token
	
	@Test
	public void createBooking() {
		logger = LogManager.getLogger(CreateBooking.class);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/resources/log4j2.properties");
		logger.info("Base URI and request specification established.");
		//ping 
		logger.info("ping start");
		HealthCheck ping = new HealthCheck();
		ping.ping();
		logger.info("ping successfully.");
		
		logger.info("Generating token");
		String token = CreateToken.generateToken();	
		logger.info("Token generated successfully.");
		//body parameter
		logger.info("Body data entry.");
				String body = "{\r\n"
						+ "    \"firstname\" : \""+firstname+"\",\r\n"
						+ "    \"lastname\" : \""+lastname+"\",\r\n"
						+ "    \"totalprice\" : "+totalprice+",\r\n"
						+ "    \"depositpaid\" : "+depositpaid+",\r\n"
						+ "    \"bookingdates\" : {\r\n"
						+ "        \"checkin\" : \""+checkin+"\",\r\n"
						+ "        \"checkout\" : \""+checkout+"\"\r\n"
						+ "    },\r\n"
						+ "    \"additionalneeds\" : \""+additionalneeds+"\"\r\n"
						+ "}";
				
				logger.info("Sending response");
				//Response to sent end uri and body
				response = httpreqeust
						.header("Content-Type","application/json")
						.header("cookie",token)
						.body(body).when().post("/booking")
						.then().log().body().assertThat().statusCode(200)
						.extract().response();
				logger.info("response complete");
		//Json Path
		JsonPath path = JsonPathMethod.rawToJson(response);
		logger.info("Retrieving body data");
		//Retrieve the data from body
		id  = path.get("bookingid").toString();
		String body_firstname =path.get("booking.firstname");
		String body_lastname = path.get("booking.lastname");
		String body_totalprice = path.get("booking.totalprice").toString();
		String body_depositpaid = path.get("booking.depositpaid").toString();
		String body_checkin = path.get("booking.bookingdates.checkin");
		String body_checkout =path.get("booking.bookingdates.checkout");
		String body_additionalneeds = path.get("booking.additionalneeds");
		logger.info("Retrieve data from body successful.");
		
		logger.info("Assertion the data");
		//assertion
		Assert.assertEquals(firstname, body_firstname);
		Assert.assertEquals(lastname, body_lastname);
		Assert.assertEquals(totalprice, body_totalprice);
		Assert.assertEquals(depositpaid, body_depositpaid);
		Assert.assertEquals(checkin, body_checkin);
		Assert.assertEquals(checkout, body_checkout);
		Assert.assertEquals(additionalneeds, body_additionalneeds);	
		logger.info("Assertion complete");
	}

}
