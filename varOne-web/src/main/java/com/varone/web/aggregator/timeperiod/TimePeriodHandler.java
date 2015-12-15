/**
 * 
 */
package com.varone.web.aggregator.timeperiod;

import java.util.ArrayList;
import java.util.List;

import com.varone.node.MetricsProperties;
import com.varone.web.metrics.bean.TimeValuePairBean;

/**
 * @author allen
 *
 */
public class TimePeriodHandler {
	
	/** 
	 * The unit of the periods of plot point
	 * 30 seconds is default by varOne
	 **/
	public final static long DEFAULT_PLOT_PERIOD = 30;
	
	private String metricUnit;
	private int metricPeriod;
	
	public TimePeriodHandler(MetricsProperties metricsProperties){
		this.metricUnit = metricsProperties.getCsvUnit();
		this.metricPeriod = Integer.valueOf(metricsProperties.getCsvPeriod());
	}
	
	public long[] transferToLongPeriod(String periodExpression) {
		TimePeriod timePeriod = TimePeriod.fromString(periodExpression);
		long currentTimeMillis = System.currentTimeMillis();
		
		if(timePeriod.equals(TimePeriod.MINUTE_30)){
			long thrityMinutesAgoMillis = currentTimeMillis - 30*60*1000;
			return new long[]{thrityMinutesAgoMillis, currentTimeMillis};
		} else if(timePeriod.equals(TimePeriod.HOUR_1)){
			long oneHourAgoMillis = currentTimeMillis - 60*60*1000;
			return new long[]{oneHourAgoMillis, currentTimeMillis};
		} else if(timePeriod.equals(TimePeriod.HOUR_2)){
			long twoHourAgoMillis = currentTimeMillis - 120*60*1000;
			return new long[]{twoHourAgoMillis, currentTimeMillis};
		} else if(timePeriod.equals(TimePeriod.HOUR_6)){
			long sixHourAgoMillis = currentTimeMillis - 360*60*1000;
			return new long[]{sixHourAgoMillis, currentTimeMillis};
		} else {
			throw new RuntimeException("No support time period: " + periodExpression);
		}
	}
	
	public List<Long> getDefaultPlotPointInPeriod(long[] startAndEndTime){
		return this.getPlotPointInPeriod(startAndEndTime, TimePeriodHandler.DEFAULT_PLOT_PERIOD);
	}
	
	public List<Long> getPlotPointInPeriod(long[] startAndEndTime, long periodOfPlotPoint){
		List<Long> result = new ArrayList<Long>();
		
		long base = 0;
		
		if(this.metricUnit.equals("seconds"))
			base = 1000;
		else
			throw new RuntimeException("Only support seconds period unit");
		
		long start = startAndEndTime[0] / base;
		long end = startAndEndTime[1] / base;
				
		for(long i=start;i<=end;i+=periodOfPlotPoint){
			result.add(i);
		}
		
		return result;
	}
	
	public List<TimeValuePairBean> ingestPeriodData(List<TimeValuePairBean> timeValuePairs, List<Long> plotPointInPeriod){
		PeriodDataIngestor dataIngestor = this.createPeriodDataIngestor(timeValuePairs);
		return dataIngestor.ingest(plotPointInPeriod);
	}
	
	
	private PeriodDataIngestor createPeriodDataIngestor(List<TimeValuePairBean> dataSet){
		if(this.metricUnit.equals("seconds"))
			return new SecondPeriodIngestor(dataSet);
		else
			throw new RuntimeException("Only support seconds period unit");
	}
}
