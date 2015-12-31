/**
 * 
 */
package com.varone.web.reader.eventlog.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.log4j.Logger;

import com.varone.web.eventlog.bean.SparkEventLogBean;
import com.varone.web.reader.eventlog.EventLogReader;
import com.varone.web.reader.eventlog.parser.SparkEventLogParser;

/**
 * @author allen
 *
 */
public class EventLogHdfsReaderImpl implements EventLogReader {
	private Logger logger = Logger.getLogger(EventLogHdfsReaderImpl.class.getName());
	
	private Configuration config;
	private FileSystem fs;
	private Path logDir;
	
	public EventLogHdfsReaderImpl(Configuration config) throws IOException{
		logger.debug("EventLogHdfsReaderImpl constructor");
		
		this.config = config;
		String logPathStr = this.config.get("spark.eventLog.dir");
		this.fs = FileSystem.get(this.config);
		this.logDir = new Path(logPathStr);
		if(!this.fs.isDirectory(this.logDir)) 
			throw new FileNotFoundException(logPathStr+" does not exist in HDFS");
	}

	/* (non-Javadoc)
	 * @see com.varone.web.reader.eventlog.EventLogReader#getAllInProgressLog()
	 */
	@Override
	public Map<String, SparkEventLogBean> getAllInProgressLog()
			throws Exception {
		logger.info("getAllInProgressLog method");
		
		Map<String, SparkEventLogBean> allEventLogs = new LinkedHashMap<String, SparkEventLogBean>();
		
		PathFilter filter = new PathFilter(){
			@Override
		    public boolean accept(Path path){
		    	return path.getName().endsWith(".inprogress");
		    }
		};
		FileStatus[] allInProgressFileState = fs.listStatus(this.logDir, filter);
				
		for(FileStatus status: allInProgressFileState){
			SparkEventLogParser parser = new SparkEventLogParser();
			FSDataInputStream fis = fs.open(status.getPath());
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while(null != (line=br.readLine())){
            	parser.parseLine(line);
            }
            br.close();
            allEventLogs.put(status.getPath().getName(), parser.result());
		}
		
		return allEventLogs;
	}

	/* (non-Javadoc)
	 * @see com.varone.web.reader.eventlog.EventLogReader#getInProgressLog(java.lang.String)
	 */
	@Override
	public SparkEventLogBean getInProgressLog(final String applicationId)
			throws Exception {
		logger.info("getInProgressLog method, applicationId = " + applicationId);
		
		PathFilter filter = new PathFilter(){
			@Override
		    public boolean accept(Path path){
		    	if(path.getName().endsWith(applicationId + ".inprogress") || 
		    			path.getName().endsWith(applicationId)) return true;
		    	else return false;
		    }
		};
		List<String> eventNames = new ArrayList<String>();
		eventNames.add(SparkEventLogParser.EXECUTOR_ADD);
		eventNames.add(SparkEventLogParser.JOB_START);
		eventNames.add(SparkEventLogParser.TASK_START);
		eventNames.add(SparkEventLogParser.TASK_END);
		FileStatus[] allInProgressFileState = fs.listStatus(this.logDir, filter);
		
		FileStatus status = allInProgressFileState[0];
		SparkEventLogParser parser = new SparkEventLogParser(eventNames);
		FSDataInputStream fis = fs.open(status.getPath());
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while(null != (line=br.readLine())){
        	parser.parseLine(line);
        }
        br.close();
		return parser.result();
	}
	
	public SparkEventLogBean getHistoryStageDetails(final String applicationId) 
			throws Exception{
		logger.info("getHistoryStageDetails method, applicationId = " + applicationId);
		PathFilter filter = new PathFilter() {
			@Override
			public boolean accept(Path path) {
			    if(path.getName().endsWith(applicationId)){
			    	return true;
			    }else{
			    	return false;
			    }
			}
		};
		List<String> eventNames = new ArrayList<String>();
		eventNames.add(SparkEventLogParser.EXECUTOR_ADD);
		eventNames.add(SparkEventLogParser.JOB_START);
		eventNames.add(SparkEventLogParser.JOB_END);
		eventNames.add(SparkEventLogParser.TASK_START);
		eventNames.add(SparkEventLogParser.TASK_END);
		eventNames.add(SparkEventLogParser.STAGE_SUBMIT);
		eventNames.add(SparkEventLogParser.STAGE_COMPLETED);
		eventNames.add(SparkEventLogParser.BLOCKMANAGER_ADD);
		
		FileStatus[] applicationHistoryFileState = fs.listStatus(this.logDir, filter);
		FileStatus status = applicationHistoryFileState[0];
		SparkEventLogParser parser = new SparkEventLogParser(eventNames);
		FSDataInputStream fis = fs.open(status.getPath());
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line;
		while(null != (line=br.readLine())){
			parser.parseLine(line);
		}
		br.close();
		return parser.result();
	}

	@Override
	public List<SparkEventLogBean> getAllSparkAppLog() throws Exception {
		logger.info("getAllSparkAppLog method");
		List<SparkEventLogBean> result = new ArrayList<SparkEventLogBean>();
		
		PathFilter filter = new PathFilter(){
			@Override
		    public boolean accept(Path path){
		    	return !path.getName().endsWith(".inprogress");
		    }
		};
		
		FileStatus[] allFileState = fs.listStatus(this.logDir, filter);
		
		List<String> eventNames = new ArrayList<String>();
		eventNames.add(SparkEventLogParser.APP_START);
		eventNames.add(SparkEventLogParser.APP_END);
		
		for(FileStatus status: allFileState){
			SparkEventLogParser parser = new SparkEventLogParser(eventNames);
			parser.enableBreakPoint();
			FSDataInputStream fis = fs.open(status.getPath());
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null, tmp;
            boolean found = false;
            while(null != (tmp=br.readLine())){
            	line = tmp;
            	if(!found)
            		found = parser.parseLine(line);
            }
            parser.parseLine(line);
            br.close();
            result.add(parser.result());
		}
		return result;
	}

	@Override
	public SparkEventLogBean getApplicationJobs(final String applicationId)
			throws Exception {
		logger.info("getApplicationJobs method, applicationId = " + applicationId);
		PathFilter filter = new PathFilter(){
			@Override
		    public boolean accept(Path path){
		    	return !path.getName().endsWith(".inprogress") &&
		    			path.getName().indexOf(applicationId) != -1;
		    }
		};
		
		FileStatus[] allFileState = fs.listStatus(this.logDir, filter);
		
		List<String> eventNames = new ArrayList<String>();
		eventNames.add(SparkEventLogParser.JOB_START);
		eventNames.add(SparkEventLogParser.JOB_END);
		eventNames.add(SparkEventLogParser.TASK_END);
		eventNames.add(SparkEventLogParser.STAGE_SUBMIT);
		eventNames.add(SparkEventLogParser.STAGE_COMPLETED);
		
		FileStatus status = allFileState[0];
		SparkEventLogParser parser = new SparkEventLogParser(eventNames);
		FSDataInputStream fis = fs.open(status.getPath());
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while(null != (line=br.readLine())){
        	parser.parseLine(line);
        }
        br.close();
		return parser.result();
	}

	@Override
	public SparkEventLogBean getJobStages(final String applicationId, String jobId)
			throws Exception {
		logger.info("getJobStages method, applicationId = " + applicationId + " jobId = " + jobId);
		
		PathFilter filter = new PathFilter(){
			@Override
		    public boolean accept(Path path){
		    	return !path.getName().endsWith(".inprogress") &&
		    			path.getName().indexOf(applicationId) != -1;
		    }
		};
		
		FileStatus[] allFileState = fs.listStatus(this.logDir, filter);
		List<String> eventNames = new ArrayList<String>();
		eventNames.add(SparkEventLogParser.JOB_START);
		eventNames.add(SparkEventLogParser.STAGE_SUBMIT);
		eventNames.add(SparkEventLogParser.STAGE_COMPLETED);
		eventNames.add(SparkEventLogParser.TASK_END);
		
		FileStatus status = allFileState[0];
		SparkEventLogParser parser = new SparkEventLogParser(eventNames);
		FSDataInputStream fis = fs.open(status.getPath());
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while(null != (line=br.readLine())){
        	parser.parseLine(line);
        }
        br.close();
		return parser.result();
	}

}
