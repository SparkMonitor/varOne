/**
 * 
 */
package com.varone.node.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.varone.node.MetricsProperties;

/**
 * @author allen
 *
 */
public class MetricsPropertiesParser {
	
	public final static String CSV_PERIOD = "*.sink.csv.period";
	public final static String CSV_UNIT = "*.sink.csv.unit";
	public final static String CSV_DIR = "*.sink.csv.directory";
	
	public static MetricsProperties loadProperties(String filePath) {
		MetricsProperties metricsProps = null;
		Properties prop = new Properties();
		InputStream input = null;
		
		try{
			input = new FileInputStream(filePath);

			prop.load(input);
			
			metricsProps = new MetricsProperties();
			
			String csvPeriod;
			if(null == (csvPeriod = prop.getProperty(CSV_PERIOD))){
				throw new RuntimeException("Can not find '"+CSV_PERIOD+"' in metrics properties file: " + filePath);
			} else {
				metricsProps.setCsvPeriod(csvPeriod);
			}
			
			String csvUnit;
			if(null == (csvUnit = prop.getProperty(CSV_UNIT))){
				throw new RuntimeException("Can not find '"+CSV_UNIT+"' in metrics properties file: " + filePath);
			} else {
				metricsProps.setCsvUnit(csvUnit);
			}
			
			String csvDir;
			if(null == (csvDir = prop.getProperty(CSV_DIR))){
				throw new RuntimeException("Can not find '"+CSV_DIR+"' in metrics properties file: " + filePath);
			} else {
				metricsProps.setCsvDir(csvDir);
			}
			
			
		} catch(IOException e){
			throw new RuntimeException(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch(IOException e){
					
				}
			}
		}
		
		return metricsProps;
		
	}
}
