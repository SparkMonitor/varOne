/**
 * 
 */
package com.varone.web.aggregator.unit;

import com.varone.web.metrics.bean.TimeValuePairBean;
import com.varone.web.vo.TimeValueVO;

/**
 * @author allen
 *
 */
public abstract class AbstractUnitAggregator {
	
	protected double getDoubleValue(String value){
		return Double.parseDouble(value);
	}
	
	protected long getLongValue(String value){
		return Long.parseLong(value);
	}
	
	public abstract void aggregate(String format, TimeValuePairBean newPair, TimeValueVO currPair);
}
