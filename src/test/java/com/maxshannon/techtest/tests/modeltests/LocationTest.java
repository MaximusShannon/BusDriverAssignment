package com.maxshannon.techtest.tests.modeltests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.maxshannon.techtest.models.Location;

public class LocationTest extends EntityData {
	
	
	/*
	 * Test makes sure the location setters are working
	 * these properties are set in the inherited class
	 *  */
	@Test
	public void testLocationSetters() {
		
		assertEquals("1123Dublin1", location.toString());
	}
	
	/*
	 * Properties are initialized in the inherited class
	 * so it this test checks that the values have actually been inserted.
	 * */
	@Test
	public void testLocationGetters() {
		
		assertEquals(1, location.getId());
		assertEquals(123, location.getLocationId());
		assertEquals("Dublin", location.getLocationName());
		assertEquals(1, location.getVersion());
	}
	
	/*
	 * Tests the driver constructor to make sure it constructs a POJO
	 * */
	@Test
	public void testLocationConstructor() {
		
		Location location2 = new Location(1, 123, "Wicklow", 1);
		
		assertEquals("1123Wicklow1", location2.toString());

	}

}
