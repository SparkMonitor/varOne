/**
 * 
 */
package com.varone.hadoop.rpc.metrics.impl;

import java.util.List;

import com.varone.hadoop.rpc.metrics.MetricsServiceRequest;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsRequestProto;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsRequestProtoOrBuilder;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;

/**
 * @author allen
 *
 */
public class MetricsServiceRequestPBImpl implements MetricsServiceRequest {
	protected MetricsRequestProto proto = MetricsRequestProto.getDefaultInstance();
	protected MetricsRequestProto.Builder builder = null;
	protected boolean viaProto = false;
	
	protected String applicationId;
	protected List<MetricsTypeProto> metrics;
	
	/**
	 * 
	 */
	public MetricsServiceRequestPBImpl() {
		this.builder = MetricsRequestProto.newBuilder();
	}
	
	public MetricsServiceRequestPBImpl(MetricsRequestProto proto) {
		this.proto = proto;
		this.viaProto = true;
	}

	/* (non-Javadoc)
	 * @see com.varone.hadoop.rpc.metrics.MetricsServiceRequest#getApplicationId()
	 */
	@Override
	public String getApplicationId() {
		MetricsRequestProtoOrBuilder p = viaProto? this.proto:this.builder;
		this.applicationId = p.getApplicationId();
		return this.applicationId;
	}

	/* (non-Javadoc)
	 * @see com.varone.hadoop.rpc.metrics.MetricsServiceRequest#setApplicationId(java.lang.String)
	 */
	@Override
	public void setApplicationId(String applicationId) {
		this.builder.clearApplicationId();
		this.builder.setApplicationId(applicationId);
		this.applicationId = applicationId;
	}

	/* (non-Javadoc)
	 * @see com.varone.hadoop.rpc.metrics.MetricsServiceRequest#getMetrics()
	 */
	@Override
	public List<MetricsTypeProto> getMetrics() {
		MetricsRequestProtoOrBuilder p = viaProto? this.proto:this.builder;
		this.metrics = p.getMetricsList();
		return this.metrics;
	}

	/* (non-Javadoc)
	 * @see com.varone.hadoop.rpc.metrics.MetricsServiceRequest#setMetrics(java.util.List)
	 */
	@Override
	public void setMetrics(List<MetricsTypeProto> metrics) {
		this.builder.clearMetrics();
		this.builder.addAllMetrics(metrics);
		this.metrics = metrics;
	}

	public MetricsRequestProto getProto() {
		return this.builder.build();
	}

}
