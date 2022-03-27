package com.reusablemethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseMethod {

	protected static RequestSpecification httpreqeust;
	protected static String id = null;
	protected Response response;
	public static String mytoken = null;
	//Fetching Property file.
	protected static File file = new File("./Payload/payload.properties");
	protected static FileInputStream fiStream = null;
	protected static Properties prop = new Properties();
	//For report
	protected static ExtentReports extentreport;
	protected static ExtentTest extenttest;
	//logger using log4j
	public static Logger logger = null;
	
	//handling exception.
	static {
		try {
		fiStream = new FileInputStream(file);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			prop.load(fiStream);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Retrieving values from properties file 	
	protected String firstname = prop.getProperty("firstname");
	protected String lastname =prop.getProperty("lastname");
	protected String totalprice = prop.getProperty("totalprice");
	protected String depositpaid = prop.getProperty("depositpaid");
	protected String checkin = prop.getProperty("checkin");
	protected String checkout = prop.getProperty("checkout");
	protected String additionalneeds = prop.getProperty("additionalneeds");
	
	//basic requirement pass.
	@BeforeSuite (description="Basic url and request pass")
	public void basicInfo() {
		//passing specific base URI
		RestAssured.baseURI =  prop.getProperty("baseurl");
						
		//request
		httpreqeust = RestAssured.given();
	}
//	Set path of report
	@BeforeSuite
	protected void setReport() {
		extentreport = new ExtentReports("./Reports/Testreport.html");
	}
	
	
	//closing reporting
	@AfterSuite
	protected void endTest() {
		extentreport.flush();
		extentreport.close();
	}
}
