/**
 * 
 */
package com.varone.node;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.hadoop.conf.Configuration;

import com.varone.node.utils.Consts;
import com.varone.node.utils.MetricsPropertiesParser;

/**
 * @author allen
 *
 */
public class SPMNode {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption("d", "metricsDir", true, "enter the directory which include metrics.properties");
		options.addOption("p", "port", true, "enter port number");
		options.addOption("t", "thread", true, "enter thread quantity");
		
		MetricsProperties loadProperties = null;
		HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
		
        String metricsDir = "";
		String port       = "";
		String thread     = "";
		
        try {
            cmd = parser.parse( options, args);
            metricsDir = cmd.getOptionValue("d", "");
            port = cmd.getOptionValue("p", Consts.VARONE_NODE_DEFAULT_PORT.toString());
            thread = cmd.getOptionValue("t", Consts.VARONE_NODE_DEFAULT_THREAD_NUM.toString());
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp( "varOned-{version}.jar", options );
            return;
        }
         
        if(metricsDir.isEmpty()) {
            formatter.printHelp( "varOned-{version}.jar", options );
            return;
        }
        
        try{
        	int portN = Integer.parseInt(port);
        	Integer.parseInt(thread);
        	if(portN < 0 || portN > 65535) throw new NumberFormatException("1 < port number < 65535");
        } catch (NumberFormatException e){
        	System.err.println(e.getMessage());
        	return;
        } 
        
        
        try{
	    	loadProperties = MetricsPropertiesParser.loadProperties(
					new File(metricsDir, "metrics.properties").getAbsolutePath());
	    	if(loadProperties.getCsvDir().equals("")){
	    		throw new Exception("The value of *.sink.csv.directory should not be empty");
	    	}
	    	
	    	if(!loadProperties.getCsvPeriod().equals("1")){
	    		throw new Exception("The value of *.sink.csv.period should be 1");
	    	} 
	    	
	    	if(!loadProperties.getCsvUnit().equals("seconds")){
	    		throw new Exception("The value of *.sink.csv.unit should be seconds");
	    	} 
        } catch (Exception e){
        	System.err.println(e.getMessage());
        	return;
        } 
        
        
		Configuration config = new Configuration();
		config.set(Consts.VARONE_SPARK_METRICS_PROP_DIR, metricsDir);
		config.set(Consts.VARONE_NODE_PORT, port);
		config.set(Consts.VARONE_NODE_THREAD_NUM, thread);
		config.set(Consts.SPARK_METRICS_CSV_PERIOD, loadProperties.getCsvPeriod());
		config.set(Consts.SPARK_METRICS_CSV_UNIT, loadProperties.getCsvUnit());
		config.set(Consts.SPARK_METRICS_CSV_DIR, loadProperties.getCsvDir());
		
		NodeMetricsService service = new NodeMetricsService(config);
		service.start();
	}

}
