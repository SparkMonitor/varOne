/**
 * 
 */
package com.varone.web.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.varone.web.facade.SparkMonitorFacade;
import com.varone.web.vo.DefaultTotalNodeVO;

/**
 * @author allen
 *
 */	
@Produces(MediaType.APPLICATION_JSON)
@Path("/cluster")
public class ClusterResource {
	@GET
	@Path("/")
	public String fetchClusterDashBoard(@QueryParam("metrics") String metrics, 
			@QueryParam("period") String period) throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		List<String> metricsAsList = new ArrayList<String>();
		if(metrics != null){
			String[] metricsArr = metrics.split(",");
			for(String metric: metricsArr){
				if(!metric.trim().equals(""))
					metricsAsList.add(metric);
			}
		}
		
		DefaultTotalNodeVO result = facade.getDefaultClusterDashBoard(metricsAsList, period);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}
