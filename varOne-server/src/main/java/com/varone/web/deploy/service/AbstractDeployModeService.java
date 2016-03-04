package com.varone.web.deploy.service;

import java.io.Closeable;
import java.util.List;

public abstract class AbstractDeployModeService implements Closeable{
	
	public abstract List<String> getRunningSparkApplications() throws Exception;
	
	public abstract boolean isStartRunningSparkApplication(String applicationId) throws Exception;
	
	public abstract List<String> getSparkApplicationsByPeriod(long start, long end) throws Exception;
	

}
