package com.varone.web.yarn.service;

import java.io.Closeable;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		return this.getRestfulRunningApplications();
	}
	
	public boolean isStartRunningSparkApplication(String id) throws IOException{
		return this.getRestfulRunningApplications().contains(id);
	}
	
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
	public class Job {
		private int jobId;
		private String name;
		private String submissionTime;
		private String completionTime;
		private String[] stageIds;
		private String status;
		private int numTasks;
		private int numActiveTasks;
		private int numCompletedTasks;
		private int numSkippedTasks;
		private int numFailedTasks;
		private int numActiveStages;
		private int numCompletedStages;
		private int numSkippedStages;
		private int numFailedStages;
		public int getJobId() {
			return jobId;
		}
		public void setJobId(int jobId) {
			this.jobId = jobId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSubmissionTime() {
			return submissionTime;
		}
		public void setSubmissionTime(String submissionTime) {
			this.submissionTime = submissionTime;
		}
		public String getCompletionTime() {
			return completionTime;
		}
		public void setCompletionTime(String completionTime) {
			this.completionTime = completionTime;
		}
		public String[] getStageIds() {
			return stageIds;
		}
		public void setStageIds(String[] stageIds) {
			this.stageIds = stageIds;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getNumTasks() {
			return numTasks;
		}
		public void setNumTasks(int numTasks) {
			this.numTasks = numTasks;
		}
		public int getNumActiveTasks() {
			return numActiveTasks;
		}
		public void setNumActiveTasks(int numActiveTasks) {
			this.numActiveTasks = numActiveTasks;
		}
		public int getNumCompletedTasks() {
			return numCompletedTasks;
		}
		public void setNumCompletedTasks(int numCompletedTasks) {
			this.numCompletedTasks = numCompletedTasks;
		}
		public int getNumSkippedTasks() {
			return numSkippedTasks;
		}
		public void setNumSkippedTasks(int numSkippedTasks) {
			this.numSkippedTasks = numSkippedTasks;
		}
		public int getNumFailedTasks() {
			return numFailedTasks;
		}
		public void setNumFailedTasks(int numFailedTasks) {
			this.numFailedTasks = numFailedTasks;
		}
		public int getNumActiveStages() {
			return numActiveStages;
		}
		public void setNumActiveStages(int numActiveStages) {
			this.numActiveStages = numActiveStages;
		}
		public int getNumCompletedStages() {
			return numCompletedStages;
		}
		public void setNumCompletedStages(int numCompletedStages) {
			this.numCompletedStages = numCompletedStages;
		}
		public int getNumSkippedStages() {
			return numSkippedStages;
		}
		public void setNumSkippedStages(int numSkippedStages) {
			this.numSkippedStages = numSkippedStages;
		}
		public int getNumFailedStages() {
			return numFailedStages;
		}
		public void setNumFailedStages(int numFailedStages) {
			this.numFailedStages = numFailedStages;
		}	
	}
}
