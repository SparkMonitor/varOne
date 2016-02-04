package com.varone.web.aggregator.timeperiod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.varone.conf.VarOneConfiguration;
import com.varone.node.MetricsProperties;
import com.varone.web.metrics.bean.TimeValuePairBean;
import com.varone.web.util.VarOneEnv;

/**
 * @author allen
 *
 */
public class TimePeriodHandlerTest {

	private TimePeriodHandler timePeriodHandler;

	@Before
	public void setUp() throws Exception {
		VarOneConfiguration conf = VarOneConfiguration.create();
		VarOneEnv env = new VarOneEnv(conf);
		MetricsProperties metricsConfiguration = env.loadMetricsConfiguration();
		this.timePeriodHandler = new TimePeriodHandler(metricsConfiguration);
	}

	@Test
	public void testTransfer30mToLongPeriod() {
		String periodExpression = "30m";
		long[] transferToLongPeriod = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		assertEquals(transferToLongPeriod.length, 2);
		assertEquals(transferToLongPeriod[1] - transferToLongPeriod[0], 30*60*1000);
	}
	
	@Test
	public void testTransfer1hToLongPeriod() {
		String periodExpression = "1h";
		long[] transferToLongPeriod = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		assertEquals(transferToLongPeriod.length, 2);
		assertEquals(transferToLongPeriod[1] - transferToLongPeriod[0], 60*60*1000);
	}
	
	@Test
	public void testTransfer2hToLongPeriod() {
		String periodExpression = "2h";
		long[] transferToLongPeriod = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		assertEquals(transferToLongPeriod.length, 2);
		assertEquals(transferToLongPeriod[1] - transferToLongPeriod[0], 120*60*1000);
	}
	
	@Test
	public void testTransfer6hToLongPeriod() {
		String periodExpression = "6h";
		long[] transferToLongPeriod = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		assertEquals(transferToLongPeriod.length, 2);
		assertEquals(transferToLongPeriod[1] - transferToLongPeriod[0], 360*60*1000);
	}
	
	@Test
	public void testTransferAnInvalidPeriod() {
		String periodExpression = "8h";
		try{
			this.timePeriodHandler.transferToLongPeriod(periodExpression);
			fail("8h is a invalid period expression");
		} catch(RuntimeException e){
			assertEquals(e.getMessage(), "Time period expression could not be matched: "+periodExpression);
		}
	}

	@Test
	/** 
	 * Default period of plot point is 30 seconds
	 * User request period is 30 minutes
	 * Testing period is fourty minutes ago to twenty minutes ago
	 *     
	 *                   current  
	 *      |-----30m-----|
	 * |---20m---|
	 *      |****|
	 * 
	 * star means that data which we desired.
	 * **/
	public void testIngestDefaultPeriodData_1() {
		
		final int fourtyMinuteAgo = 40;
		final int twentyMinuteAgo = 20;
		
		String periodExpression = "30m";
		long[] startAndEndTime = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		// current time
		long currentTimeMillis = startAndEndTime[1];
		// back to 40 minutes ago
		long fourtyMinutesAgoMillis = currentTimeMillis - fourtyMinuteAgo*60*1000;
		// back to 20 minutes ago
		long twentyMinutesAgoMillis = currentTimeMillis - twentyMinuteAgo*60*1000;
		
		List<TimeValuePairBean> timeValuePairs =
				this.genFakeMetricDataByPerSecond(fourtyMinutesAgoMillis, twentyMinutesAgoMillis);
		
		assertEquals(timeValuePairs.size(), 60*(fourtyMinuteAgo-twentyMinuteAgo)+1);
		
		List<Long> plotPointInPeriod = this.timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
		List<TimeValuePairBean> result = this.timePeriodHandler.ingestPeriodData(timeValuePairs, plotPointInPeriod);
		
		assertEquals((30-20)*2+1, result.size());
		
		
		for(int i=0;i<result.size();i++){
			TimeValuePairBean first = result.get(i);
			TimeValuePairBean second = (i+1>=result.size())?null:result.get(i+1);
			
			if(null != second){
				assertEquals(second.getTime() - first.getTime(), TimePeriodHandler.DEFAULT_PLOT_PERIOD);
			}
		}	
	}
	
	@Test
	/** 
	 * Default period of plot point is 30 seconds
	 * User request period is 30 minutes
	 * Testing period is thirty minutes ago to ten minutes ago
	 *     
	 *                   current  
	 *      |-----30m-----|
	 *      |---20m---|
	 *      |*********|
	 * 
	 * star means that data which we desired.
	 * **/
	public void testIngestDefaultPeriodData_2() {
		
		final int thirtyMinuteAgo = 30;
		final int tenMinuteAgo = 10;
		
		String periodExpression = "30m";
		long[] startAndEndTime = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		// current time
		long currentTimeMillis = startAndEndTime[1];
		// back to 30 minutes ago
		long thirtyMinutesAgoMillis = currentTimeMillis - thirtyMinuteAgo*60*1000;
		// back to 10 minutes ago
		long tenMinutesAgoMillis = currentTimeMillis - tenMinuteAgo*60*1000;
		
		List<TimeValuePairBean> timeValuePairs =
				this.genFakeMetricDataByPerSecond(thirtyMinutesAgoMillis, tenMinutesAgoMillis);
		
		assertEquals(timeValuePairs.size(), 60*(thirtyMinuteAgo-tenMinuteAgo)+1);
		
		List<Long> plotPointInPeriod = this.timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
		List<TimeValuePairBean> result = this.timePeriodHandler.ingestPeriodData(timeValuePairs, plotPointInPeriod);

		assertEquals(2*20+1, result.size());
		
		
		for(int i=0;i<result.size();i++){
			TimeValuePairBean first = result.get(i);
			TimeValuePairBean second = (i+1>=result.size())?null:result.get(i+1);
			
			if(null != second){
				assertEquals(second.getTime() - first.getTime(), TimePeriodHandler.DEFAULT_PLOT_PERIOD);
			}
		}	
	}
	
	@Test
	/** 
	 * Default period of plot point is 30 seconds
	 * User request period is 30 minutes
	 * Testing period is ten minutes ago to ten minutes later
	 *     
	 *                 current  
	 *      |-----30m-----|
	 *               |---20m---|
	 *               |*********|
	 * 
	 * star means that data which we desired.
	 * **/
	public void testIngestDefaultPeriodData_3() {
		
		final int tenMinuteAgo = 10;
		final int tenMinuteLater = 10;
		
		String periodExpression = "30m";
		long[] startAndEndTime = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		// current time
		long currentTimeMillis = startAndEndTime[1];
		// back to 10 minutes ago
		long tenMinutesAgoMillis = currentTimeMillis - tenMinuteAgo*60*1000;
		// go to 10 minutes after
		long tenMinutesLaterMillis = tenMinuteLater*60*1000 + currentTimeMillis;
		
		List<TimeValuePairBean> timeValuePairs =
				this.genFakeMetricDataByPerSecond(tenMinutesAgoMillis, tenMinutesLaterMillis);
		
		assertEquals(timeValuePairs.size(), 60*(tenMinuteLater+tenMinuteLater)+1);
		
		List<Long> plotPointInPeriod = this.timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
		List<TimeValuePairBean> result = this.timePeriodHandler.ingestPeriodData(timeValuePairs, plotPointInPeriod);

		assertEquals(2*10+1, result.size());
		
		
		for(int i=0;i<result.size();i++){
			TimeValuePairBean first = result.get(i);
			TimeValuePairBean second = (i+1>=result.size())?null:result.get(i+1);
			
			if(null != second){
				assertEquals(second.getTime() - first.getTime(), TimePeriodHandler.DEFAULT_PLOT_PERIOD);
			}
		}	
	}
	
	@Test
	/** 
	 * Default period of plot point is 30 seconds
	 * User request period is 30 minutes
	 * Testing period is fifty minutes ago to fourty minutes ago
	 *     
	 *                   current  
	 *          |-----30m-----|
	 * |--10m--|
	 * 
	 * star means that data which we desired.
	 * 
	 * 
	 * **/
	public void testIngestDefaultPeriodData_4() {
		
		final int fiftyMinuteAgo = 50;
		final int fourtyMinuteAgo = 40;
		
		String periodExpression = "30m";
		long[] startAndEndTime = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		// current time
		long currentTimeMillis = startAndEndTime[1];
		// back to 50 minutes ago
		long fiftyMinutesAgoMillis = currentTimeMillis - fiftyMinuteAgo*60*1000;
		// back to 40 minutes ago
		long fourtyMinutesAgoMillis = currentTimeMillis - fourtyMinuteAgo*60*1000;
		
		List<TimeValuePairBean> timeValuePairs =
				this.genFakeMetricDataByPerSecond(fiftyMinutesAgoMillis, fourtyMinutesAgoMillis);
		
		assertEquals(timeValuePairs.size(), 60*(fiftyMinuteAgo-fourtyMinuteAgo)+1);
		
		List<Long> plotPointInPeriod = this.timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
		List<TimeValuePairBean> result = this.timePeriodHandler.ingestPeriodData(timeValuePairs, plotPointInPeriod);

		assertEquals(0, result.size());
		
		
		for(int i=0;i<result.size();i++){
			TimeValuePairBean first = result.get(i);
			TimeValuePairBean second = (i+1>=result.size())?null:result.get(i+1);
			
			if(null != second){
				assertEquals(second.getTime() - first.getTime(), TimePeriodHandler.DEFAULT_PLOT_PERIOD);
			}
		}
	}
	
	@Test
	/** 
	 * Default period of plot point is 30 seconds
	 * User request period is 1 hour
	 * Testing period is seventy minutes ago to fifty minutes ago
	 *     
	 *                   current  
	 *      |-----1h-----|
	 * |---20m---|
	 *      |****|
	 * 
	 * star means that data which we desired.
	 * **/
	public void testIngestDefaultPeriodData_5() {
		
		final int seventyMinuteAgo = 70;
		final int fiftyMinuteAgo = 50;
		
		String periodExpression = "1h";
		long[] startAndEndTime = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		// current time
		long currentTimeMillis = startAndEndTime[1];
		// back to 70 minutes ago
		long seventyMinutesAgoMillis = currentTimeMillis - seventyMinuteAgo*60*1000;
		// back to 50 minutes ago
		long fiftyMinutesAgoMillis = currentTimeMillis - fiftyMinuteAgo*60*1000;
		
		List<TimeValuePairBean> timeValuePairs =
				this.genFakeMetricDataByPerSecond(seventyMinutesAgoMillis, fiftyMinutesAgoMillis);
		
		assertEquals(timeValuePairs.size(), 60*(seventyMinuteAgo-fiftyMinuteAgo)+1);
		
		List<Long> plotPointInPeriod = this.timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
		List<TimeValuePairBean> result = this.timePeriodHandler.ingestPeriodData(timeValuePairs, plotPointInPeriod);
		
		assertEquals((10)*2+1, result.size());
		
		
		for(int i=0;i<result.size();i++){
			TimeValuePairBean first = result.get(i);
			TimeValuePairBean second = (i+1>=result.size())?null:result.get(i+1);
			
			if(null != second){
				assertEquals(second.getTime() - first.getTime(), TimePeriodHandler.DEFAULT_PLOT_PERIOD);
			}
		}	
	}
	
	@Test
	/** 
	 * Default period of plot point is 30 seconds
	 * User request period is 1 hour
	 * Testing period is sixty minute ago to fourty minutes ago
	 *     
	 *                   current  
	 *      |-----1h-----|
	 *      |---20m---|
	 *      |*********|
	 * 
	 * star means that data which we desired.
	 * **/
	public void testIngestDefaultPeriodData_6() {
		
		final int sixtytyMinuteAgo = 60;
		final int fourtyMinuteAgo = 40;
		
		String periodExpression = "1h";
		long[] startAndEndTime = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		// current time
		long currentTimeMillis = startAndEndTime[1];
		// back to 60 minutes ago
		long sixtyMinutesAgoMillis = currentTimeMillis - sixtytyMinuteAgo*60*1000;
		// back to 40 minutes ago
		long fourtyMinutesAgoMillis = currentTimeMillis - fourtyMinuteAgo*60*1000;
		
		List<TimeValuePairBean> timeValuePairs =
				this.genFakeMetricDataByPerSecond(sixtyMinutesAgoMillis, fourtyMinutesAgoMillis);
		
		assertEquals(timeValuePairs.size(), 60*(sixtytyMinuteAgo-fourtyMinuteAgo)+1);
		
		List<Long> plotPointInPeriod = this.timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
		List<TimeValuePairBean> result = this.timePeriodHandler.ingestPeriodData(timeValuePairs, plotPointInPeriod);
		
		assertEquals((20)*2+1, result.size());
		
		
		for(int i=0;i<result.size();i++){
			TimeValuePairBean first = result.get(i);
			TimeValuePairBean second = (i+1>=result.size())?null:result.get(i+1);
			
			if(null != second){
				assertEquals(second.getTime() - first.getTime(), TimePeriodHandler.DEFAULT_PLOT_PERIOD);
			}
		}	
	}
	
	@Test
	/** 
	 * Default period of plot point is 30 seconds
	 * User request period is 1 hour
	 * Testing period is ten minutes ago to ten minutes later
	 *     
	 *                 current  
	 *      |-----1h-----|
	 *               |---20m---|
	 *               |*********|
	 * 
	 * star means that data which we desired.
	 * **/
	public void testIngestDefaultPeriodData_7() {
		
		final int tenMinuteAgo = 10;
		final int tenMinuteLater = 10;
		
		String periodExpression = "1h";
		long[] startAndEndTime = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		// current time
		long currentTimeMillis = startAndEndTime[1];
		// back to 10 minutes ago
		long tenMinutesAgoMillis = currentTimeMillis - tenMinuteAgo*60*1000;
		// go to 10 minutes after
		long tenMinutesLaterMillis = tenMinuteLater*60*1000 + currentTimeMillis;
		
		List<TimeValuePairBean> timeValuePairs =
				this.genFakeMetricDataByPerSecond(tenMinutesAgoMillis, tenMinutesLaterMillis);
		
		assertEquals(timeValuePairs.size(), 60*(tenMinuteLater+tenMinuteLater)+1);
		
		List<Long> plotPointInPeriod = this.timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
		List<TimeValuePairBean> result = this.timePeriodHandler.ingestPeriodData(timeValuePairs, plotPointInPeriod);

		assertEquals(2*10+1, result.size());
		
		
		for(int i=0;i<result.size();i++){
			TimeValuePairBean first = result.get(i);
			TimeValuePairBean second = (i+1>=result.size())?null:result.get(i+1);
			
			if(null != second){
				assertEquals(second.getTime() - first.getTime(), TimePeriodHandler.DEFAULT_PLOT_PERIOD);
			}
		}	
	}
	
	@Test
	/** 
	 * Default period of plot point is 30 seconds
	 * User request period is 1 hour
	 * Testing period is eighty minutes ago to seventy minutes ago
	 *     
	 *                   current  
	 *          |-----1h-----|
	 * |--10m--|
	 * 
	 * star means that data which we desired.
	 * 
	 * 
	 * **/
	public void testIngestDefaultPeriodData_8() {
		
		final int eightyMinuteAgo = 80;
		final int seventyMinuteAgo = 70;
		
		String periodExpression = "30m";
		long[] startAndEndTime = this.timePeriodHandler.transferToLongPeriod(periodExpression);
		// current time
		long currentTimeMillis = startAndEndTime[1];
		// back to 80 minutes ago
		long eightyMinutesAgoMillis = currentTimeMillis - eightyMinuteAgo*60*1000;
		// back to 70 minutes ago
		long seventyMinutesAgoMillis = currentTimeMillis - seventyMinuteAgo*60*1000;
		
		List<TimeValuePairBean> timeValuePairs =
				this.genFakeMetricDataByPerSecond(eightyMinutesAgoMillis, seventyMinutesAgoMillis);
		
		assertEquals(timeValuePairs.size(), 60*(eightyMinuteAgo-seventyMinuteAgo)+1);
		
		List<Long> plotPointInPeriod = this.timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
		List<TimeValuePairBean> result = this.timePeriodHandler.ingestPeriodData(timeValuePairs, plotPointInPeriod);
		
		assertEquals(0, result.size());
		
		
		for(int i=0;i<result.size();i++){
			TimeValuePairBean first = result.get(i);
			TimeValuePairBean second = (i+1>=result.size())?null:result.get(i+1);
			
			if(null != second){
				assertEquals(second.getTime() - first.getTime(), TimePeriodHandler.DEFAULT_PLOT_PERIOD);
			}
		}
	}
	
	/**
	 * @author allen
	 *
	 * if you want to test the condition as following defined in metrics.properties:  
	 *   *.sink.csv.period=1
	 *   *.sink.csv.unit=seconds 
	 * then 
	 *   call this function to generate fake metrics data per second  
	 * 
	 **/
	private List<TimeValuePairBean> genFakeMetricDataByPerSecond(long start, long end){
		final int period = 1;
		List<TimeValuePairBean> fakeData = new ArrayList<TimeValuePairBean>();
		
		/** Drop milliseconds,
		 *  @see com.varone.web.aggregator.timeperiod.SecondPeriodIngestor#ingest(java.util.List)
		 **/
		long startWithoutMilli = start / 1000;
		long endWithoutMilli = end / 1000;
		
		for(long i=startWithoutMilli;i<=endWithoutMilli;i+=period){
			TimeValuePairBean fakeBean = new TimeValuePairBean();
			fakeBean.setTime(i);
			fakeBean.setValue("");
			fakeData.add(fakeBean);
		}
		return fakeData;
	}

}
