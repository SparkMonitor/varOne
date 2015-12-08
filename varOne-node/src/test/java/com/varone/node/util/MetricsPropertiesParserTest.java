/**
 * 
 */
package com.varone.node.util;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import com.varone.node.MetricsProperties;
import com.varone.node.utils.MetricsPropertiesParser;

/**
 * @author allen
 *
 */
public class MetricsPropertiesParserTest extends TestCase{
	
	private String validPropPath;
	private String withoutPeriodPropPath;
	private String withoutUnitPropPath;
	private String withoutDirPropPath;

	public MetricsPropertiesParserTest(String name) {
		super(name);
		this.validPropPath = this.getClass().getResource("/metrics.valid.properties").getPath();
		this.withoutPeriodPropPath = this.getClass().getResource("/metrics.withoutPeriod.properties").getPath();
		this.withoutUnitPropPath = this.getClass().getResource("/metrics.withoutUnit.properties").getPath();
		this.withoutDirPropPath = this.getClass().getResource("/metrics.withoutDir.properties").getPath();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test method for {@link com.varone.node.utils.MetricsPropertiesParser#loadProperties(java.lang.String)}.
	 */
	@Test
	public void testLoadValidProperties() {
		try {
			MetricsProperties props = MetricsPropertiesParser.loadProperties(this.validPropPath);
			
			assertNotNull(props);
			assertNotNull(props.getCsvDir());
			assertNotNull(props.getCsvPeriod());
			assertNotNull(props.getCsvUnit());
			assertEquals(props.getCsvDir(), "/tmp/csvsink");
			assertEquals(props.getCsvPeriod(), "1");
			assertEquals(props.getCsvUnit(), "seconds");
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link com.varone.node.utils.MetricsPropertiesParser#loadProperties(java.lang.String)}.
	 */
	@Test
	public void testLoadInvalidPropertiesWithoutPeriodProp() {
		try {
			MetricsPropertiesParser.loadProperties(this.withoutPeriodPropPath);
			fail("Should not be parsering successful");
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().indexOf("Can not find '*.sink.csv.period' in metrics properties file") > -1);
		}		
	}
	
	/**
	 * Test method for {@link com.varone.node.utils.MetricsPropertiesParser#loadProperties(java.lang.String)}.
	 */
	@Test
	public void testLoadInvalidPropertiesWithoutUnitProp() {
		try {
			MetricsPropertiesParser.loadProperties(this.withoutUnitPropPath);
			fail("Should not be parsering successful");
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().indexOf("Can not find '*.sink.csv.unit' in metrics properties file") > -1);
		}		
	}
	
	/**
	 * Test method for {@link com.varone.node.utils.MetricsPropertiesParser#loadProperties(java.lang.String)}.
	 */
	@Test
	public void testLoadInvalidPropertiesWithoutDirProp() {
		try {
			MetricsPropertiesParser.loadProperties(this.withoutDirPropPath);
			fail("Should not be parsering successful");
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().indexOf("Can not find '*.sink.csv.directory' in metrics properties file") > -1);
		}		
	}

}
