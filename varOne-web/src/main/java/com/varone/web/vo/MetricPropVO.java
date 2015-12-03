/**
 * 
 */
package com.varone.web.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author allen
 *
 */
@XmlRootElement
public class MetricPropVO {
	private String title;
	private String property;
	
	public MetricPropVO(){}
	
	public MetricPropVO(String title, String property){
		this.title = title;
		this.property = property;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

	
}