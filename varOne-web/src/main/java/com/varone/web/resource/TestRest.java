/**
 * 
 */
package com.varone.web.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * @author allen
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/test")
public class TestRest {
	private Logger logger = Logger.getLogger(TestRest.class.getName());

	/**
	 * 
	 */
	public TestRest() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("/")
	public String test(@Context HttpServletRequest request){
		logger.info("Hello Allen");
		return "{\"name\":\"Allen\"}";
	}
	

}
