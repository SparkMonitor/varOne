/**
 * 
 */
package com.varone.web.aggregator.timeperiod;

import java.util.ArrayList;
import java.util.List;

import com.varone.web.metrics.bean.TimeValuePairBean;

/**
 * @author allen
 *
 */
public abstract class PeriodDataIngestor {
	
	protected List<TimeValuePairBean> dataSet;
	
	public PeriodDataIngestor(List<TimeValuePairBean> dataSet) {
		this.dataSet = dataSet;
	}

	public List<TimeValuePairBean> ingest(List<Long> periodOfPlotPoint){
		List<TimeValuePairBean> result = new ArrayList<TimeValuePairBean>();
				
		for(TimeValuePairBean pair: this.dataSet){
			long ts = pair.getTime();
			if(periodOfPlotPoint.contains(ts)){
				result.add(pair);
			}
		}
		return result;
	}
}
