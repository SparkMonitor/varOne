import React, { PropTypes } from 'react';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import ProcessBar from '../commons/process-bar';
import { millisFormatter } from '../../utils/data-format';

class StageNameLink extends React.Component {

  static propTypes = {
    stage: PropTypes.object,
    onLinkSelect: PropTypes.func
  }

  handleStageSelect = () => {
    const { id } = this.props.stage;
    this.props.onLinkSelect(id);
  }

  render() {
    const { description } = this.props.stage;
    return <a href='#' onClick={ this.handleStageSelect }>{ description }</a>;
  }
}

class StageList extends React.Component {

  static propTypes = {
    stages: React.PropTypes.array,
    onStageSelect: React.PropTypes.func
  }

  taskFormatter = (cell, row) => {
    return <ProcessBar successPercent={ row.tasksSuccessPercent }>{ cell }</ProcessBar>;
  }

  stageNameFormatter = (cell, row) => {
    return <StageNameLink stage={ row } onLinkSelect={ this.props.onStageSelect }/>;
  }

  timeUnitFormatter = (cell) => millisFormatter(cell);

  render() {
    return (
      <BootstrapTable
        data={ this.props.stages }
        striped
        keyField='id'>
        <TableHeaderColumn dataField='id' width='75'>Stage ID</TableHeaderColumn>
        <TableHeaderColumn
          dataField='description'
          dataFormat={ this.stageNameFormatter }>Description</TableHeaderColumn>
        <TableHeaderColumn
          dataField='submitTime'
          dataFormat={ this.timeUnitFormatter }>Submitted</TableHeaderColumn>
        <TableHeaderColumn
          dataField='duration'
          dataFormat={ this.timeUnitFormatter }>Duration</TableHeaderColumn>
        <TableHeaderColumn
          dataField='tasksSuccessVSTotal'
          dataFormat={ this.taskFormatter }>Tasks: Succeeded/Total</TableHeaderColumn>
        <TableHeaderColumn dataField='readAmount'>Input</TableHeaderColumn>
        <TableHeaderColumn dataField='writeAmount'>Ouput</TableHeaderColumn>
      </BootstrapTable>
    );
  }
}

export default StageList;
