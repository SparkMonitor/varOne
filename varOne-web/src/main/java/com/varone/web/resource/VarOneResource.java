/**
 * 
 */
package com.varone.web.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	@GET
	@Path("/conf")
	public String fetchVarOneConfig() throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		VarOneConfigVO result = facade.getVarOneConfig();
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@POST
	@Path("/conf")
	public String updateVarOneConfig(final VarOneConfigForm conf) throws Exception{
		
		SparkMonitorFacade facade = new SparkMonitorFacade();

		UpdateStatusVO result =	facade.updateVarOneConfig(conf);

		Gson gson = new Gson();
		return gson.toJson(result);
	}
}