/**
 * 
 */
package com.haredb.sparkmonitor.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.haredb.sparkmonitor.facade.SparkMonitorFacade;
import com.haredb.sparkmonitor.vo.DefaultApplicationVO;

/**
 * @author allen
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/job")
public class JobResource {
	@GET
	@Path("/")
	public String fetchRunngingJobs(@Context HttpServletRequest request) throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		List<String> runningJobs = facade.getRunningJobs();
		Gson gson = new Gson();
		return gson.toJson(runningJobs);
	}
	
	@GET
	@Path("/{applicationId}")
	public String fetchJob(@PathParam("applicationId") String applicationId, @QueryParam("metrics") String metrics) throws Exception{
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
		DefaultApplicationVO result = facade.getJobDashBoard(applicationId, metricsAsList);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}
