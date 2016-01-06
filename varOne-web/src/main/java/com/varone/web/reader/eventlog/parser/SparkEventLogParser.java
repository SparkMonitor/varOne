/**
 * 
 */
package com.varone.web.reader.eventlog.parser;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.varone.web.eventlog.bean.SparkEventLogBean;

/**
 * @author allen
 *
 */
public class SparkEventLogParser {
	
	public final static String APP_START       = "SparkListenerApplicationStart";
	public final static String JOB_START       = "SparkListenerJobStart";
	public final static String STAGE_SUBMIT    = "SparkListenerStageSubmitted";
	public final static String EXECUTOR_ADD    = "SparkListenerExecutorAdded";
	public final static String TASK_START      = "SparkListenerTaskStart";
	public final static String TASK_END        = "SparkListenerTaskEnd";
	public final static String STAGE_COMPLETED = "SparkListenerStageCompleted";
	public final static String JOB_END         = "SparkListenerJobEnd";
	public final static String APP_END         = "SparkListenerApplicationEnd";
	public final static String BLOCKMANAGER_ADD = "SparkListenerBlockManagerAdded";
	
	/***
	 * If you just want to find only one event,
	 * you can set breakPoint to true for avoiding unnecessary parsing
	 * if this parser find the event and must return true to perform the "event found!, so you can skip post parser."   
	 */
	private boolean breakPoints = false;
	private Gson gson;
	private JsonParser parser;
	private List<String> eventNames;
	private SparkEventLogBean eventLogs; 
	
	/**
	 * default SparkEventLogParser collect executor and task information
	 * if you want to collect another event, please create another constructor for your case.
	**/
	public SparkEventLogParser(){
		this.gson = new Gson();
		this.parser = new JsonParser();
		this.eventLogs = new SparkEventLogBean();
		//default event log, EXECUTOR_ADD & TASK_START & TASK_END
		this.eventNames = new ArrayList<String>();
		this.eventNames.add(EXECUTOR_ADD);
		this.eventNames.add(TASK_START);
		this.eventNames.add(TASK_END);
	}
	
	public SparkEventLogParser(List<String> eventNames){
		this.gson = new Gson();
		this.parser = new JsonParser();
		this.eventLogs = new SparkEventLogBean();
		this.eventNames = new ArrayList<String>(eventNames.size());
		for(String eventName: eventNames){
			this.eventNames.add(eventName);
		}
	}
	
	public boolean parseLine(String line) throws Exception{
		int i = -1;
		JsonElement element = null;
		try{
			element = parser.parse(line);
		} catch(Exception e){
			System.out.println(line);
			return false;
		}
		if(element.isJsonObject()){
			boolean onlyOneEvent = false;
			JsonObject asJsonObject = element.getAsJsonObject();
			String event = asJsonObject.get("Event").getAsString();
			
			if((i = this.eventNames.indexOf(event)) != -1){
				String eventName = this.eventNames.get(i);
				switch(eventName){
					case JOB_START:
						this.eventLogs.addJobStart(this.gson.fromJson(element, SparkEventLogBean.JobStart.class));
						break;
					case EXECUTOR_ADD: 
						this.eventLogs.addExecutorAdd(this.gson.fromJson(element, SparkEventLogBean.ExecutorAdded.class));
						break;
					case TASK_START:
						this.eventLogs.addTaskStart(this.gson.fromJson(element, SparkEventLogBean.TaskStart.class));
						break;
					case TASK_END:
						this.eventLogs.addTaskEnd(this.gson.fromJson(element, SparkEventLogBean.TaskEnd.class));
						break;
					case APP_START:
						this.eventLogs.setAppStart(this.gson.fromJson(element, SparkEventLogBean.AppStart.class));
						onlyOneEvent = true;
						break;
					case APP_END: 
						this.eventLogs.setAppEnd(this.gson.fromJson(element, SparkEventLogBean.AppEnd.class));
						onlyOneEvent = true;
						break;
					case JOB_END:
						this.eventLogs.addJobEnd(this.gson.fromJson(element, SparkEventLogBean.JobEnd.class));
						break;
					case STAGE_SUBMIT:
						this.eventLogs.addStageSubmit(this.gson.fromJson(element, SparkEventLogBean.StageSubmit.class));
						break;
					case BLOCKMANAGER_ADD:
						this.eventLogs.addBlocKManager(this.gson.fromJson(element, SparkEventLogBean.BlockManager.class));
						break;
					case STAGE_COMPLETED:
						this.eventLogs.addStageComplete(this.gson.fromJson(element, SparkEventLogBean.StageCompleted.class));
				}
			}
			if(this.breakPoints && onlyOneEvent){
				return true;
			} else {
				return false;
			}
		} else {
			throw new Exception("JsonParser Exception: " + line + " is not a json foramt.");
		}
	}
	
	public void enableBreakPoint(){
		this.breakPoints = true;
	}
	
	public SparkEventLogBean result(){
		return this.eventLogs;
	}
	
}
