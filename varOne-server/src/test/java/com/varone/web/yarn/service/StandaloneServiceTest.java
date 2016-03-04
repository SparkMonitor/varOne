package com.varone.web.yarn.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import com.google.gson.Gson;
import com.varone.conf.VarOneConfiguration;
import com.varone.web.util.VarOneEnv;
import com.varone.web.yarn.service.StandaloneService.ApplicationsBean;

public class StandaloneServiceTest {
	private String restfulURL = "http://server-a3:8080/api/v1/applications";
	
	@Test
	public void testhttpconnection() {
		try {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(restfulURL);
			int status = client.executeMethod(method);
			assertEquals(200, status);
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	@Test
	public void restfulTest() throws Exception{
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(restfulURL);
		int status = client.executeMethod(method);
		byte[] resultByte = method.getResponseBody(1024 * 1024 * 10);
		String result = new String(resultByte);
		
		
		Gson gson = new Gson();
		ApplicationsBean []applications = gson.fromJson(result, ApplicationsBean[].class);
		assertEquals(200, status);
		assertTrue(applications.length > 0);
	}
	@Test
	public void datetest(){
		try{
			String dateTime = "2016-03-02T07:13:45.843GMT";
			SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
			Date date = dateFormatGmt.parse(dateTime);
			assertEquals("Wed Mar 02 15:13:45 CST 2016", date.toString());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
