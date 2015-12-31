/**
 * 
 */
package com.varone.web.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.varone.web.facade.SparkMonitorFacade;
import com.varone.web.vo.DefaultNodeVO;

/**
 * @author allen
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/nodes")
public class NodeResource {
	private Logger logger = Logger.getLogger(NodeResource.class.getName());
	@GET
	@Path("/")
	public String fetchNodes() throws Exception{
		logger.info("start fetchNodes method ...");
		SparkMonitorFacade facade = new SparkMonitorFacade();
		List<String> nodes = facade.getNodeLists();
		Gson gson = new Gson();
		String toJson = gson.toJson(nodes);
		logger.debug("toJson = " + toJson);
		logger.info("finish fetchNodes method ...");
		return toJson;
	}
	
	@GET
	@Path("/{node}")
	public String fetchNodeDashBoard(@PathParam("node") String node, 
			@QueryParam("metrics") String metrics, @QueryParam("period") String period) throws Exception{
		logger.info("start fetchNodeDashBoard method ...");
		logger.debug("node = " + node + " metrics = " + metrics + " period = " + period);
		SparkMonitorFacade facade = new SparkMonitorFacade();
		List<String> metricsAsList = new ArrayList<String>();
		if(metrics != null){
			String[] metricsArr = metrics.split(",");
			for(String metric: metricsArr){
				if(!metric.trim().equals(""))
					metricsAsList.add(metric);
			}
		}
		DefaultNodeVO nodeDashBoard = facade.getNodeDashBoard(node, metricsAsList, period);
		Gson gson = new Gson();
		String toJson =  gson.toJson(nodeDashBoard);
		logger.debug("toJson = " + toJson);
		logger.info("finish fetchNodeDashBoard method ...");
		return toJson;
	}
}
