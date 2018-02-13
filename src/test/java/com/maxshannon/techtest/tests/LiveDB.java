package com.maxshannon.techtest.tests;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.maxshannon.techtest.DatabaseInteractionService;

public class LiveDB {

	protected static EntityManagerFactory emf;
	protected static EntityManager em;
	protected static ClassPathXmlApplicationContext context;
	protected static DatabaseInteractionService dbService;
	protected static JSONArray response;
	
	@BeforeClass
	public static void init() throws FileNotFoundException, SQLException{
		emf = Persistence.createEntityManagerFactory("BusDetails_JPA_TEST");
		em = emf.createEntityManager();
		context = new ClassPathXmlApplicationContext("beans.xml");
		dbService = (DatabaseInteractionService) context.getBean("databaseinteractionservice");
	}
	
	
	@AfterClass
	public static void tearDown() {
		
		em.clear();
		em.close();
		emf.close();
		context.close();
	}
}
