/**
 * 
 */
package com.varone.node;

/**
 * @author allen
 *
 */
public class MetricsProperties {
	/* *.sink.csv.period */
	private String csvPeriod;
	/* *.sink.csv.unit */
	private String csvUnit;
	/* *.sink.csv.directory */
	private String csvDir;
	
	public String getCsvPeriod() {
		return csvPeriod;
	}
	public void setCsvPeriod(String csvPeriod) {
		this.csvPeriod = csvPeriod;
	}
	public String getCsvUnit() {
		return csvUnit;
	}
	public void setCsvUnit(String csvUnit) {
		this.csvUnit = csvUnit;
	}
	public String getCsvDir() {
		return csvDir;
	}
	public void setCsvDir(String csvDir) {
		this.csvDir = csvDir;
	}
}
