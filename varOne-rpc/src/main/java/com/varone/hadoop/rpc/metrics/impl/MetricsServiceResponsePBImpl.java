/**
 * 
 */
package com.varone.hadoop.rpc.metrics.impl;

import java.util.List;

import com.varone.hadoop.rpc.metrics.MetricsServiceResponse;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsResponseProtoOrBuilder;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;

/**
 * @author allen
 *
 */
public class MetricsServiceResponsePBImpl implements MetricsServiceResponse {
	protected MetricsResponseProto proto = MetricsResponseProto.getDefaultInstance();
	protected MetricsResponseProto.Builder builder = null;
	protected boolean viaProto = false;
	
	
	protected List<MetricsMapProto> result;
	
	/**
	 * 
	 */
	public MetricsServiceResponsePBImpl() {
		this.builder = MetricsResponseProto.newBuilder();
	}

	public MetricsServiceResponsePBImpl(MetricsResponseProto proto) {
		this.proto = proto;
		this.viaProto = true;
	}
	
	/* (non-Javadoc)
	 * @see com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceResponse#getResult()
	 */
	@Override
	public List<MetricsMapProto> getResult() {
		MetricsResponseProtoOrBuilder p = viaProto? this.proto:this.builder;
		this.result = p.getResultList();
		return this.result;
	}

	/* (non-Javadoc)
	 * @see com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceResponse#setResult(java.util.List)
	 */
	@Override
	public void setResult(List<MetricsMapProto> result) {
		this.builder.clearResult();
		this.builder.addAllResult(result);
		this.result = result;
	}

	public MetricsResponseProto getProto() {
		return this.builder.build();
	}

}
