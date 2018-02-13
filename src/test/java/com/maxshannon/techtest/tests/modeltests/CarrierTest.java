package com.maxshannon.techtest.tests.modeltests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.maxshannon.techtest.models.Carrier;


public class CarrierTest extends EntityData {
	
	/*
	 * Test makes sure the carriers setters are working
	 * these properties are set in the inherited class
	 *  */
	@Test
	public void testCarrierSetters() {
		
		assertEquals("1GLR1", carrier.toString());
	}

	/*
	 * Properties are initialized in the inherited class
	 * so it this test checks that the values have actually been inserted.
	 * */
	@Test
	public void testCarrierGetters() {
		
		assertEquals(1, carrier.getId());
		assertEquals(1, carrier.getVersion());
		assertEquals("GLR", carrier.getCarrierName());
	}
	
	/*
	 * Tests the carrier constructor to make sure it constructs a POJO
	 * */
	@Test
	public void testCarrierConstructor() {
		
		Carrier carrier = new Carrier(1, 1, "GLR");
		assertEquals("1GLR1", carrier.toString());
	}
}
