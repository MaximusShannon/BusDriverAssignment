package com.maxshannon.techtest;

import java.util.ArrayList;

import com.maxshannon.techtest.models.Carrier;
import com.maxshannon.techtest.models.Driver;
import com.maxshannon.techtest.models.Location;

public interface Checker {
	
	public void getResponse();
	
	public void seperateResponse();
	
	public void getDatabaseLists();
	
	public void checkForUpdates();
	
	public void update(ArrayList<Driver> driverList, ArrayList<Location> locationList, ArrayList<Carrier> carrierList);

}
