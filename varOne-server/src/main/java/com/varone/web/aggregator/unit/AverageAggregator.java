/**
 * 
 */
package com.varone.web.aggregator.unit;

import java.util.List;

import com.varone.web.metrics.bean.TimeValuePairBean;
import com.varone.web.vo.TimeValueVO;

/**
 * @author allen
 *
 */
public class AverageAggregator extends AbstractUnitAggregator {

	/* (non-Javadoc)
	 * @see com.varone.web.aggregator.unit.AbstractUnitAggregator#aggregate(java.lang.String, 
	 * 		com.varone.web.metrics.bean.TimeValuePairBean, com.varone.web.vo.TimeValueVO)
	 */
	@Override
	public void aggregate(String format, TimeValuePairBean newPair, TimeValueVO currPair) {
		String[] valueAndCount = currPair.getValue().split(",");
		
		double newValue = Double.parseDouble(newPair.getValue());
		double currValue = Double.parseDouble(valueAndCount[0]);
		valueAndCount[1] = String.valueOf(Integer.valueOf(valueAndCount[1])+1);
		if(format.equals("MILLIS") || format.equals("OPS")){
			currPair.setValue(String.valueOf((long)newValue+currValue)+","+valueAndCount[1]);
		} else {
			currPair.setValue(String.valueOf(newValue+currValue)+","+valueAndCount[1]);
		}
	}
	
	public void calculateAvg(List<TimeValueVO> periodData){
		for(TimeValueVO timeValue: periodData){
			String[] valueAndCount = timeValue.getValue().split(",");
			double sum = Double.parseDouble(valueAndCount[0]);
			int count  = Integer.parseInt(valueAndCount[1]);
			if(sum > 0){
				double avg = sum / count;
				timeValue.setValue(String.valueOf(avg));
			} else {
				timeValue.setValue("0");
			}
		}
	}

}
