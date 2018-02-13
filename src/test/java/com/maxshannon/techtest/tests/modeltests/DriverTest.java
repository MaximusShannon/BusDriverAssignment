package com.maxshannon.techtest.tests.modeltests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.maxshannon.techtest.models.Driver;

public class DriverTest extends EntityData {

	/*
	 * Test makes sure the driver setters are working
	 * these properties are set in the inherited class
	 *  */
	@Test
	public void testDriverSetters() {
		
		assertEquals("11231MaxPShannonJHK", driver.toString());	
	}
	
	/*
	 * Properties are initialized in the inherited class
	 * so it this test checks that the values have actually been inserted.
	 * */
	@Test
	public void testDriverGetters() {
		
		assertEquals("Max", driver.getFirstName());
		assertEquals("Shannon", driver.getLastName());
		assertEquals(1, driver.getId());
		assertEquals(123, driver.getDriverId());
		assertEquals("JHK", driver.getOper_class());
		assertEquals(1, driver.getVersion());
		assertEquals("P", driver.getMiddleInitial());
		
	}
	
	/*
	 * Tests the driver constructor to make sure it constructs a POJO
	 * */
	@Test
	public void testDriverConstructor() {
		
		Driver driver2 = new Driver(1, 2, 3, "Jack", "P", "Power", "NBR");
		assertEquals("123JackPPowerNBR", driver2.toString());
	}
}
