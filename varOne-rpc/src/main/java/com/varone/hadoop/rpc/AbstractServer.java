/**
 * 
 */
package com.varone.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;

import com.varone.hadoop.rpc.IService;
import com.varone.hadoop.rpc.RPCService;

/**
 * @author allen
 *
 */
public abstract class AbstractServer {

	protected IService service;

	
	public abstract void constructServer(Configuration config) throws Exception;
	
	public void constructHadoopServer(Class protocol, Configuration config, Object serverInstance, int threadCount, int port) throws Exception{
		if(port < 0 || port > 65535) throw new Exception("Illegal port number " + port);
		this.service = new RPCService(protocol, config, serverInstance, threadCount, port);
	}
	
	public void start() throws Exception{
		this.service.startService();
	}
	
	public void stop() throws Exception{
		this.service.stopService();
	}

}
