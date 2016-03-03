package com.varone.web.yarn.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import com.google.gson.Gson;
import com.varone.conf.VarOneConfiguration;
import com.varone.web.util.VarOneEnv;
import com.varone.web.yarn.service.StandaloneService.ApplicationsBean;

public class StandaloneServiceTest {

	@Test
	public void testhttpconnection() {
		try {
			String httpURL = "http://server-a2:4040/api/v1/applications";
			
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(httpURL);
			int status = client.executeMethod(method);
		} catch(Exception e){
			
		}
	}
	@Test
	public void restfulTest() throws Exception{
		String httpURL = "http://server-a2:4040/api/v1/applications";
		
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(httpURL);
		int status = client.executeMethod(method);
		System.out.println(status);
		byte[] resultByte = method.getResponseBody(1024 * 1024 * 10);
		String result = new String(resultByte);
		
		
		Gson gson = new Gson();
		ApplicationsBean []applications = gson.fromJson(result, ApplicationsBean[].class);
		System.out.println(applications.length);
		for(ApplicationsBean application : applications){
			System.out.println(application.getId());
			System.out.println(application.getName());
			System.out.println(application.getAttempts().get(0).getStartTime());
		}
	}
	@Test
	public void applicationRunningTest() throws IOException {
		StandaloneService service = new StandaloneService(null);
		List<String> list = service.getRunningSparkApplications();
		assertTrue(list.size() > 0);
	}
	@Test
	public void isStartRunningSparkApplication() throws IOException {
		StandaloneService service = new StandaloneService(null);
		boolean isRunning = service.isStartRunningSparkApplication("HBase_Query_with_RDD");
		assertTrue(isRunning);
	}
	@Test
	public void sparkenvtest(){
		VarOneEnv env = new VarOneEnv(VarOneConfiguration.create());
		System.out.println(env.getSparkMasterIP());
		System.out.println(env.getSparkMasterWebUIPort());
	}

}
