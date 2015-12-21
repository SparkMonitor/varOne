import React from 'react';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import ProcessBar from '../commons/process-bar';


class StageDetailsList extends React.Component {
	static propTypes = {
		stageDetails: React.PropTypes.array
	}

	render(){
    	return(
    	   <BootstrapTable
    	           data={this.props.stageDetails}
    	   	       striped={true}
    	           keyField="id">
    	   <TableHeaderColumn dataField="id" width="5%">Stage ID</TableHeaderColumn>
           <TableHeaderColumn dataField="index">Index</TableHeaderColumn>
           <TableHeaderColumn dataField="launchTime" width="10%">Launch Time</TableHeaderColumn>
           <TableHeaderColumn dataField="finishTime" width="10%">Finish Time</TableHeaderColumn>
           <TableHeaderColumn dataField="status">Status</TableHeaderColumn>
           <TableHeaderColumn dataField="duration" width="9%">Duration</TableHeaderColumn>
           <TableHeaderColumn dataField="locality" width="9%">Locality</TableHeaderColumn>
    	   </BootstrapTable>
    	);
    }
	
}

export default StageDetailsList;