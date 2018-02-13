package com.maxshannon.techtest.tests.modeltests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.maxshannon.techtest.models.Carrier;
import com.maxshannon.techtest.models.Driver;
import com.maxshannon.techtest.models.Location;

public class EntityData {

	/*
	 * Declare types needed by class that inherits this class
	 * */
	protected static Driver driver;
	protected static Location location;
	protected static Carrier carrier;	
	protected static ClassPathXmlApplicationContext context;

	/*
	 * Initialize the above types so the class that inherits these can use them correctly
	 * */
	@BeforeClass
	public static void init() {
		context = new ClassPathXmlApplicationContext("beans.xml"); 
		driver = (Driver) context.getBean("driver");
		location = (Location) context.getBean("location");
		carrier = (Carrier) context.getBean("carrier");

		/*Initialize values into the driver, location and carrier bean*/
		driver.setFirstName("Max");
		driver.setId(1);
		driver.setDriverId(123);
		driver.setLastName("Shannon");
		driver.setMiddleInitial("P");
		driver.setOper_class("JHK");
		driver.setVersion(1);
		
		location.setId(1);
		location.setLocationId(123);
		location.setLocationName("Dublin");
		location.setVersion(1);
		
		carrier.setCarrierName("GLR");
		carrier.setId(1);
		carrier.setVersion(1);
	}

	/*Close application context*/
	@AfterClass
	public static void tearDown() {
		context.close();
	}




}
