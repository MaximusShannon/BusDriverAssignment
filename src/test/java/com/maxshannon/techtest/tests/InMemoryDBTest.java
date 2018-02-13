package com.maxshannon.techtest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.maxshannon.techtest.models.Carrier;
import com.maxshannon.techtest.models.Driver;
import com.maxshannon.techtest.models.Location;

public class InMemoryDBTest extends MemoryDB {

	
	/*
	 * Test persistence to the in-memory database
	 * 
	 * Sets random values to driver, location and carrier
	 * 
	 * Once it persists them to the h2.database, it then tries to 
	 * find the first driver it inserts, if this comes back with a value
	 * it has inserted a record into the in-mem db which constitutes a successful
	 * test.
	 * 
	 *  
	 * */
	@Test
	public void testPersist() {
		
		em.getTransaction().begin();
		
		Driver driver; 
		Location location; 
		Carrier carrier; 
		
		for(int i = 1; i <= 3; i++) {
			
			driver = new Driver();
			location = new Location();
			carrier = new Carrier();
			
			driver.setDriverId(500);
			driver.setId(i);
			driver.setVersion(1);
			driver.setFirstName("Max");
			driver.setLastName("Shannon");
			driver.setMiddleInitial(""); // apparently middle_init, isn't of type string?
			driver.setOper_class("bus");
			
			location.setVersion(1);
			location.setId(i);
			location.setLocationId(600);
			location.setLocationName("Ireland");
			
			carrier.setVersion(1);
			carrier.setId(i);
			carrier.setCarrierName("Bus TransFers");
					
			driver.setLocation(location);
			driver.setCarrier(carrier);
			location.setDriver(driver);
			carrier.setDriver(driver);
			
			em.persist(driver);
			em.persist(location);
			em.persist(carrier);
			
			em.flush();			
		}		

		em.getTransaction().commit();
		
		Driver newDriver = new Driver();
		newDriver = em.find(Driver.class, 1);
		
		assertNotNull(newDriver);
	}

	/*
	 * Once the above test has run, and the objects have been persisted
	 * it should be able to get a driver from the database. if it returns 
	 * a driver then the test is successful
	 * 
	 * */
	@Test
	public void testRetreive() {
		
		Driver driver = em.find(Driver.class, 1);
		
		assertNotNull(driver);
		assertEquals("Max", driver.getFirstName());
		
	}
	
	/*
	 * Looks for a persisted object with the ID of 100,
	 * which doesn't exist so driver should be null
	 * 
	 * */
	@Test
	public void testRetrieve_failure(){
		
		Driver driver = em.find(Driver.class, 100);	
		assertEquals(null, driver);
	}
	

	/*
	 * Tests updating an entity within the database
	 * it changes the record within the database then
	 * makes sure the record has changed.
	 * */
	@Test
	public void testUpdateEntities() {
		
		Driver driver = em.find(Driver.class, 1);
		Location location = em.find(Location.class, 1);
		Carrier carrier = em.find(Carrier.class, 1);
		
		driver.setFirstName("Maximuss");
		location.setLocationName("Austria");
		carrier.setCarrierName("DRP");
		
		assertNotNull(driver);
		assertNotNull(location);
		assertNotNull(carrier);
		
		assertEquals("Maximuss", driver.getFirstName());
		assertEquals("Austria", location.getLocationName());
		assertEquals("DRP", carrier.getCarrierName());
	}
	
	
	/*
	 * Deletes the driver record with a ID: 2
	 * then tries to retrieve a driver with the id of 2
	 * which then it cannot find so therefore the record has been
	 * deleted
	 * */
	@Test
	public void testDeleteEntity() {
		
		Driver driver = em.find(Driver.class, 2);
		em.getTransaction().begin();
		
		em.remove(driver);
		em.getTransaction().commit();
		
		Driver newDriver = em.find(Driver.class, 2);
		assertEquals(null, newDriver);
	}

}
