package com.varone.web.yarn.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.hadoop.yarn.exceptions.YarnException;

import com.google.gson.Gson;
import com.varone.web.util.VarOneEnv;



public class StandaloneService extends AbstractDeployModeService {
	private HttpClient client = new HttpClient();
	private String httpURLRoot;
	
	
	public StandaloneService(VarOneEnv varOneEnv){
		this.httpURLRoot = varOneEnv.getSparkMasterRestfulURL();
	}
	
	@Override
	public List<String> getRunningSparkApplications() throws IOException{
		return this.getRestfulRunningApplications();
	}
	
	@Override
	public boolean isStartRunningSparkApplication(String id) throws IOException{
		return this.getRestfulRunningApplications().contains(id);
	}
	
	@Override
	public List<String> getSparkApplicationsByPeriod(long start, long end) throws YarnException, IOException {
		List<String> applicationIds = new ArrayList<String>();

		ApplicationsBean applications[] = getRestfulAllApplicationsBeanArray();
		for(ApplicationsBean application : applications){
			List<Attempt> attempts = application.getAttempts();
			for(Attempt attempt : attempts){
				long reportStartTime = dateToTimestamp(attempt.getStartTime());
				long reportFinishTime = dateToTimestamp(attempt.getEndTime());
				
				if((start <= reportStartTime && reportStartTime <= end) || 
						(start <= reportFinishTime && reportFinishTime <= end)){
					applicationIds.add(application.getId());
					break;
				}
				
			}
		}
		return applicationIds;
	}
	
	@Override
	public void close() throws IOException {
		//Nothing
	}
	
	private List<String> getRestfulRunningApplications() throws IOException{
		ApplicationsBean applications[] = getRestfulAllApplicationsBeanArray();
		
		List<String> applicationList = new ArrayList<String>();
		for(ApplicationsBean application : applications){
			if(application != null){
				List<Attempt> attempts = application.getAttempts();
				for(Attempt attempt : attempts){
					if(attempt.isCompleted() == false){
						applicationList.add(application.getId());
						break;
					}
				}
			}
		}
		return applicationList;
	}
	
	private ApplicationsBean[] getRestfulAllApplicationsBeanArray(){
		try{
			GetMethod method = new GetMethod(httpURLRoot + "/api/v1/applications");
			this.client.executeMethod(method);
			byte[] resultByte = method.getResponseBody(1024 * 1024 * 10);
			String result = new String(resultByte);
			return this.jsonconvertToApplicationsBean(result);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private ApplicationsBean[] jsonconvertToApplicationsBean(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, ApplicationsBean[].class);
	}

	private long dateToTimestamp(String dateTime){
		try{
			if(dateTime != null){
				SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
				Date date = dateFormatGmt.parse(dateTime);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				return c.getTimeInMillis();
			}else{
				return 0;
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
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
