/**
 * 
 */
package com.varone.web.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.varone.web.exception.VarOneException;
import com.varone.web.exception.VarOneExceptionParser;
import com.varone.web.facade.SparkMonitorFacade;
import com.varone.web.vo.HistoryDetailStageVO;
import com.varone.web.vo.HistoryVO;
import com.varone.web.vo.JobVO;
import com.varone.web.vo.StageVO;

/**
 * @author allen
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/history")
public class HistoryResource {
	private Logger logger = Logger.getLogger(HistoryResource.class.getName());
	@GET
	@Path("/")
	public String fetchAllApplications() {
		logger.info("start fetchAllApplications method ...");
		try{
			SparkMonitorFacade facade = new SparkMonitorFacade();
			
			List<HistoryVO> result = facade.getAllSparkApplication();
			
			Gson gson = new Gson();
			String toJson = gson.toJson(result);
			
			logger.debug("toJson = " + toJson);
			logger.info("finish fetchAllApplications method ...");
			return toJson;
		}catch(Exception e){
			VarOneExceptionParser parser = new VarOneExceptionParser();
			String errorMessage = parser.parse(e);
			logger.error(errorMessage);
			throw new VarOneException(errorMessage);
		}
	}
	
	@GET
	@Path("/{applicationId}/jobs")
	public String fetchApplicationJobs(@PathParam("applicationId") String applicationId){
		logger.info("start fetchApplicationJobs method ...");
		logger.debug("applicationId = " + applicationId);
		try{
			SparkMonitorFacade facade = new SparkMonitorFacade();
			
			List<JobVO> result = facade.getSparkApplicationJobs(applicationId);
			
			Gson gson = new Gson();
			String toJson = gson.toJson(result);
			logger.debug("toJson = " + toJson);
			logger.info("finish fetchApplicationJobs method ...");
			return toJson;
		}catch(Exception e){
			VarOneExceptionParser parser = new VarOneExceptionParser();
			String errorMessage = parser.parse(e);
			logger.error(errorMessage);
			throw new VarOneException(errorMessage);
		}
	}
	
	@GET
	@Path("/{applicationId}/{jobId}/stages")
	public String fetchJobStages(@PathParam("applicationId") String applicationId, 
			@PathParam("jobId") String jobId){
		logger.info("start fetchJobStages method ...");
		logger.debug("applicationId = " + applicationId + " jobId = " + jobId);
		try{
			SparkMonitorFacade facade = new SparkMonitorFacade();
			
			List<StageVO> result = facade.getSparkJobStages(applicationId, jobId);
			
			Gson gson = new Gson();
			String toJson = gson.toJson(result);
			logger.debug("toJson = " + toJson);
			logger.info("finish fetchJobStages method ...");
			return toJson;
		}catch(Exception e){
			VarOneExceptionParser parser = new VarOneExceptionParser();
			String errorMessage = parser.parse(e);
			logger.error(errorMessage);
			throw new VarOneException(errorMessage);
		}
	}
	
	@GET
	@Path("/{applicationId}/{jobId}/{stageId}")
	public String fetchStageDetail(@PathParam("applicationId") String applicationId, 
			@PathParam("jobId") String jobId, @PathParam("stageId") String stageId){
		logger.info("start fetchStageDetail method ...");
		logger.debug("application = " + applicationId + " jobId = " + jobId  + " stageId = " + stageId);
		try{
			SparkMonitorFacade facade = new SparkMonitorFacade();
			HistoryDetailStageVO historyDetailStage = facade.getHistoryDetailStageTask(applicationId, Integer.parseInt(stageId));
				
			Gson gson = new Gson();
			String toJson = gson.toJson(historyDetailStage);
			logger.debug("toJson = " + toJson);
			logger.info("finish fetchStageDetail method ...");
			return toJson;
		}catch(Exception e){
			VarOneExceptionParser parser = new VarOneExceptionParser();
			String errorMessage = parser.parse(e);
			logger.error(errorMessage);
			throw new VarOneException(errorMessage);
		}
	}
}
