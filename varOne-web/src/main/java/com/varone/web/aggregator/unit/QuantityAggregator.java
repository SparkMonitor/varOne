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
public class QuantityAggregator extends AbstractUnitAggregator {

	/* (non-Javadoc)
	 * @see com.varone.web.aggregator.unit.AbstractUnitAggregator#aggregate(java.lang.String, 
	 * 			com.varone.web.metrics.bean.TimeValuePairBean, com.varone.web.vo.TimeValueVO)
	 */
	@Override
	public void aggregate(String format, TimeValuePairBean newPair, TimeValueVO currPair) {
		long newValue = Long.parseLong(newPair.getValue());
		long currValue = Long.parseLong(currPair.getValue());
		currPair.setValue(String.valueOf(newValue+currValue));
	}

}
