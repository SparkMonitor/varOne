/**
 * 
 */
package com.varone.web.aggregator;

import java.util.ArrayList;
import java.util.Map.Entry;

import junit.framework.TestCase;

import com.varone.web.facade.SparkMonitorFacade;
import com.varone.web.vo.DefaultApplicationVO;
import com.varone.web.vo.DefaultTotalNodeVO;

/**
 * @author allen
 *
 */
public class SparkMonitorFacadeTest extends TestCase {

	/**
	 * @param name
	 */
	public SparkMonitorFacadeTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test method for {@link com.varone.web.aggregator.UIDataTransfer#getDefaultTotalNodeDashBoard()}.
	 * @throws Exception 
	 */
	public void testGetDefaultTotalNodeDashBoard() throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		DefaultTotalNodeVO result = facade.getDefaultClusterDashBoard(new ArrayList<String>(), "30m");
		
		assertNotNull(result);
		assertEquals(result.getExecutorNum(), 0);
		assertEquals(result.getJobNum(), 0);
		assertEquals(result.getNodeNum(), 5);
		assertEquals(result.getTaskNum(), 0);
		assertEquals(result.getExecutorNumByNode().size(), 5);
		assertEquals(result.getTaskStartedNumByNode().size(), 5);
		
		for(Integer value: result.getExecutorNumByNode().values()){
			assertEquals(value+0, 0);
		}
		
		for(Integer value: result.getTaskStartedNumByNode().values()){
			assertEquals(value+0, 0);
		}
	}
	
	public void testGetDefaultTotalNodeDashBoardWithRunningJob() throws Exception{
		SparkMonitorFacade facade = new SparkMonitorFacade();
		DefaultTotalNodeVO result = facade.getDefaultClusterDashBoard(new ArrayList<String>(), "30m");
		
		System.out.println("Node Num: " + result.getNodeNum());
		System.out.println("Spark Running Job Num: " + result.getJobNum());
		System.out.println("Executor Num: " + result.getExecutorNum());
		System.out.println("Task Num: " + result.getTaskNum());
		
		for(Entry<String, Integer> entry: result.getExecutorNumByNode().entrySet()){
			System.out.println(entry.getValue() + " executors running on " + entry.getKey());
		}
		
		for(Entry<String, Integer> entry: result.getTaskStartedNumByNode().entrySet()){
			System.out.println(entry.getValue() + " tasks completed on " + entry.getKey());
		}
		
	}
	
	public void testGetJobDashBoard() throws Exception{
		String runningAppId = "application_1439169262151_0587";
		SparkMonitorFacade facade = new SparkMonitorFacade();
		DefaultApplicationVO result = facade.getJobDashBoard(runningAppId, new ArrayList<String>());
		
		assertNotNull(result);
		
	}
	
	
}
