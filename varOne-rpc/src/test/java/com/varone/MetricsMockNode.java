/**
 * 
 */
package com.varone;

import org.apache.hadoop.conf.Configuration;

/**
 * @author allen
 *
 */
public class MetricsMockNode {

	/**
	 * @throws Exception 
	 * 
	 */
	public static void main(String[] args) throws Exception {
		
		Configuration config = new Configuration();
		
		MetricsMockService service = new MetricsMockService(config);
		service.start();
	}

}
