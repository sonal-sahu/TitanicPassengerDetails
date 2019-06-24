package com.sonal.titanic.passenger.details.controller;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class TestController {

	@Test
	public void getHomePageTest() {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"http://localhost:8082/TitanicPassengerDetails/home.html?access_token=cd9c8410-de31-4f1c-96fd-4fac5205b2c1");
		try {
			HttpResponse response = client.execute(request);
			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}
			assertEquals(200, response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Important: Close the connect
			client.getConnectionManager().shutdown();
		}
	}

	@Test
	public void getSearchTest() {

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://localhost:8082/TitanicPassengerDetails/search?searchByGender=male");
		try {
			HttpResponse response = client.execute(request);
			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder textView = new StringBuilder();
			String line = "";
			HashMap<String, String> map = new HashMap<String, String>();
			while ((line = rd.readLine()) != null) {
				textView.append(line);
			}
			String text = new String(textView);
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(text);
			String statusMessage = (String) json.get("message");
			assertEquals("Data Found", statusMessage);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void getPassengersListInGridTest() {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"http://localhost:8082/TitanicPassengerDetails/getPassengers?searchByGender=male&filterscount=0&groupscount=0&sortdatafield=passenger_id&sortorder=asc&pagenum=0&pagesize=10&recordstartindex=0&recordendindex=10&filtervalue0=&filtercondition0=&filterdatafield0=&filteroperator0=&_=1561387281451");
		try {
			HttpResponse response = client.execute(request);
			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder textView = new StringBuilder();
			String line = "";
			String count = "";
			HashMap<String, String> map = new HashMap<String, String>();
			while ((line = rd.readLine()) != null) {
				textView.append(line);

			}
			String text = new String(textView);
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(text);
			long rows = (Long) json.get("totalRows");
			assertEquals(266, rows);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void addPassengerTest() {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://localhost:8082/TitanicPassengerDetails/addPassenger");
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("data[passenger_id]", "111"));
			nameValuePairs.add(new BasicNameValuePair("data[passenger_class]", "2"));
			nameValuePairs.add(new BasicNameValuePair("data[name]", "Sonal"));
			nameValuePairs.add(new BasicNameValuePair("data[sex]", "male"));
			nameValuePairs.add(new BasicNameValuePair("data[age]", "25"));
			nameValuePairs.add(new BasicNameValuePair("data[sib_sp]", "1"));
			nameValuePairs.add(new BasicNameValuePair("data[parch]", "0"));
			nameValuePairs.add(new BasicNameValuePair("data[ticket]", "2243r43"));
			nameValuePairs.add(new BasicNameValuePair("data[fare]", "2323"));
			nameValuePairs.add(new BasicNameValuePair("data[cabin]", "2sd"));
			nameValuePairs.add(new BasicNameValuePair("data[embarked]", "S"));
			nameValuePairs.add(new BasicNameValuePair("searchByGender", "female"));
			nameValuePairs.add(new BasicNameValuePair("filterValue", ""));
			nameValuePairs.add(new BasicNameValuePair("filterCondition", ""));
			nameValuePairs.add(new BasicNameValuePair("filterDataField", ""));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			assertEquals(200, response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void deletePassengerTest() {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://localhost:8082/TitanicPassengerDetails/deletePassenger");
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("row", "111"));
			nameValuePairs.add(new BasicNameValuePair("searchByGender", "female"));
			nameValuePairs.add(new BasicNameValuePair("filterValue", ""));
			nameValuePairs.add(new BasicNameValuePair("filterCondition", ""));
			nameValuePairs.add(new BasicNameValuePair("filterDataField", ""));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			assertEquals(200, response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updatePassengerTest() {

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"http://localhost:8082/TitanicPassengerDetails/updatePassenger?update=true&passenger_id=892&name=Kelly,%20%20Mr.%20Jam&age=34.5");
		try {
			HttpResponse response = client.execute(request);
			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}
			assertEquals(200, response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Important: Close the connect
			client.getConnectionManager().shutdown();
		}

	}
}
