package com.varone.web.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.varone.conf.ConfVars;
import com.varone.conf.VarOneConfiguration;


@Produces(MediaType.APPLICATION_JSON)
@Path("/varone")
public class VarOneResource {

	@GET
	@Path("/timer")
	public String fetchTimerInterval(){
		VarOneConfiguration config = VarOneConfiguration.create();
		return config.getTimerInterval();
	}
}
