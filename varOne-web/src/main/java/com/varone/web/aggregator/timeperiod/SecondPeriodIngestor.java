/**
 * 
 */
package com.varone.web.aggregator.timeperiod;

import java.util.List;

import com.varone.web.metrics.bean.TimeValuePairBean;

/**
 * @author allen
 *
 */
public class SecondPeriodIngestor extends PeriodDataIngestor {

	public SecondPeriodIngestor(List<TimeValuePairBean> dataSet) {
		 super(dataSet);
	}
}
