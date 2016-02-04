/**
 * 
 */
package com.varone.web.aggregator.timeperiod;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author allen
 *
 */
public class TimePeriodTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.varone.web.aggregator.timeperiod.TimePeriod#fromString(java.lang.String)}.
	 */
	@Test
	public void testFromStringWith30m() {
		TimePeriod timePeriod = TimePeriod.fromString("30m");
		assertEquals(timePeriod, TimePeriod.MINUTE_30);
	}
	
	/**
	 * Test method for {@link com.varone.web.aggregator.timeperiod.TimePeriod#fromString(java.lang.String)}.
	 */
	@Test
	public void testFromStringWith1h() {
		TimePeriod timePeriod = TimePeriod.fromString("1h");
		assertEquals(timePeriod, TimePeriod.HOUR_1);
	}
	
	/**
	 * Test method for {@link com.varone.web.aggregator.timeperiod.TimePeriod#fromString(java.lang.String)}.
	 */
	@Test
	public void testFromStringWith2h() {
		TimePeriod timePeriod = TimePeriod.fromString("2h");
		assertEquals(timePeriod, TimePeriod.HOUR_2);
	}
	
	/**
	 * Test method for {@link com.varone.web.aggregator.timeperiod.TimePeriod#fromString(java.lang.String)}.
	 */
	@Test
	public void testFromStringWith6h() {
		TimePeriod timePeriod = TimePeriod.fromString("6h");
		assertEquals(timePeriod, TimePeriod.HOUR_6);
	}
	
	/**
	 * Test method for {@link com.varone.web.aggregator.timeperiod.TimePeriod#fromString(java.lang.String)}.
	 */
	@Test
	public void testFromStringWithInvalidStr() {
		try{
			TimePeriod.fromString("invalid");
			fail("Should not be passed");
		} catch(Exception e){
			assertEquals(e.getMessage(), "Time period expression could not be matched: invalid");
		}
	}

}
