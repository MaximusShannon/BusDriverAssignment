package com.maxshannon.techtest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.json.JSONArray;


import com.maxshannon.techtest.models.Carrier;
import com.maxshannon.techtest.models.Driver;
import com.maxshannon.techtest.models.Location;

/*
 *This class handles the interaction with the database for inserting entities/persestence
 *and checking is the database populated
 * 
 * */

public class DatabaseInteractionService implements DBService {
	
	private JSONArray response;
	private DataReceiver dataReceiver;
	private EntityManagerFactory emFactory;
	private EntityManager entityManager;
	private Driver driver;
	private Location location;
	private Carrier carrier;
	private List<String> resultList;
	
	/*DatabaseInteractionService has dependencies of type dataReceiver, driver, location and carrier.*/
	public DatabaseInteractionService(DataReceiver dataReceiver, Driver driver, Location location, Carrier carrier) {
		this.dataReceiver = dataReceiver;
		this.driver = driver;
		this.location = location;
		this.carrier = carrier;
	}
	
	
	/*
	 * This method opens a connection to the endpont
	 * Gets the response from the endpoint, prepares it and closes the connection to the endpoint;
	 * */
	public void getData() {
		
		dataReceiver.openConnection();
		dataReceiver.receiveResponse();
		dataReceiver.closeConnection();
		response = dataReceiver.getPreparedResponse();
	}
	
	/*
	 * This method checks if there is any results in the database.
	 * 
	 * Again, design could have been changes a bit, to inherit the enitityManage and Factory
	 * 
	 * If there are results it returns true, and if there aren't it returns false. Which are using by the main method
	 * to determine what to do.
	 * 
	 * 
	 * */
	
	public boolean isDatabasePopulated() {
		
		boolean isPopulated = false;
		
		emFactory = Persistence.createEntityManagerFactory("BusDetails_JPA");
		entityManager = emFactory.createEntityManager();
		
		Query sizeCheck = entityManager.createNativeQuery("SELECT * FROM Driver");
		resultList = sizeCheck.getResultList();
		
		if(resultList.size() > 0 ) {
			isPopulated = true;
		}
		
		entityManager.close();
		return isPopulated;
	}
	
	
	/*
	 * This method is called if the database is not populated
	 * 
	 * it loops through the response JSONArray, and used it to to populate a driver bean, location bean and carrier bean
	 * and then persist them to the database
	 * 
	 * 
	 * Had an issue with middle initials, which is not of type String apparently, but JSON doesn't support Char types?
	 * */
	public void insertEntities() {
		
		int id = 1;
		int count = 0;
		
		int entityListSize = response.length();
		emFactory = Persistence.createEntityManagerFactory("BusDetails_JPA");
		entityManager = emFactory.createEntityManager();
		entityManager.getTransaction().begin();
				
		for(int i = 0; i < entityListSize; i++) {
				
			driver.setDriverId(response.getJSONObject(i).getInt("oper_nbr"));
			driver.setId(id + 1000);
			driver.setVersion(1);
			driver.setFirstName(capatalizeString(response.getJSONObject(i).getString("first_name")));
			driver.setLastName(capatalizeString(response.getJSONObject(i).getString("last_name")));
			driver.setMiddleInitial(""); // apparently middle_init, isn't of type string?
			driver.setOper_class(response.getJSONObject(i).getString("oper_class"));
			
			location.setVersion(1);
			location.setId(id + 2000);
			location.setLocationId(response.getJSONObject(i).getInt("home_loc_6"));
			location.setLocationName(response.getJSONObject(i).getString("home_loc_3"));
			
			carrier.setVersion(1);
			carrier.setId(id + 3000);
			carrier.setCarrierName(response.getJSONObject(i).getString("carrier_cd"));
			
			/*set each object, which in the database corresponds to each foreign key*/
			driver.setLocation(location);
			driver.setCarrier(carrier);
			location.setDriver(driver);
			carrier.setDriver(driver);
			
			entityManager.persist(driver);
			entityManager.persist(location);
			entityManager.persist(carrier);
			
			/*Flush the entityManager so the same bean isn't inserted twice*/
			
			entityManager.flush();
			entityManager.clear();			
			id++;
			count++;
		}
		
		entityManager.getTransaction().commit();
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
	 * Reponse getter, used for testing purposes
	 * */
	public JSONArray getResponse() {
		return response;
	}	
	
	
}
