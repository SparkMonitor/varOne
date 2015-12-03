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

import com.google.gson.Gson;
import com.varone.web.facade.SparkMonitorFacade;
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
	@GET
	@Path("/")
	public String fetchAllApplications() throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		
		List<HistoryVO> result = facade.getAllSparkApplication();
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@GET
	@Path("/{applicationId}/jobs")
	public String fetchApplicationJobs(@PathParam("applicationId") String applicationId) throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		
		List<JobVO> result = facade.getSparkApplicationJobs(applicationId);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@GET
	@Path("/{applicationId}/{jobId}/stages")
	public String fetchJobStages(@PathParam("applicationId") String applicationId, 
			@PathParam("jobId") String jobId) throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		
		List<StageVO> result = facade.getSparkJobStages(applicationId, jobId);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@GET
	@Path("/{applicationId}/{jobId}/{stageId}")
	public String fetchStageDetail(@PathParam("applicationId") String applicationId, 
			@PathParam("jobId") String jobId, @PathParam("stageId") String stageId) throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		
//		@TODO
//		facade.getSparkStageDetails(applicationId, jobId, stageId); 
		
		Gson gson = new Gson();
		return gson.toJson("");
	}
}
