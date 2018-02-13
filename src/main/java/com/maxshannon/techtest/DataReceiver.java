package com.maxshannon.techtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 * This class handles all the interaction between the endpoint
 * 
 * It opens connections between the endpoint and app
 * */

public class DataReceiver implements RequestService {
	
	private String appUrl;
	private URL url;
	private int retryCount;
	private int timeoutLength;
	private HttpURLConnection conn;
	private BufferedReader br;
	private JSONArray preparedResponse;
	
	public DataReceiver() {
		
		
	}
	
	/*
	 * opens a connection the a URL that is defined in the application.properties file.
	 *  -> https://pegasus.greyhound.com/busTracker/dispatch/driverBusAssignment
	 *  
	 *  sets the request method to GET
	 *  accepts application/json type
	 *  
	 *  connection timeout length is also defined in application.properties
	 *  -> 60000 miliseconds
	 * */
	public void openConnection() {
			
		try {
			url = new URL(appUrl);		
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setConnectTimeout(timeoutLength);
			br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method reads the response into a string
	 * and passes it to the prepareResponseForDatabaseInteraction(which is passed a value returned from the trimReponse(response) )
	 * 
	 *  .
	 * */

	public void receiveResponse() {
		
		String response;
		try {
			response = br.readLine();		
			prepareResponseForDatabaseInteraction(trimResponse(response));
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	/* 
	 * This method prepares the response from the server creating a JSON Object
	 * and inserting that object into the prepared response array allowing it to be
	 * easily used to create and persist the entities into the database
	 * 
	 * */
	public void prepareResponseForDatabaseInteraction(String response) {
		
		JSONObject jsonResponse = new JSONObject(response);
		preparedResponse = jsonResponse.getJSONArray("results");
	}
	
	/*
	 * This method trims all the whitespace from before, after and inside the records
	 * it does this by replacing all the whitespace with "" 
	 * 
	 * */
	
	public String trimResponse(String response) {
		
		String trimmedResponse;
		trimmedResponse = response.replaceAll("\\s+", "");
		
		return trimmedResponse;
	}	
	
	/*getters and setting used for testing and dependency injection*/

	public JSONArray getPreparedResponse() {
		return preparedResponse;
	}

	public HttpURLConnection getConn() {
		return conn;
	}

	public void closeConnection() {
		conn.disconnect();
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void setTimeoutLength(int timeoutLength) {
		this.timeoutLength = timeoutLength;
	}
}
