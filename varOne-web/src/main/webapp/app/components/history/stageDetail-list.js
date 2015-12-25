import React from 'react';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import ProcessBar from '../commons/process-bar';
import { byte_format, percentage_format, millis_format, date_format } from '../../utils/data-format';

class StageDetailsList extends React.Component {
	static propTypes = {
		stageDetails: React.PropTypes.array,
		completeTaskSize: React.PropTypes.number,
		metricCompletedTasks: React.PropTypes.array,
		stageAggregator: React.PropTypes.array
	}
	memoryUnitFormatter = (cell, row) => {
		return byte_format(cell);
	}
	timeUnitFormatter = (cell, row) => {
		return millis_format(cell);
	}
	dateUnitFormatter = (cell, row) => {
		return date_format(cell);
	}
	summaryMetricsCompleteFormatter = (cell, row) => {
		console.log(typeof cell);
		if(row.metric == 'Duration' || row.metric == 'GC Time'){
			return millis_format(cell);
		}else if(row.metric == 'Input Size'){
			return byte_format(cell);
		}else{
			return cell;
		}
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
                    <TableHeaderColumn dataField="min" dataFormat={this.summaryMetricsCompleteFormatter}>Min</TableHeaderColumn>
                    <TableHeaderColumn dataField="median" dataFormat={this.summaryMetricsCompleteFormatter}>Median</TableHeaderColumn>
                    <TableHeaderColumn dataField="max" dataFormat={this.summaryMetricsCompleteFormatter}>Max</TableHeaderColumn>
           	    </BootstrapTable>
    			
    			<h2>Aggregated Metrics by Executor:</h2>

    	        <BootstrapTable
    	                  data={this.props.stageAggregator}
    	   	              striped={true}
    	                  pagination={true}
    	                  keyField="id">
	    	         <TableHeaderColumn dataField="executeId">Executor ID</TableHeaderColumn>
	                 <TableHeaderColumn dataField="address">Address</TableHeaderColumn>
	                 <TableHeaderColumn dataField="maxMemory" dataFormat={this.memoryUnitFormatter}>Max Memory</TableHeaderColumn>
	                 <TableHeaderColumn dataField="taskTime" dataFormat={this.timeUnitFormatter}>Task Time</TableHeaderColumn>
	                 <TableHeaderColumn dataField="totalTasks">Total Tasks</TableHeaderColumn>
	                 <TableHeaderColumn dataField="failedTasks">Failed Tasks</TableHeaderColumn>
	                 <TableHeaderColumn dataField="succeededTasks">Succeeded Tasks</TableHeaderColumn>
	                 <TableHeaderColumn dataField="inputSize" dataFormat={this.memoryUnitFormatter}>Input Size</TableHeaderColumn>
	                 <TableHeaderColumn dataField="records">Records</TableHeaderColumn>
    	         </BootstrapTable>
    	   
    	         <h2>Tasks:</h2>
    	         <BootstrapTable
                           data={this.props.stageDetails}
   	                       striped={true}
                           pagination={true}
                           keyField="id">
    	         
    	             <TableHeaderColumn dataField="index">Index</TableHeaderColumn>
	                 <TableHeaderColumn dataField="id">ID</TableHeaderColumn>
	                 <TableHeaderColumn dataField="attempt">Attempt</TableHeaderColumn>
	                 <TableHeaderColumn dataField="locality">Locality Level</TableHeaderColumn>
	                 <TableHeaderColumn dataField="executorID">Executor ID</TableHeaderColumn>
	                 <TableHeaderColumn dataField="host">Host</TableHeaderColumn>
                     <TableHeaderColumn dataField="launchTime" dataFormat={this.dateUnitFormatter}>Launch Time</TableHeaderColumn>
                     <TableHeaderColumn dataField="finishTime" dataFormat={this.dateUnitFormatter}>Finish Time</TableHeaderColumn>
                     <TableHeaderColumn dataField="runTime" dataFormat={this.timeUnitFormatter}>Duration</TableHeaderColumn>
                     <TableHeaderColumn dataField="gcTime" dataFormat={this.timeUnitFormatter}>GC Time</TableHeaderColumn>
                     <TableHeaderColumn dataField="resultSize">Result Size</TableHeaderColumn>
                     <TableHeaderColumn dataField="inputSize" dataFormat={this.memoryUnitFormatter}>Input Size</TableHeaderColumn>
                     <TableHeaderColumn dataField="records">Records</TableHeaderColumn>
                     <TableHeaderColumn dataField="status">Status</TableHeaderColumn>
                </BootstrapTable>
    	   </div>
    	  
    	   
    	);
    }
	
}

export default StageDetailsList;