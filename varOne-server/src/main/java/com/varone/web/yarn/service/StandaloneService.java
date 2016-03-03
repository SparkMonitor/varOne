package com.varone.web.yarn.service;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.varone.conf.VarOneConfiguration;
import com.varone.server.VarOneServer;
import com.varone.web.util.VarOneEnv;



public class StandaloneService extends AbstractDeployModeService {
	private static final Logger LOG = Logger.getLogger(VarOneServer.class);
	
	private HttpClient client = new HttpClient();
	private String httpURLRoot;
	
	
	public StandaloneService(Configuration config){
		VarOneEnv varOneEnv = new VarOneEnv(VarOneConfiguration.create());
		
		String sparkMasterIP = varOneEnv.getSparkMasterIP();
		String sparkMasterWebUIPort = varOneEnv.getSparkMasterWebUIPort();
		if(sparkMasterIP == null ||  sparkMasterWebUIPort == null){
			throw new RuntimeException("spark-env.sh file not setting SPARK_MASTER_IP and SPARK_MASTER_WEB_PORT environment variable");
		}
		this.httpURLRoot = "http://" + sparkMasterIP + ":" + sparkMasterWebUIPort;
	}
	
	public List<String> getRunningSparkApplications() throws IOException{
		return this.getRestfulApplications();
	}
	
	public boolean isStartRunningSparkApplication(String id) throws IOException{
		return this.getRestfulApplications().contains(id);
	}
	
	public List<String> getSparkApplicationsByPeriod(long start, long end) throws YarnException, IOException {
		List<String> lists = new ArrayList<String>();
		return lists;
	}
	
	@Override
	public void close() throws IOException {
		//Nothing
	}
	
	private List<String> getRestfulApplications() throws IOException{
		GetMethod method = new GetMethod(httpURLRoot + "/api/v1/applications");
		this.client.executeMethod(method);
		byte[] resultByte = method.getResponseBody(1024 * 1024 * 10);
		String result = new String(resultByte);
		ApplicationsBean applications[] = this.jsonconvertToApplicationsBean(result);
		
		List<String> applicationList = new ArrayList<String>();
		for(ApplicationsBean application : applications){
			List<Attempt> attempts = application.getAttempts();
			for(Attempt attempt : attempts){
				if(attempt.isCompleted() == false){
					applicationList.add(application.getId());
					break;
				}
			}
		}
		return applicationList;
	}
	
	private ApplicationsBean[] jsonconvertToApplicationsBean(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, ApplicationsBean[].class);
	}
	
	public class ApplicationsBean {
		private String id;
		private String name;
		private List<Attempt> attempts;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Attempt> getAttempts() {
			return attempts;
		}
		public void setAttempts(List<Attempt> attempts) {
			this.attempts = attempts;
		}
	}
	public class Attempt {
		private String startTime;
		private String endTime;
		private String sparkUser;
		private boolean completed;
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public String getSparkUser() {
			return sparkUser;
		}
		public void setSparkUser(String sparkUser) {
			this.sparkUser = sparkUser;
		}
		public boolean isCompleted() {
			return completed;
		}
		public void setCompleted(boolean completed) {
			this.completed = completed;
		}
	}
}
