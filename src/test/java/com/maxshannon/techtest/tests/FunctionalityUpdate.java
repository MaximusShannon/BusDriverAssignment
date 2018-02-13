package com.maxshannon.techtest.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.maxshannon.techtest.UpdateChecker;
import com.maxshannon.techtest.models.Carrier;
import com.maxshannon.techtest.models.Driver;
import com.maxshannon.techtest.models.Location;

public class FunctionalityUpdate {

	/*
	 * declare types needed by the test-case inherited by this class
	 * 
	 * This type of design would have been better used with the data-receiver to enable 
	 * maximum reuse of code
	 * 
	 * */	
	protected static UpdateChecker updCheck;
	protected static ClassPathXmlApplicationContext context;
	protected static EntityManagerFactory emf;
	protected static EntityManager em;	
	
	/*
	 * Initialize before each test
	 * */
	@BeforeClass
	public static void init() {
		context = new ClassPathXmlApplicationContext("beans.xml"); 
		updCheck = (UpdateChecker) context.getBean("updatechecker");
		emf = Persistence.createEntityManagerFactory("BusDetails_JPA");
		em = emf.createEntityManager();
		
	}
	
	/*
	 * Reset the database after each transaction so the data in the database wont trigger an update
	 * on next execution
	 * 
	 * close the entity-manager
	 * close the entity-manager-factory
	 * close the context
	 * */
	
	@AfterClass
	public static void tearDown() {
		
		em.getTransaction().begin();
		
		Driver updatedDriver = em.find(Driver.class, 1009);
		updatedDriver.setFirstName("Lee");

		Location updatedLocation = em.find(Location.class, 2009);
		updatedLocation.setLocationName("MEM");
		
		Carrier updatedCarrier = em.find(Carrier.class, 3009);
		updatedCarrier.setCarrierName("GLI");
		
		em.getTransaction().commit();
		
		em.clear();
		em.close();
		emf.close();
		context.close();

	}
	
}
