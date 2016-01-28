/**
 * 
 */
package com.varone.web.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.varone.web.exception.VarOneException;
import com.varone.web.exception.VarOneExceptionParser;
import com.varone.web.facade.SparkMonitorFacade;
import com.varone.web.vo.DefaultApplicationVO;

/**
 * @author allen
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/job")
public class JobResource {
	private Logger logger = Logger.getLogger(JobResource.class.getName());
	@GET
	@Path("/")
	public String fetchRunngingJobs(@Context HttpServletRequest request){
		logger.info("start fetchRunningJobs method ...");
		try{
			SparkMonitorFacade facade = new SparkMonitorFacade();
			List<String> runningJobs = facade.getRunningJobs();
			Gson gson = new Gson();
			String toJson = gson.toJson(runningJobs);
			logger.debug("toJson = " + toJson);
			logger.info("finish fetchRunningJobs method ...");
			return toJson;
		}catch(Exception e){
			VarOneExceptionParser parser = new VarOneExceptionParser();
			throw new VarOneException(parser.parse(e));
		}
	}
	
	@GET
	@Path("/{applicationId}")
	public String fetchJob(@PathParam("applicationId") String applicationId, 
			@QueryParam("metrics") String metrics, @QueryParam("period") String period){
		logger.info("start fetchJob method ...");
		logger.debug("applicationId = " + applicationId + " metrics = " + metrics + " period = " + period);
		try{
			SparkMonitorFacade facade = new SparkMonitorFacade();
			List<String> metricsAsList = new ArrayList<String>();
			if(metrics != null){
				String[] metricsArr = metrics.split(",");
				for(String metric: metricsArr){
					if(!metric.trim().equals(""))
						metricsAsList.add(metric);
				}
	//			metricsAsList = Arrays.asList(metrics.split(","));
			}
			DefaultApplicationVO result = facade.getJobDashBoard(applicationId, metricsAsList, period);
			Gson gson = new Gson();
			String toJson =  gson.toJson(result);
			logger.debug("toJson = " + toJson);
			logger.info("finish fetchJob method ...");
			return toJson;
		}catch(Exception e){
			VarOneExceptionParser parser = new VarOneExceptionParser();
			throw new VarOneException(parser.parse(e));
		}
	}
}
