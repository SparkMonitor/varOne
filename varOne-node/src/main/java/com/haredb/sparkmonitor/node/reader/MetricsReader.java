/**
 * 
 */
package com.haredb.sparkmonitor.node.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.haredb.sparkmonitor.node.MetricTuple;
import com.haredb.sparkmonitor.node.MetricsType;

/**
 * @author allen
 *
 */
public abstract class MetricsReader {
	
	protected MetricsType metricType;
	protected MetricsReader reader;
	
	public MetricsReader(MetricsReader reader, MetricsType metricType) {
		this.reader = reader;
		this.metricType = metricType;
	}
	
	protected Map<String, List<MetricTuple>> getMetricsData(Collection<File> filteredFiles) throws IOException{
		Map<String, List<MetricTuple>> result = new LinkedHashMap<String, List<MetricTuple>>();
		
		for(File metricFile: filteredFiles){
			List<String> lines = FileUtils.readLines(metricFile);
			lines.remove(0); //skip header
			List<MetricTuple> tuples = new ArrayList<MetricTuple>(lines.size());
			for(String pairStr: lines){
				tuples.add(new MetricTuple(pairStr));
			}
			result.put(metricFile.getName(), tuples);
		}
		
		return result;
	}

	protected abstract Map<String, List<MetricTuple>> read(String applicationId, String metricsDir) throws IOException;
}
