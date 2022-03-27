package com.herokuapp.crudoperation;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.reusablemethods.BaseMethod;

public class DeleteBooking extends BaseMethod {
	@Test
	public void deleteBooking() {
		logger = LogManager.getLogger(DeleteBooking.class);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/resources/log4j2.properties");
		//calling booking class
		CreateBooking book = new CreateBooking();
		book.createBooking();
		
		logger.info("Creating Extent Report.");
		//reporting 	
		extenttest = extentreport.startTest("Delete booking.");
		
		logger.info("sending delete response");
		//response 
		response = httpreqeust.header("Content-Type","application/json")
				.header("cookie","token="+mytoken)
				.when().delete("/booking/"+id)
				.then().log().all().assertThat()
				.statusCode(201).extract().response();
		logger.info("Delete response successfully");
		//Reporting
		extenttest.log(LogStatus.PASS, "Delete successfully.");
		extentreport.endTest(extenttest);
		logger.info("Extent report complete.");
	}
}
