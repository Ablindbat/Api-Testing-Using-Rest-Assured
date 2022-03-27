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

public class UpdateBooking extends BaseMethod {

	@Test
	public void updateBooking() {
		//log4j
		logger = LogManager.getLogger(UpdateBooking.class);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/resources/log4j2.properties");
		//reporting
		logger.info("Starting Extent report for create booking");
		extenttest = extentreport.startTest("Create booking.");		
		//calling CreateBooking class
		CreateBooking book = new CreateBooking();
		book.createBooking();
		extenttest.log(LogStatus.PASS, "Create booking pass successfully.");
		extentreport.endTest(extenttest);
		logger.info("End extent report for create booking.");
		//reporting 
		logger.info("Start extent report for update booking");
		extenttest = extentreport.startTest("Update booking.");
		
		//Retrieving values from properties file 	
		logger.info("Retrieving data from property file.");
		String updatedfirstname = prop.getProperty("updatedfirstname");
		String updatedlastname =prop.getProperty("updatedlastname");
		String updatedtotalprice = prop.getProperty("updatedtotalprice");
		String updateddepositpaid = prop.getProperty("updateddepositpaid");
		String updatedcheckin = prop.getProperty("updatedcheckin");
		String updatedcheckout = prop.getProperty("updatedcheckout");
		String updatedadditionalneeds = prop.getProperty("updatedadditionalneeds");
		logger.info("Property file data Retrieved.");
		
		//body
		logger.info("Passing body data.");
		String updatebody = "{\r\n"
				+ "    \"firstname\" : \""+updatedfirstname+"\",\r\n"
				+ "    \"lastname\" : \""+updatedlastname+"\",\r\n"
				+ "    \"totalprice\" : "+updatedtotalprice+",\r\n"
				+ "    \"depositpaid\" : "+updateddepositpaid+",\r\n"
				+ "    \"bookingdates\" : {\r\n"
				+ "        \"checkin\" : \""+updatedcheckin+"\",\r\n"
				+ "        \"checkout\" : \""+updatedcheckout+"\"\r\n"
				+ "    },\r\n"
				+ "    \"additionalneeds\" : \""+updatedadditionalneeds+"\"\r\n"
				+ "}";
		//response
		logger.info("Passing update booking response.");
		response = httpreqeust
				.header("Content-Type","application/json")
				.header("cookie","token="+mytoken).body(updatebody)
				.when().put("/booking/"+id)
				.then().log().body().assertThat().statusCode(200).extract().response();	
		logger.info("Update booking response passed.");
				//Json Path
				JsonPath path = JsonPathMethod.rawToJson(response);
				//Retrieving result body data
				logger.info("Start retrieving data from result body.");
				String body_firstname =path.get("firstname");
				String body_lastname = path.get("lastname");
				String body_totalprice = path.get("totalprice").toString();
				String body_depositpaid = path.get("depositpaid").toString();
				String body_checkin = path.get("bookingdates.checkin");
				String body_checkout =path.get("bookingdates.checkout");
				String body_additionalneeds = path.get("additionalneeds");
				logger.info("Result body data Retrieved successful.");
				
				//assertion
				logger.info("Start assertion");
				Assert.assertEquals(updatedfirstname, body_firstname);
				Assert.assertEquals(updatedlastname, body_lastname);
				Assert.assertEquals(updatedtotalprice, body_totalprice);
				Assert.assertEquals(updateddepositpaid, body_depositpaid);
				Assert.assertEquals(updatedcheckin, body_checkin);
				Assert.assertEquals(updatedcheckout, body_checkout);
				Assert.assertEquals(updatedadditionalneeds, body_additionalneeds);
				logger.info("Assertion complete.");
				
				extenttest.log(LogStatus.PASS, "Update booking successfully.");
				extentreport.endTest(extenttest);
				logger.info("Update booking extent report end.");
	}
}
