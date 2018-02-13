package com.maxshannon.techtest.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
 * This class is the Driver bean.
 * 
 * It has two relationships between beans
 * 
 * Many to one location
 * 
 * and one to many carriers.
 * 
 * */

@Entity
@Table
@NamedQueries(value = {
		@NamedQuery(name = "Driver.getAll", query = "SELECT d FROM Driver d")
})
public class Driver {
	
	@Id
	private int id;					
	private int driverId;				
	private int version;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String oper_class;
	
	@ManyToOne
	private Location location;	
	@OneToOne
	private Carrier carrier;
	
	public Driver() {}

	public Driver(int id, int driverId, int version, String firstName,
			String middleInitial, String lastName, String oper_class) {
		super();
		this.id = id;
		this.driverId = driverId;
		this.version = version;
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.oper_class = oper_class;
	}
	
	

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOper_class() {
		return oper_class;
	}

	public void setOper_class(String oper_class) {
		this.oper_class = oper_class;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	//custom toString() due to bug with java-9
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		result.append(id + "" + driverId+version+firstName+middleInitial+lastName+oper_class);	
		return result.toString();
		
	}
}
