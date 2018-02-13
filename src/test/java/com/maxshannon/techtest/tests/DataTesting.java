package com.maxshannon.techtest.tests;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.maxshannon.techtest.DataReceiver;

public class DataTesting {
	
	
	/*
	 * declare types needed by the test-case inherited by this class
	 * 
	 * This type of design would have been better used with the data-receiver to enable 
	 * maximum reuse of code
	 * 
	 * */
	protected static DataReceiver dataReceiver;
	protected static ClassPathXmlApplicationContext context;
	protected static JSONArray preparedResponse;
	
	/*
	 * Initialize before each test
	 * */
	@BeforeClass
	public static void init() {
		context = new ClassPathXmlApplicationContext("beans.xml");
		dataReceiver = (DataReceiver) context.getBean("datareceiver");
		dataReceiver.openConnection();
	}
	
	/*
	 * Close after each test
	 * 
	 * Close the http connection
	 * 
	 * Close the context
	 * */
	@AfterClass
	public static void tearDown() {
		dataReceiver.closeConnection();
		context.close();	
	}
	

}
