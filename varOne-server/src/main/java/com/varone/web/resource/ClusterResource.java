/**
 * 
 */
package com.varone.web.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.varone.web.exception.VarOneException;
import com.varone.web.exception.VarOneExceptionParser;
import com.varone.web.facade.SparkMonitorFacade;
import com.varone.web.vo.DefaultTotalNodeVO;

/**
 * @author allen
 *
 */	
@Produces(MediaType.APPLICATION_JSON)
@Path("/cluster")
public class ClusterResource {
	private Logger logger = Logger.getLogger(ClusterResource.class.getName());
	
	@GET
	@Path("/")
	public String fetchClusterDashBoard(@QueryParam("metrics") String metrics, 
			@QueryParam("period") String period){
		logger.info("start fetchClusterDashBoard method ...");
		logger.debug("metrics = " + metrics + " period = " + period);
		try{
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
			String toJson = gson.toJson(result);
			
			logger.debug("metricsAsList size = " + metricsAsList.size() + " period = " + period);
			logger.debug(" toJson = " + toJson);
			logger.info("finish fetchClusterDashBoard method ...");
			return toJson;
		}catch(Exception e){
		    VarOneExceptionParser parser = new VarOneExceptionParser();
		    String errorMessage = parser.parse(e);
			logger.error(errorMessage);
			throw new VarOneException(errorMessage);
		}
	}
}
