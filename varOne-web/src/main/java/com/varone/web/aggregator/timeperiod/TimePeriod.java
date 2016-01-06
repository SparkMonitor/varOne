package com.varone.web.aggregator.timeperiod;


public enum TimePeriod {
	MINUTE_30("30m"),
	HOUR_1("1h"),
	HOUR_2("2h"),
	HOUR_6("6h");
	
	
	private String expression;
	
	TimePeriod(String expression){
		this.expression = expression;
	}
	
	public String expression(){
		return this.expression;
	}
	
	public static TimePeriod fromString(String text) {
        if (text != null) {
            for (TimePeriod time : TimePeriod.values()) {
                if (text.equals(time.expression())) {
                    return time;
                }
            }
        }
        throw new RuntimeException("Time period expression could not be matched: " + text);
    }
}
