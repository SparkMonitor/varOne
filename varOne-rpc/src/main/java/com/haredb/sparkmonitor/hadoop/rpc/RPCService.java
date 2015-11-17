/**
 * 
 */
package com.haredb.sparkmonitor.hadoop.rpc;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.security.token.SecretManager;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.apache.hadoop.yarn.exceptions.YarnRuntimeException;
import org.apache.hadoop.yarn.ipc.YarnRPC;

import com.google.protobuf.BlockingService;
import com.haredb.sparkmonitor.hadoop.rpc.IService;

/**
 * @author allen
 *
 */
public class RPCService implements IService {

	private int threadCount;
	private Server server;
	private Configuration config;
	private Class protocol;
	private Object serverInstance;
	private InetSocketAddress serviceAddress;
	private Configuration localConf = new Configuration();
	private ConcurrentMap<Class<?>, Method> protoCache = new ConcurrentHashMap<Class<?>, Method>();
	private RPCService(){}
	
	public RPCService(Class protocol, Configuration config, Object serverInstance, int threadCount, int port) throws Exception{
		this.config = config;
		this.protocol = protocol;
		this.threadCount = threadCount;
		this.serverInstance = serverInstance;
		this.serviceAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), port);
	}
	
	
	/* (non-Javadoc)
	 * @see com.haredb.hadoop.rpc.IService#startService()
	 */
	@Override
	public void startService() throws Exception {
	    YarnRPC rpc = YarnRPC.create(this.config);
	    Constructor<?> constructor = null;
	    if (constructor == null) {
	      Class<?> pbServiceImplClazz = null;
	      try {
	        pbServiceImplClazz = localConf
	            .getClassByName(getPbServiceImplClassName(protocol));
	      } catch (ClassNotFoundException e) {
	        throw new YarnRuntimeException("Failed to load class: [ pb service impl]", e);
	      }
	      try {
	        constructor = pbServiceImplClazz.getConstructor(protocol);
	        constructor.setAccessible(true);
//	        serviceCache.putIfAbsent(protocol, constructor);
	      } catch (NoSuchMethodException e) {
	        throw new YarnRuntimeException("Could not find constructor with params: "
	            + Long.TYPE + ", " + InetSocketAddress.class + ", "
	            + Configuration.class, e);
	      }
	    }
	    
	    Object service = null;
	    try {
	      service = constructor.newInstance(this.serverInstance);
	    } catch (InvocationTargetException e) {
	      throw new YarnRuntimeException(e);
	    } catch (IllegalAccessException e) {
	      throw new YarnRuntimeException(e);
	    } catch (InstantiationException e) {
	      throw new YarnRuntimeException(e);
	    }

	    Class<?> pbProtocol = service.getClass().getInterfaces()[0];
	    Method method = protoCache.get(protocol);
	    if (method == null) {
	      Class<?> protoClazz = null;
	      try {
	        protoClazz = localConf.getClassByName(getProtoClassName(protocol));
	      } catch (ClassNotFoundException e) {
	        throw new YarnRuntimeException("Failed to load class: ["
	            + getProtoClassName(protocol) + "]", e);
	      }
	      try {
	        method = protoClazz.getMethod("newReflectiveBlockingService",
	            pbProtocol.getInterfaces()[0]);
	        method.setAccessible(true);
	        protoCache.putIfAbsent(protocol, method);
	      } catch (NoSuchMethodException e) {
	        throw new YarnRuntimeException(e);
	      }
	    }
	    
	    try {
	    	this.server = createServer(pbProtocol, this.serviceAddress, this.config, null, this.threadCount,
	          (BlockingService)method.invoke(null, service), null);
	    } catch (InvocationTargetException e) {
	      throw new YarnRuntimeException(e);
	    } catch (IllegalAccessException e) {
	      throw new YarnRuntimeException(e);
	    } catch (IOException e) {
	      throw new YarnRuntimeException(e);
	    }
		this.server.start();
	}
	
	 private String getPbServiceImplClassName(Class<?> clazz) {
	    String srcPackagePart = getPackageName(clazz);
	    String srcClassName = getClassName(clazz);
	    String destPackagePart = srcPackagePart + "." + "impl.pb.service";
	    String destClassPart = srcClassName + "PBServiceImpl";
	    return destPackagePart + "." + destClassPart;
	 }
	 private String getProtoClassName(Class<?> clazz) {
	    String srcClassName = getClassName(clazz);
	    return "com.haredb.sparkmonitor.hadoop.rpc.protos" + "." + srcClassName + "Protos$" + srcClassName;  
	 }
	  private String getClassName(Class<?> clazz) {
	    String fqName = clazz.getName();
	    return (fqName.substring(fqName.lastIndexOf(".") + 1, fqName.length()));
	  }
		  
	  private String getPackageName(Class<?> clazz) {
	    return clazz.getPackage().getName();
	  }
	  private Server createServer(Class<?> pbProtocol, InetSocketAddress addr, Configuration conf, 
		      SecretManager<? extends TokenIdentifier> secretManager, int numHandlers, 
		      BlockingService blockingService, String portRangeConfig) throws IOException {
		    RPC.setProtocolEngine(conf, pbProtocol, ProtobufRpcEngine.class);
		    RPC.Server server = new RPC.Builder(conf).setProtocol(pbProtocol)
		        .setInstance(blockingService).setBindAddress(addr.getHostName())
		        .setPort(addr.getPort()).setNumHandlers(numHandlers).setVerbose(false)
		        .setSecretManager(secretManager).setPortRangeConfig(portRangeConfig)
		        .build();
		    System.out.println("Adding protocol "+pbProtocol.getCanonicalName()+" to the server");
		    server.addProtocol(RPC.RpcKind.RPC_PROTOCOL_BUFFER, pbProtocol, blockingService);
		    return server;
		  }
	/* (non-Javadoc)
	 * @see com.haredb.hadoop.rpc.IService#stopService()
	 */
	@Override
	public void stopService() throws Exception {
		if (this.server != null) {
    		this.server.stop();
    	}
	}
}
