/**
 * 
 */
package com.haredb.sparkmonitor.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author allen
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/test")
public class TestRest {

	/**
	 * 
	 */
	public TestRest() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("/")
	public String test(@Context HttpServletRequest request){
		return "{\"name\":\"Allen\"}";
	}
	

}
