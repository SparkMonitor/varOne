/**
 * 
 */
package com.varone.web.aggregator.unit;


/**
 * @author allen
 *
 */
public class TimeUnitTransfer {
	
	
	public String transferToAxisX(long value) {
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		int length = String.valueOf(value).length();
		
		//without millisecond
		if(length == 10) {
//			return sdf.format(value * 1000);
			return String.valueOf(value * 1000);
		}
		
		return null;
	}

}
