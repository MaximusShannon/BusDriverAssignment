package com.maxshannon.techtest.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.maxshannon.techtest.models.Carrier;
import com.maxshannon.techtest.models.Driver;
import com.maxshannon.techtest.models.Location;

public class UpdaterTest extends FunctionalityUpdate {
	
	/*
	 * Makes sure the capatilizeString() method returns the correct value
	 * 
	 * It gets MAX, and returns Max
	 * */
	@Test
	public void testCapatilizeString() {
		
		String nameCap = updCheck.capatalizeString("MAX");
		assertEquals("Max", nameCap);
	}
	
	/*
	 * Makes sure the methods getting the driverListsFromDatabase - locationListFromDatabase,
	 * carrierListFromDatabase are populated after it calls .getDatabaseLists();
	 * */
	@Test
	public void testGetDatabaseLists() {
		
		updCheck.getDatabaseLists();
		
		assertEquals(314, updCheck.getDriverListFromDatabase().get(0).getDriverId());
		assertEquals("MEM", updCheck.getLocationListFromDatabase().get(0).getLocationName());
		assertEquals("GLI", updCheck.getCarrierListFromDatabase().get(0).getCarrierName());
	}
	
	/*
	 * Makes sure the response the updCheck gets is split into its correct
	 * ArrayLists
	 * */
	@Test
	public void testSeperateReponse() {
		
		updCheck.getResponse();
		updCheck.seperateResponse();
		
		assertEquals(314, updCheck.getDriverListFromResponse().get(0).getDriverId());
		assertEquals("MEM", updCheck.getLocationListFromResponse().get(0).getLocationName());
		assertEquals("GLI", updCheck.getCarrierListFromResponse().get(0).getCarrierName());
	}
	
	/*
	 * Tests the live database, gets an object from the database
	 * 
	 * updates a field within each object,
	 * 
	 * commits the transaction
	 * 
	 * then gets the current databaselists + the currentResponse
	 * 
	 * and checks them vs the current response, technically it should need an update because
	 * 
	 * the values have changed and then run the update() method because these recors have changed.
	 * */
	@Test
	public void testUpdate() {
		
		em.getTransaction().begin();
		
		Driver updatedDriver = em.find(Driver.class, 1009);
		updatedDriver.setFirstName("Max");

		Location updatedLocation = em.find(Location.class, 2009);
		updatedLocation.setLocationName("Dublin");
		
		Carrier updatedCarrier = em.find(Carrier.class, 3009);
		updatedCarrier.setCarrierName("BusDublin");
		
		em.getTransaction().commit();
		
		updCheck.getDatabaseLists();
		updCheck.getResponse();
		updCheck.seperateResponse();
		updCheck.checkForUpdates();	
		
		//the record i update is in the 9th index
		assertEquals("Max", updCheck.getDriverListFromDatabase().get(8).getFirstName());
		assertEquals("Dublin", updCheck.getLocationListFromDatabase().get(8).getLocationName());
		assertEquals("BusDublin", updCheck.getCarrierListFromDatabase().get(8).getCarrierName());
	
	}
}
