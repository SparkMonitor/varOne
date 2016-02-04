/**
 * 
 */
package com.varone.server;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import com.varone.conf.ConfVars;
import com.varone.conf.VarOneConfiguration;
import com.varone.web.resource.ClusterResource;
import com.varone.web.resource.HistoryResource;
import com.varone.web.resource.JobResource;
import com.varone.web.resource.NodeResource;

/**
 * @author allen
 *
 */
public class VarOneServer extends Application {
	private static final Logger LOG = Logger.getLogger(VarOneServer.class);
	public static Server jettyWebServer;
	/**
	 * 
	 */
	public VarOneServer() throws Exception {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		VarOneConfiguration conf = VarOneConfiguration.create();
		conf.setProperty("args", args);
		conf.verifyEnv();
		
		
		// REST api
	    final ServletContextHandler resourcesContext = setupResourcesContextHandler(conf);
	    // Web UI
	    final WebAppContext webApp = setupWebAppContext(conf);

	    // add all handlers
	    ContextHandlerCollection contexts = new ContextHandlerCollection();
	    contexts.setHandlers(new Handler[]{resourcesContext, webApp});

	    jettyWebServer = setupJettyServer(conf);
	    jettyWebServer.setHandler(contexts);
	    
	    LOG.info("Starting varOne server");
	    try {
	      jettyWebServer.start(); //start VarOneServer
	    } catch (Exception e) {
	      LOG.error("Error while running jettyServer", e);
	      System.exit(-1);
	    }
	    LOG.info("Done, varOne server started");

	    Runtime.getRuntime().addShutdownHook(new Thread(){
	      @Override public void run() {
	        LOG.info("Shutting down varOne Server ... ");
	        try {
	          jettyWebServer.stop();
	        } catch (Exception e) {
	          LOG.error("Error while stopping servlet container", e);
	        }
	        LOG.info("Bye");
	      }
	    });

	    if (System.getenv("VARONE_IDENT_STRING") == null) {
	      try {
	        System.in.read();
	      } catch (IOException e) {
	        LOG.error("Exception in VarOneServer while main ", e);
	      }
	      System.exit(0);
	    }
	    
	    jettyWebServer.join();
	}
	
	private static Server setupJettyServer(VarOneConfiguration conf) {
		AbstractConnector connector = new SelectChannelConnector();
	    

	    // Set some timeout options to make debugging easier.
	    int timeout = 1000 * 30;
	    connector.setMaxIdleTime(timeout);
	    connector.setSoLingerTime(-1);
	    connector.setHost(conf.getServerAddress());
	    connector.setPort(conf.getServerPort());

	    final Server server = new Server();
	    server.addConnector(connector);

	    return server;
	}

	private static WebAppContext setupWebAppContext(VarOneConfiguration conf) {
		WebAppContext webApp = new WebAppContext();
	    webApp.setContextPath(conf.getServerContextPath());
	    File warPath = new File(conf.getString(ConfVars.VARONE_WAR));
	    if (warPath.isDirectory()) {
	      webApp.setResourceBase(warPath.getPath());
	      webApp.setParentLoaderPriority(true);
	    } else {
	      // use packaged WAR
	      webApp.setWar(warPath.getAbsolutePath());
	      File warTempDirectory = new File(conf.getRelativeDir(ConfVars.VARONE_WAR_TEMPDIR));
	      warTempDirectory.mkdir();
	      LOG.info("ZeppelinServer Webapp path: {}" + warTempDirectory.getPath());
	      webApp.setTempDirectory(warTempDirectory);
	    }
	    // Explicit bind to root
	    webApp.addServlet(new ServletHolder(new DefaultServlet()), "/*");
	    return webApp;
	}

	private static ServletContextHandler setupResourcesContextHandler(
			VarOneConfiguration conf) {
		final ServletHolder cxfServletHolder = new ServletHolder(new CXFNonSpringJaxrsServlet());
	    cxfServletHolder.setInitParameter("javax.ws.rs.Application", VarOneServer.class.getName());
	    cxfServletHolder.setName("rest");
	    cxfServletHolder.setForcedPath("rest");

	    final ServletContextHandler cxfContext = new ServletContextHandler();
	    cxfContext.setSessionHandler(new SessionHandler());
	    cxfContext.setContextPath(conf.getServerContextPath());
	    cxfContext.addServlet(cxfServletHolder, "/rest/*");

	    return cxfContext;
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		return classes;
	}
	
	@Override
	public Set<Object> getSingletons() {
	    Set<Object> singletons = new HashSet<>();
	    
	    ClusterResource clusterResource = new ClusterResource();
	    singletons.add(clusterResource);
	    
	    HistoryResource historyResource = new HistoryResource();
	    singletons.add(historyResource);
	    
	    JobResource jobResource = new JobResource();
	    singletons.add(jobResource);
	    
	    NodeResource nodeResource = new NodeResource();
	    singletons.add(nodeResource);
	    
	    return singletons;
	}
}
