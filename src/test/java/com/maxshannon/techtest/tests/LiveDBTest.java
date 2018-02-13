package com.maxshannon.techtest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LiveDBTest extends LiveDB {
	
	@Test
	public void testGetData() {
		
		dbService.getData();
		response = dbService.getResponse();
		
		assertNotNull(response);
	}
	
	@Test
	public void testIsDatabasePopulated() {
		
		assertEquals(true, dbService.isDatabasePopulated());
	}
	
	@Test
	public void testDbServiceCapatilizeStrings() {
		
		String capatalized = dbService.capatalizeString("SHANNoN");
		
		assertEquals("Shannon", capatalized);
	}
	
	

}
