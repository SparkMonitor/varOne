/**
 * 
 */
package com.varone.hadoop.rpc.metrics.impl.pb.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.yarn.ipc.RPCUtil;

import com.google.protobuf.ServiceException;
import com.varone.hadoop.rpc.metrics.MetricsService;
import com.varone.hadoop.rpc.metrics.MetricsServicePB;
import com.varone.hadoop.rpc.metrics.MetricsServiceRequest;
import com.varone.hadoop.rpc.metrics.MetricsServiceResponse;
import com.varone.hadoop.rpc.metrics.impl.MetricsServiceRequestPBImpl;
import com.varone.hadoop.rpc.metrics.impl.MetricsServiceResponsePBImpl;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsRequestProto;

/**
 * @author allen
 *
 */
public class MetricsServicePBClientImpl implements MetricsService, Closeable {
	
	private MetricsServicePB proxy;
	
	public MetricsServicePBClientImpl(long clientVersion, InetSocketAddress addr, Configuration conf) throws IOException{
		RPC.setProtocolEngine(conf, MetricsServicePB.class, ProtobufRpcEngine.class);
		this.proxy = (MetricsServicePB)RPC
				.getProxy(MetricsServicePB.class, clientVersion, addr, conf);
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		if(this.proxy != null) {
			RPC.stopProxy(this.proxy);
		}
	}

	/* (non-Javadoc)
	 * @see com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsService#getMetrics(com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceRequest)
	 */
	@Override
	public MetricsServiceResponse getMetrics(MetricsServiceRequest request)
			throws Exception {
		MetricsRequestProto requestProto = ((MetricsServiceRequestPBImpl)request).getProto();
		try {
		      return new MetricsServiceResponsePBImpl(proxy.getMetrics(null, requestProto));
	    } catch (ServiceException e) {
	      RPCUtil.unwrapAndThrowException(e);
	      return null;
	    }
	}

}
