/**
 * 
 */
package com.haredb.sparkmonitor.node;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.hadoop.conf.Configuration;

import com.haredb.sparkmonitor.node.utils.Consts;

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
		options.addOption("d", "metricsDir", true, "enter spark metrics directory");
		options.addOption("p", "port", true, "enter port number");
		options.addOption("t", "thread", true, "enter thread quantity");
		
		HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
		
        String metricsDir = "";
		String port       = "";
		String thread     = "";
		
        try {
            cmd = parser.parse( options, args);
            metricsDir = cmd.getOptionValue("d", "");
            port = cmd.getOptionValue("p", Consts.HARE_SPARK_MONITOR_NODE_DEFAULT_PORT.toString());
            thread = cmd.getOptionValue("t", Consts.HARE_SPARK_MONITOR_NODE_DEFAULT_THREAD.toString());
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp( "spmn.jar", options );
            return;
        }
         
        if(metricsDir.isEmpty()) {
            formatter.printHelp( "spmn.jar", options );
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
        
		Configuration config = new Configuration();
		config.set(Consts.HARE_SPARK_METRICS_CSV_DIR, metricsDir);
		config.set(Consts.HARE_SPARK_MONITOR_NODE_PORT, port);
		config.set(Consts.HARE_SPARK_MONITOR_NODE_THREAD, thread);
		
		NodeMetricsService service = new NodeMetricsService(config);
		service.start();
	}

}
