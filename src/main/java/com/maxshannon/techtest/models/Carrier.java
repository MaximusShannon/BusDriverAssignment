package com.maxshannon.techtest.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * This class is the carrier bean.
 * 
 * It has a relationship of Many to one driver
 * 
 * Each carrier has one to many drivers.
 * 
 * JPA entities*/

@Entity
@Table
public class Carrier {
	
	@Id
	private int id;
	private int version;
	private String carrierName;
	
	/*
	 * This is used when persisting entities to insert the drivers id into this entity in the database
	 * */
	@ManyToOne
	private Driver driver;
	
	public Carrier() {}

	public Carrier(int id, int version, String carrierName) {
		super();
		this.id = id;
		this.version = version;
		this.carrierName = carrierName;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/*Custom toString method to compare records for database updating*/
	
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		result.append(id + "" + carrierName + version);
		
		return result.toString();
	}
	
	
	
}
