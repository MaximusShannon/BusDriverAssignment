package com.maxshannon.techtest.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * This is the location bean
 * 
 * it has a relationship of 
 * 
 * Many to one driver
 * 
 * */

@Entity
@Table
public class Location {
	
	@Id
	private int id;
	private int locationId;
	private String locationName;
	private int version;
	
	@ManyToOne
	private Driver driver;
	
	public Location() {}

	public Location(int id, int locationId, String locationName, int version) {
		super();
		this.id = id;
		this.locationId = locationId;
		this.locationName = locationName;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	
	/*Custom toString to compare records for updating
	 * 
	 * Also java9 has a bug where it wont generate a toString()*/
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		result.append(id + "" + locationId + locationName + version);
		
		return result.toString();
	}
	
	
}
