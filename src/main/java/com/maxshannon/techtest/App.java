package com.maxshannon.techtest;

import org.springframework.context.support.ClassPathXmlApplicationContext;


/*
 * AUTHOR: Max Shannon
 * Version: 1.2
 * 
 * 
 * This project was done as a techtest from Utrack.
 * 
 * 
 * */


public class App {

	public static void main( String[] args ){

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");  	
		
		/*Get beans and inject their dependencies from src/main/resources/beans.xml*/
		
		DatabaseInteractionService dbInteraction = (DatabaseInteractionService) context.getBean("databaseinteractionservice");
		UpdateChecker updaterService = (UpdateChecker) context.getBean("updatechecker");
		
		/*
		 * Checks if the database populated then gets the most recent response from the endpoint.
		 * 
		 * It then separates that response into 3 ArrayList of type Driver, Location and Carrier,
		 * it then gets a list from the database and separates it into 3 ArrayLists of type Driver,
		 * Location and Carrier.
		 * 
		 * It then compares these lists against each other to see if any records in the database have changed
		 * and then calls the updaterService.update();
		 * 
		 * The update method receives 3 @params, which are 3 ArrayLists, which will only be populated if an
		 * update is required. 
		 * */
		if(dbInteraction.isDatabasePopulated()) {
			
			System.out.println("Database populated.. checking for updates...");
			
			updaterService.getResponse();
			updaterService.seperateResponse();
			updaterService.getDatabaseLists();
			updaterService.checkForUpdates();
		}else {
			
			/*
			 * If the database is empty, it gets the data from the endpoint, and inserts it into the database after
			 * cleaning it and trimming it.
			 * */
			System.out.println("Creating Database....");
			
			dbInteraction.getData();
			dbInteraction.insertEntities();
		}

		/*Close the applicatonContext*/
		context.close();       
	}    
}
