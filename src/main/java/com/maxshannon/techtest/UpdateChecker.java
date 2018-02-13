package com.maxshannon.techtest;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.json.JSONArray;


import com.maxshannon.techtest.models.Carrier;
import com.maxshannon.techtest.models.Driver;
import com.maxshannon.techtest.models.Location;

/*This class handles all the functionality of getting the most current response from the endpoint
 * creating lists based on this endpoint.
 * - driverListFromResponse
 * -locationListFromResponse
 * -carrierListFromResponse
 * 
 * It is also responsible for getting the latest database records and sorting them into 3 corresponding
 * lists.
 * -driverListFromDatabase
 * -locationListFromDatabase
 * -carrierListFromDatabase
 * 
 * 
 * */

public class UpdateChecker implements Checker {
	
	
	private ArrayList<Driver> driverListFromDatabase;
	private ArrayList<Driver> driverListFromResponse;
	private ArrayList<Location> locationListFromDatabase;
	private ArrayList<Location> locationListFromResponse;
	private ArrayList<Carrier> carrierListFromDatabase;
	private ArrayList<Carrier> carrierListFromResponse;	
	private ArrayList<Driver> updateableDrivers;
	private ArrayList<Location> updateableLocations;
	private ArrayList<Carrier> updateableCarriers;
	private JSONArray response;
	private DataReceiver dataReceiver;
	private Driver driver;
	private Location location;
	private Carrier carrier;
	private EntityManagerFactory emFactory;
	private EntityManager entityManager;


	
	public UpdateChecker(DataReceiver dataReceiver, Driver driver, Location location, Carrier carrier) {
		this.dataReceiver = dataReceiver;
		this.driver = driver;
		this.location = location;
		this.carrier = carrier;
	}
	
	/*
	 * In high-n-sight, i could have designed this part a little better, I should have inherited the properties
	 * that gave me the response, and anything to do with the data being received. As this code is used multiple times
	 * across the application.
	 * 
	 * UpdateChecker -> Inherits from -> DataReceiver
	 * 
	 * Due to time constraints I cannot refactor this.
	 * 
	 * 
	 * response = dataReceiver.getPreparedReponse() converts the response to a JSONArray so it is easily interpreted
	 * */
	public void getResponse() {
		dataReceiver.openConnection();
		dataReceiver.receiveResponse();
		dataReceiver.closeConnection();
		response = dataReceiver.getPreparedResponse();
	}
	
	/*
	 * This method gets the the size of each table in the database and loops to collect each record
	 * from the database and store it in an ArrayList from comparing against the most recent endpoint
	 * response.
	 * 
	 * 
	 * */
	public void getDatabaseLists() {
		
		int sizeOfTable;
		Query getTableSize; 
		Long tableSize;
		
		/*This is also reused multiple times so it should be inherited as a static type.*/
		emFactory = Persistence.createEntityManagerFactory("BusDetails_JPA");
		entityManager = emFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		/*Get the table size for the loop*/
		getTableSize = entityManager.createNativeQuery("SELECT COUNT(*) FROM Driver");
		tableSize =  (Long) getTableSize.getSingleResult();
		sizeOfTable = Integer.parseInt(tableSize.toString());
		
		driverListFromDatabase = new ArrayList<Driver>();
		locationListFromDatabase = new ArrayList<Location>();
		carrierListFromDatabase = new ArrayList<Carrier>();
				
		for(int i = 1; i <= sizeOfTable; i++) {
			
			driver = entityManager.find(Driver.class, i+1000);
			driverListFromDatabase.add(driver);
			
			location = entityManager.find(Location.class, i+2000);
			locationListFromDatabase.add(location);
			
			carrier = entityManager.find(Carrier.class, i+3000);
			carrierListFromDatabase.add(carrier);
		}
		entityManager.close();
	}
	
	/*
	 * This method changes strings that is sent in
	 * 
	 * @param the name that is passed: Example: mAx -> after method Max
	 * 
	 * Or exAMPLE -> Example
	 * */
	public String capatalizeString(String namePassed) {
		
		String loweredCasedName = namePassed.toLowerCase();
		String capatalizedName = loweredCasedName.substring(0, 1).toUpperCase() + loweredCasedName.substring(1);
		
		return capatalizedName;
	}	
	
	
	/* 
	 * This method instantiates 3 Lists
	 * 
	 * It then gets the response (from the endpoint) -> which is of type JSONArray
	 * 
	 * It splits the response and uses each JSONObject in the array to create new Driver, Location
	 * and Carrier objects
	 * 
	 * It then adds each object to a list, to compare against the database lists.
	 * 
	 * ISSUE: /bug the middle_init in the response was apparently not of type String, and
	 * i thought there was only String type in JSON and not char?
	 * 
	 * 
	 * */
	public void seperateResponse() {
		
		int id = 1;
		driverListFromResponse = new ArrayList<Driver>();
		locationListFromResponse = new ArrayList<Location>();
		carrierListFromResponse = new ArrayList<Carrier>();
		
		for(int i = 0; i < response.length(); i++) {
			
			driver = new Driver();
				
			driver.setDriverId(response.getJSONObject(i).getInt("oper_nbr"));
			driver.setId(id + 1000);
			driver.setVersion(1);
			driver.setFirstName(capatalizeString(response.getJSONObject(i).getString("first_name")));
			driver.setLastName(capatalizeString(response.getJSONObject(i).getString("last_name")));
			driver.setMiddleInitial(""); // apparently middle_init, isn't of type string?
			driver.setOper_class(response.getJSONObject(i).getString("oper_class"));
			
			driverListFromResponse.add(driver);
			id++;
		}
		
		/*Reset the id so each list can start at: 1001, 2001, 3001 etc.*/
		id = 1;
		
		for (int i = 0; i < response.length(); i++) {
			
			location = new Location();
			
			location.setVersion(1);
			location.setId(id + 2000);
			location.setLocationId(response.getJSONObject(i).getInt("home_loc_6"));
			location.setLocationName(response.getJSONObject(i).getString("home_loc_3"));
			
			locationListFromResponse.add(location);
			id++;
		}
		/*Reset the id so each list can start at: 1001, 2001, 3001 etc*/
		id = 1;
		for(int i = 0; i < response.length(); i++) {
			
			carrier = new Carrier();
			
			carrier.setVersion(1);
			carrier.setId(id + 3000);
			carrier.setCarrierName(response.getJSONObject(i).getString("carrier_cd"));
			
			carrierListFromResponse.add(carrier);
			id++;
		}
	}
	
	/*
	 * This method compares the databaseLists and the reponseLists against each other
	 * 
	 * using a custom toString method, instead of comparing each and every field, which would add
	 * alot more comparisons to the application i just check the strings against each other.
	 * 
	 * Example : 12345maxShannonGLIXpS .equals 12345maxShannonHIX2ps
	 * -> false;
	 * 
	 *  
	 * If they don't match, for the corresponding type - i add them to an updateAbleList which is then
	 * passed to the update(); method, at the end of this method.
	 * 
	 * Negative impact about this way is: I don't know which field needs to be updated, so I update
	 * them all.
	 * 
	 * If an update is needed, it increments an updateable counter.
	 * */
	
	public void checkForUpdates() {		
		
		int updateable = 0;
		
		updateableDrivers = new ArrayList<Driver>();
		updateableLocations = new ArrayList<Location>();
		updateableCarriers = new ArrayList<Carrier>();
		
		for(int i = 0; i < driverListFromResponse.size(); i++) {
			
			if(!driverListFromResponse.get(i).toString()
					.equals(driverListFromDatabase.get(i).toString())) {
				
				updateable++;
				updateableDrivers.add(driverListFromDatabase.get(i));
			}		
			if(!locationListFromResponse.get(i).toString()
					.equals(locationListFromDatabase.get(i).toString())) {
				
				updateable++;
				updateableLocations.add(locationListFromDatabase.get(i));
			}
			if(!carrierListFromResponse.get(i).toString()
					.equals(carrierListFromDatabase.get(i).toString())) {
				
				updateable++;
				updateableCarriers.add(carrierListFromDatabase.get(i));
			}			
		}
		
		if(updateable > 0) {
			System.out.println("Updates needed: " + updateable + " Updating.....");
			update(updateableDrivers, updateableLocations, updateableCarriers);
			
		}else {			
			System.out.println("No updates are needed at this time....");
		}	
	}
	
	/*
	 * This method handles the updating to the database.
	 * 
	 * If updates are required, they are passed into this method, from this method then
	 * 
	 * It checks if the lists are null, if they are it moves on.
	 * 
	 * It then loops each list , to update what ever needs to be updated, but it updates all the fields,
	 * this is a downfall of the way i approached this.
	 * 
	 * I could have tried to keep track of what field was updated, and then only update that field.
	 * 
	 * @param driverList
	 * @param locationList
	 * @param carrierList
	 * 
	 * */

	public void update(ArrayList<Driver> driverList, 
			ArrayList<Location> locationList,
			ArrayList<Carrier> carrierList) {
		
		emFactory = Persistence.createEntityManagerFactory("BusDetails_JPA");
		entityManager = emFactory.createEntityManager();
		
		if(driverList.size() != 0) {

			entityManager.getTransaction().begin();
			
			for(int i = 0; i < driverList.size(); i++) {
				
				driver = entityManager.find(Driver.class, driverList.get(i).getId());
				driver.setDriverId(driverList.get(i).getDriverId());
				driver.setFirstName(driverList.get(i).getFirstName());
				driver.setLastName(driverList.get(i).getLastName());
				driver.setMiddleInitial("");
				driver.setOper_class(driverList.get(i).getOper_class());
				driver.setVersion(driver.getVersion() + 1);
			}	
			
			entityManager.getTransaction().commit();			
		}
		
		if(locationList.size() != 0) {
			
			entityManager.getTransaction().begin();
			
			for(int i = 0; i < locationList.size(); i++) {
				
				location = entityManager.find(Location.class, locationList.get(i).getId());
				location.setLocationId(locationList.get(i).getLocationId());
				location.setLocationName(locationList.get(i).getLocationName());
				location.setVersion(driver.getVersion() + 1);
			}
			
			entityManager.getTransaction().commit();
					
		}
		
		if(carrierList.size() != 0) {
			
			entityManager.getTransaction().begin();
			
			for(int i = 0; i < carrierList.size(); i++) {
				
				carrier = entityManager.find(Carrier.class, carrierList.get(i).getId());
				carrier.setCarrierName(carrierList.get(i).getCarrierName());
				carrier.setVersion(carrier.getVersion() + 1);
			}
			
			entityManager.getTransaction().commit();
			
		}
		
		entityManager.close();
		emFactory.close();		
	}
	
	
	/*Getters mostly used for testing purposes*/

	public ArrayList<Driver> getDriverListFromDatabase() {
		return driverListFromDatabase;
	}

	public ArrayList<Driver> getDriverListFromResponse() {
		return driverListFromResponse;
	}

	public ArrayList<Location> getLocationListFromDatabase() {
		return locationListFromDatabase;
	}

	public ArrayList<Location> getLocationListFromResponse() {
		return locationListFromResponse;
	}

	public ArrayList<Carrier> getCarrierListFromDatabase() {
		return carrierListFromDatabase;
	}

	public ArrayList<Carrier> getCarrierListFromResponse() {
		return carrierListFromResponse;
	}

	public ArrayList<Driver> getUpdateableDrivers() {
		return updateableDrivers;
	}

	public ArrayList<Location> getUpdateableLocations() {
		return updateableLocations;
	}

	public ArrayList<Carrier> getUpdateableCarriers() {
		return updateableCarriers;
	}
	
	
	
}
