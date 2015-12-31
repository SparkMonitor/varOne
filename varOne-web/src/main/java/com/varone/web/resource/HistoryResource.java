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
	public String fetchAllApplications() throws Exception{
		logger.info("start fetchAllApplications method ...");
		SparkMonitorFacade facade = new SparkMonitorFacade();
		
		List<HistoryVO> result = facade.getAllSparkApplication();
		
		Gson gson = new Gson();
		String toJson = gson.toJson(result);
		
		logger.debug("toJson = " + toJson);
		logger.info("finish fetchAllApplications method ...");
		return toJson;
	}
	
	@GET
	@Path("/{applicationId}/jobs")
	public String fetchApplicationJobs(@PathParam("applicationId") String applicationId) throws Exception{
		logger.info("start fetchApplicationJobs method ...");
		logger.debug("applicationId = " + applicationId);
		SparkMonitorFacade facade = new SparkMonitorFacade();
		
		List<JobVO> result = facade.getSparkApplicationJobs(applicationId);
		
		Gson gson = new Gson();
		String toJson = gson.toJson(result);
		logger.debug("toJson = " + toJson);
		logger.info("finish fetchApplicationJobs method ...");
		return toJson;
	}
	
	@GET
	@Path("/{applicationId}/{jobId}/stages")
	public String fetchJobStages(@PathParam("applicationId") String applicationId, 
			@PathParam("jobId") String jobId) throws Exception{
		logger.info("start fetchJobStages method ...");
		logger.debug("applicationId = " + applicationId + " jobId = " + jobId);
		
		SparkMonitorFacade facade = new SparkMonitorFacade();
		
		List<StageVO> result = facade.getSparkJobStages(applicationId, jobId);
		
		Gson gson = new Gson();
		String toJson = gson.toJson(result);
		logger.debug("toJson = " + toJson);
		logger.info("finish fetchJobStages method ...");
		return toJson;
	}
	
	@GET
	@Path("/{applicationId}/{jobId}/{stageId}")
	public String fetchStageDetail(@PathParam("applicationId") String applicationId, 
			@PathParam("jobId") String jobId, @PathParam("stageId") String stageId) throws Exception{
		logger.info("start fetchStageDetail method ...");
		logger.debug("application = " + applicationId + " jobId = " + jobId  + " stageId = " + stageId);
		
		SparkMonitorFacade facade = new SparkMonitorFacade();
		HistoryDetailStageVO historyDetailStage = facade.getHistoryDetailStageTask(applicationId, Integer.parseInt(stageId));
			
		Gson gson = new Gson();
		String toJson = gson.toJson(historyDetailStage);
		logger.debug("toJson = " + toJson);
		logger.info("finish fetchStageDetail method ...");
		return toJson;
	}
}
