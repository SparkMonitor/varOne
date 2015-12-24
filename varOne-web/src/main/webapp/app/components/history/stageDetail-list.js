import React from 'react';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import ProcessBar from '../commons/process-bar';


class StageDetailsList extends React.Component {
	static propTypes = {
		stageDetails: React.PropTypes.array,
		completeTaskSize: React.PropTypes.func,
		metricCompletedTasks: React.PropTypes.array,
		stageAggregator: React.PropTypes.array
	}

	render(){
    	return(
    		<div>
    		
    			<h2>Summary Metrics for {this.props.completeTaskSize} Completed Tasks</h2>
    			
    			<BootstrapTable
                          data={this.props.metricCompletedTasks}
 	                      striped={true}
    					  pagination={true}
                          keyField="id">
	                <TableHeaderColumn dataField="metric">Metric</TableHeaderColumn>
                    <TableHeaderColumn dataField="min">Min</TableHeaderColumn>
                    <TableHeaderColumn dataField="median">Median</TableHeaderColumn>
                    <TableHeaderColumn dataField="max">Max</TableHeaderColumn>
           	    </BootstrapTable>
    			
    			<h2>Aggregated Metrics by Executor:</h2>
    	        <BootstrapTable
    	                  data={this.props.stageAggregator}
    	   	              striped={true}
    	                  pagination={true}
    	                  keyField="id">
	    	         <TableHeaderColumn dataField="executeId">Executor ID</TableHeaderColumn>
	                 <TableHeaderColumn dataField="address">Address</TableHeaderColumn>
	                 <TableHeaderColumn dataField="maxMemory">Max Memory</TableHeaderColumn>
	                 <TableHeaderColumn dataField="taskTime">Task Time</TableHeaderColumn>
	                 <TableHeaderColumn dataField="totalTasks">Total Tasks</TableHeaderColumn>
	                 <TableHeaderColumn dataField="failedTasks">Failed Tasks</TableHeaderColumn>
	                 <TableHeaderColumn dataField="succeededTasks">Succeeded Tasks</TableHeaderColumn>
	                 <TableHeaderColumn dataField="inputSizeAndrecords">Input Size/Records</TableHeaderColumn>
    	         </BootstrapTable>
    	   
    	         <h2>Tasks:</h2>
    	         <BootstrapTable
                           data={this.props.stageDetails}
   	                       striped={true}
                           pagination={true}
                           keyField="id">
    	         
    	             <TableHeaderColumn dataField="index">Index</TableHeaderColumn>
	                 <TableHeaderColumn dataField="id">Stage ID</TableHeaderColumn>
	                 <TableHeaderColumn dataField="attempt">Attempt</TableHeaderColumn>
	                 <TableHeaderColumn dataField="locality">Locality Level</TableHeaderColumn>
	                 <TableHeaderColumn dataField="executorIDAndHost">Executor ID/Host</TableHeaderColumn>
                     <TableHeaderColumn dataField="launchTime">Launch Time</TableHeaderColumn>
                     <TableHeaderColumn dataField="finishTime">Finish Time</TableHeaderColumn>
                     <TableHeaderColumn dataField="runTime">Duration</TableHeaderColumn>
                     <TableHeaderColumn dataField="gcTime">GC Time</TableHeaderColumn>
                     <TableHeaderColumn dataField="resultSize">Result Size</TableHeaderColumn>
                     <TableHeaderColumn dataField="inputSizeAndRecords">Input Size/Records</TableHeaderColumn>
                     <TableHeaderColumn dataField="status">Status</TableHeaderColumn>
                </BootstrapTable>
    	   </div>
    	  
    	   
    	);
    }
	
}

export default StageDetailsList;