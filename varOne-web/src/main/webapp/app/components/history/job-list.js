import React from 'react';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import ProcessBar from '../commons/process-bar';
import { date_format, millis_format } from '../../utils/data-format';

class JobNameLink extends React.Component {

  static propTypes = {
    job: React.PropTypes.object,
    onLinkSelect: React.PropTypes.func
  }

  handleJobSelect = (e) => {
    const {id} = this.props.job;
    this.props.onLinkSelect(id);
  }

  render(){
    const {description} = this.props.job;
    return <a href="#" onClick={this.handleJobSelect}>{description}</a>;
  }
}

class JobList extends React.Component {

  static propTypes = {
    jobs: React.PropTypes.array,
    onJobSelect: React.PropTypes.func
  }

  stageFormatter = (cell, row) => {
    return <ProcessBar successPercent={row['stagesSuccessPercent']}>{cell}</ProcessBar>;
  }

  taskFormatter = (cell, row) => {
    return <ProcessBar successPercent={row['tasksSuccessPercent']}>{cell}</ProcessBar>;
  }

  jobNameFormatter = (cell, row) => {
    return <JobNameLink job={row} onLinkSelect={this.props.onJobSelect}/>;
  }
  dateUnitFormatter = (cell, row) => {
	 return date_format(cell);
  }
  timeUnitFormatter = (cell, row) => {
	 return millis_format(cell);
  }
  
  render(){

    return(
      <BootstrapTable
        data={this.props.jobs}
        striped={true}
        keyField="id">
        <TableHeaderColumn dataField="id" width="5%">Job ID</TableHeaderColumn>
        <TableHeaderColumn dataField="description" dataFormat={this.jobNameFormatter}>Description</TableHeaderColumn>
        <TableHeaderColumn dataField="submitTime" width="10%" dataFormat={this.dateUnitFormatter}>Submitted</TableHeaderColumn>
        <TableHeaderColumn dataField="duration" width="10%" dataFormat={this.timeUnitFormatter}>Duration</TableHeaderColumn>
        <TableHeaderColumn dataField="stagesSuccessVSTotal" dataFormat={this.stageFormatter}>Stages: Succeeded/Total</TableHeaderColumn>
        <TableHeaderColumn dataField="tasksSuccessVSTotal" dataFormat={this.taskFormatter}>Tasks (for all stages): Succeeded/Total</TableHeaderColumn>
      </BootstrapTable>
    );
  }
}

export default JobList;
