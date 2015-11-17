import React from 'react';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import ProcessBar from '../commons/process-bar';

class StageNameLink extends React.Component {

  static propTypes = {
    stage: React.PropTypes.object,
    onLinkSelect: React.PropTypes.func
  }

  handleStageSelect = (e) => {
    const {id} = this.props.stage;
    this.props.onLinkSelect(id);
  }

  render(){
    const {description} = this.props.stage;
    return <a href="#" onClick={this.handleStageSelect}>{description}</a>;
  }
}

class StageList extends React.Component {

  static propTypes = {
    stages: React.PropTypes.array,
    onStageSelect: React.PropTypes.func
  }

  taskFormatter = (cell, row) => {
    return <ProcessBar successPercent={row['tasksSuccessPercent']}>{cell}</ProcessBar>;
  }

  stageNameFormatter = (cell, row) => {
    return <StageNameLink stage={row} onLinkSelect={this.props.onStageSelect}/>;
  }

  render(){
    return(
      <BootstrapTable
        data={this.props.stages}
        striped={true}
        keyField="id">
        <TableHeaderColumn dataField="id" width="5%">Stage ID</TableHeaderColumn>
        <TableHeaderColumn dataField="description" dataFormat={this.stageNameFormatter}>Description</TableHeaderColumn>
        <TableHeaderColumn dataField="submitTime" width="10%">Submitted</TableHeaderColumn>
        <TableHeaderColumn dataField="duration" width="10%">Duration</TableHeaderColumn>
        <TableHeaderColumn dataField="tasksSuccessVSTotal" dataFormat={this.taskFormatter}>Tasks: Succeeded/Total</TableHeaderColumn>
        <TableHeaderColumn dataField="readAmount" width="9%">Input</TableHeaderColumn>
        <TableHeaderColumn dataField="writeAmount" width="9%">Ouput</TableHeaderColumn>
      </BootstrapTable>
    );
  }
}

export default StageList;
