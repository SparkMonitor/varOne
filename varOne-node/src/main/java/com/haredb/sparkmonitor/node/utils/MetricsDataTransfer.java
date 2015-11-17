/**
 * 
 */
package com.haredb.sparkmonitor.node.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto.TupleProto;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;
import com.haredb.sparkmonitor.node.MetricTuple;
import com.haredb.sparkmonitor.node.MetricsType;

/**
 * @author allen
 *
 */
public class MetricsDataTransfer {
	
	MetricsMapProto.Builder metricsMapBuilder = null;
	TupleProto.Builder tupleBuilder = null;
	
	/**
	 * 
	 */
	public MetricsDataTransfer() {
		this.metricsMapBuilder = MetricsMapProto.newBuilder();
		this.tupleBuilder      = TupleProto.newBuilder();
	}
	
	public List<MetricsTypeProto> encodeMetricsType(List<String> metricsType) throws Exception{
		List<MetricsTypeProto> protos = new ArrayList<MetricsTypeProto>(metricsType.size());
		
		for(String type: metricsType){
			MetricsType fromString = MetricsType.fromString(type);
			MetricsTypeProto result = MetricsTypeProto.valueOf(fromString.name());
			protos.add(result);
		}
		
		return protos;
	}
	
	
	public List<MetricsType> decodeMetricsType(List<MetricsTypeProto> metricsTypeProtos) throws Exception{
		List<MetricsType> result = new ArrayList<MetricsType>(metricsTypeProtos.size());
		
		for(MetricsTypeProto proto: metricsTypeProtos){
			result.add(MetricsType.fromString(proto.name()));
		}
		
		return result;
	}
	
	public List<MetricsMapProto> encodeMetricsData(Map<String, List<MetricTuple>> metricsData){		
		List<MetricsMapProto> mapProtos = new ArrayList<MetricsMapProto>(metricsData.size());
		
		for(Entry<String, List<MetricTuple>> entry :metricsData.entrySet()){
			this.metricsMapBuilder.clear();
			this.metricsMapBuilder.setMetricsName(entry.getKey());
			for(MetricTuple tuple: entry.getValue()){
				this.tupleBuilder.clear();
				this.tupleBuilder.setTime(tuple.getTime());
				this.tupleBuilder.setValue(tuple.getValue());
				this.metricsMapBuilder.addMetricsValues(this.tupleBuilder.build());
			}
			mapProtos.add(this.metricsMapBuilder.build());
		}
		
		return mapProtos;
	}

}
