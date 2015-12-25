package com.varone.web.vo;

public class MetricCompletedTasksVO {
	public String metric;
	public long min;
	public double median;
	public long max;
	
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public long getMin() {
		return min;
	}
	public void setMin(long min) {
		this.min = min;
	}
	public double getMedian() {
		return median;
	}
	public void setMedian(double median) {
		this.median = median;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}

	
}
