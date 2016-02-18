import React, { PropTypes } from 'react';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import { dateFormatter } from '../../utils/data-format';

class AppNameLink extends React.Component {

  static propTypes = {
    app: PropTypes.object,
    onLinkSelect: PropTypes.func
  }

  handleAppSelect = () => {
    const { id } = this.props.app;
    this.props.onLinkSelect(id);
  }

  render() {
    const { name } = this.props.app;
    return (<a href='#' onClick={ this.handleAppSelect }>{ name }</a>);
  }
}

class HistoryList extends React.Component {

  static propTypes = {
    histories: PropTypes.array,
    onHistorySelect: PropTypes.func
  }

  appNameFormatter = (cell, row) => {
    return (<AppNameLink app={ row } onLinkSelect={ this.props.onHistorySelect }/>);
  }

  dateUnitFormatter = cell => {
    return dateFormatter(cell);
  }

  render() {
    return (
      <BootstrapTable
        data={ this.props.histories }
        striped
        pagination
        keyField='name'>
        <TableHeaderColumn
          dataField='name'
          dataFormat={ this.appNameFormatter }>App Name</TableHeaderColumn>
        <TableHeaderColumn dataField='id'>App ID</TableHeaderColumn>
        <TableHeaderColumn
          dataField='startTime'
          dataFormat={ this.dateUnitFormatter }>Start Time</TableHeaderColumn>
        <TableHeaderColumn
          dataField='endTime'
          dataFormat={ this.dateUnitFormatter }>End Time</TableHeaderColumn>
        <TableHeaderColumn dataField='user'>User</TableHeaderColumn>
      </BootstrapTable>
    );
  }
}

export default HistoryList;
