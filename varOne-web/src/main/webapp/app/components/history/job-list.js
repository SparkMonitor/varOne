import React, { PropTypes } from 'react';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import ProcessBar from '../commons/process-bar';
import { dateFormatter, millisFormatter } from '../../utils/data-format';

class JobNameLink extends React.Component {

  static propTypes = {
    job: PropTypes.object,
    onLinkSelect: PropTypes.func
  }

  handleJobSelect = () => {
    const { id } = this.props.job;
    this.props.onLinkSelect(id);
  }

  render() {
    const { description } = this.props.job;
    return (<a href='#' onClick={ this.handleJobSelect }>{ description }</a>);
  }
}

class JobList extends React.Component {

  static propTypes = {
    jobs: PropTypes.array,
    onJobSelect: PropTypes.func
  }

  stageFormatter = (cell, row) => {
    return (<ProcessBar successPercent={ row.stagesSuccessPercent }>{ cell }</ProcessBar>);
  }

  taskFormatter = (cell, row) => {
    return (<ProcessBar successPercent={ row.tasksSuccessPercent }>{ cell }</ProcessBar>);
  }

  jobNameFormatter = (cell, row) => {
    return <JobNameLink job={ row } onLinkSelect={ this.props.onJobSelect }/>;
  }

  dateUnitFormatter = cell => dateFormatter(cell);

  timeUnitFormatter = cell => millisFormatter(cell);

  render() {
    return (
      <BootstrapTable
        data={ this.props.jobs }
        striped
        keyField='id'>
        <TableHeaderColumn dataField='id' width='60'>Job ID</TableHeaderColumn>
        <TableHeaderColumn
          dataField='description'
          dataFormat={ this.jobNameFormatter }>Description</TableHeaderColumn>
        <TableHeaderColumn
          dataField='submitTime'
          dataFormat={ this.dateUnitFormatter }>Submitted</TableHeaderColumn>
        <TableHeaderColumn
          dataField='duration'
          dataFormat={ this.timeUnitFormatter }>Duration</TableHeaderColumn>
        <TableHeaderColumn
          dataField='stagesSuccessVSTotal'
          dataFormat={ this.stageFormatter }>Stages: Succeeded/Total</TableHeaderColumn>
        <TableHeaderColumn
          dataField='tasksSuccessVSTotal'
          dataFormat={ this.taskFormatter }>
          Tasks (for all stages): Succeeded/Total
        </TableHeaderColumn>
      </BootstrapTable>
    );
  }
}

export default JobList;
