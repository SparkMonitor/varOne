/**
 * 
 */
package com.varone.web.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.varone.web.facade.SparkMonitorFacade;
import com.varone.web.form.VarOneConfigForm;
import com.varone.web.vo.UpdateStatusVO;
import com.varone.web.vo.VarOneConfigVO;

/**
 * @author allen
 *
 */

@Produces(MediaType.APPLICATION_JSON)
@Path("/varOne")
public class VarOneResource {
	private Logger logger = Logger.getLogger(VarOneResource.class.getName());
	@GET
	@Path("/conf")
	public String fetchVarOneConfig() throws Exception{
		logger.info("start fetchVarOneConfig method ...");
		SparkMonitorFacade facade = new SparkMonitorFacade();
		VarOneConfigVO result = facade.getVarOneConfig();
		Gson gson = new Gson();
		String toJson =  gson.toJson(result);
		logger.debug("toJson = " + toJson);
		logger.info("finish fetchVarOneConfig method ...");
		return toJson;
	}
	
	@POST
	@Path("/conf")
	public String updateVarOneConfig(final VarOneConfigForm conf) throws Exception{
		logger.info("start updateVarOneConfig method ...");
		SparkMonitorFacade facade = new SparkMonitorFacade();

		UpdateStatusVO result =	facade.updateVarOneConfig(conf);

		Gson gson = new Gson();
		String toJson =  gson.toJson(result);
		logger.debug("toJson = " + toJson);
		logger.info("finish updateVarOneConfig method ...");
		return toJson;
	}
}