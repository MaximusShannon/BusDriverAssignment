package com.maxshannon.techtest.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;


public class DataReceiverTest extends DataTesting {
	
	/*
	 * Test to make sure the the data-receiver is getting the response
	 * */
	@Test
	public void receiveRepsonse_sucess() {
		dataReceiver.openConnection();
		dataReceiver.receiveResponse();
		preparedResponse = dataReceiver.getPreparedResponse();
		
		assertEquals(314, preparedResponse.getJSONObject(0).getInt("oper_nbr"));
	}
	
	/*
	 * Test to make sure the response is trimmed properly using a placeholder string
	 * */
	@Test
	public void trimString_success() {
		
		String toTrim = "Da,  da  , da  ";
		String trimmed = dataReceiver.trimResponse(toTrim);
		
		assertEquals("Da,da,da", trimmed);
	}
	
	/*
	 * Test to make sure the data-receiver connected to the endpoint successfully
	 * */
	@Test
	public void testConnection_sucess() throws IOException {
		
		//200, status ok
		assertEquals(200, dataReceiver.getConn().getResponseCode());
	}
	
}
