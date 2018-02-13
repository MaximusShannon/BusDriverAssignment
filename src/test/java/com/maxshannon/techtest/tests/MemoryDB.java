package com.maxshannon.techtest.tests;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class MemoryDB {

	/*
	 * Declare types needed by the class inherited from this class
	 * */
	protected static EntityManagerFactory emf;
	protected static EntityManager em;
	/*
	 * Initialize types needed by the class inherited
	 * */
	@BeforeClass
	public static void init() throws FileNotFoundException, SQLException{
		emf = Persistence.createEntityManagerFactory("BusDetails_JPA_TEST");
		em = emf.createEntityManager();
	}
	/*
	 * Initilize the in memory database.
	 * 
	 * Drop all the tables to allow isolated tests
	 * 
	 * execute the create.sql, which creates 3 tables and enables foreign key constraints
	 * 
	 * create.sql is location in the same package as this class
	 * */
	@Before
	public void initilizeDb() throws FileNotFoundException, SQLException{
		
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/inmemdb", "test", "test");
			Statement st = conn.createStatement();
			
			st.execute("DROP TABLE DRIVER");
			st.execute("DROP TABLE LOCATION");
			st.execute("DROP TABLE CARRIER");
			
			File script = new File(getClass().getResource("create.sql").getFile());
			RunScript.execute(conn, new FileReader(script));
			
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * after tests tear-down the assests used
	 * 
	 * entity-manager close
	 * entity-manager-factory close
	 * */
	@AfterClass
	public static void tearDown() {
		
		em.clear();
		em.close();
		emf.close();
	}
	
}
