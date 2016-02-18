import React, { PropTypes } from 'react';
import Const from '../../utils/consts';
import ClusterSummaryBlock from './cluster-summary-block';
import c3 from 'c3';

class ClusterSummary extends React.Component {

  task_started_chart = null;
  executor_node_chart = null;

  static propTypes = {
    summaryInfos: PropTypes.array,
    onNodeClick: PropTypes.func,
    taskStartedNumByNode: PropTypes.array,
    executorNumByNode: PropTypes.array
  }

  handleSummaryBlockClick = title => {
    if (title === Const.node.summary.NODE_QTY) {
      this.props.onNodeClick();
    }
  }

  componentDidMount() {
    this.defaultCharting();
  }

  componentDidUpdate() {
    this.reloadCharting();
  }

  render() {
    const summaryContents = this.props.summaryInfos.map((summary) => {
      return (
        <ClusterSummaryBlock
          key={ summary.displayName }
          title={ summary.displayName }
          panel={ summary.panel }
          icon={ summary.icon }
          value={ summary.value }
          onSummaryClick={ this.handleSummaryBlockClick }/>
      );
    });

    let sumIdx = 0;
    const rowNum = Math.ceil(summaryContents.length / 2);
    const summaryRows = Array.from(new Array(rowNum), (x, i) => {
      return (
        <div key={ 'mr' + i } className='row'>
          <div className='col-md-6'>
            { summaryContents[sumIdx++] }
          </div>
          <div className='col-md-6'>
            { summaryContents[sumIdx++] }
          </div>
        </div>
      );
    });

    return (
      <div className='row summary-header'>
        <div className='col-xs-6 col-md-6'>
          { summaryRows }
        </div>
        <div className='col-xs-3 col-md-3'>
          <div className='panel panel-primary'>
            <div className='panel-heading'>
                <i className='fa fa-pie-chart fa-fw'></i> Task Started on Node
            </div>
            <div className='panel-body'>
                <div id='morris-task-donut'></div>
            </div>
          </div>
        </div>
        <div className='col-xs-3 col-md-3'>
          <div className='panel panel-primary'>
            <div className='panel-heading'>
                <i className='fa fa-pie-chart fa-fw'></i> Executor on Node
            </div>
            <div className='panel-body'>
                <div ref='morris-executor-dount' id='morris-executor-dount'></div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  reloadCharting() {
    this.task_started_chart.load({
      columns: this.props.taskStartedNumByNode
    });

    this.executor_node_chart.load({
      columns: this.props.executorNumByNode
    });
  }


  defaultCharting() {
    this.task_started_chart = c3.generate({
      bindto: '#morris-task-donut',
      data: {
        columns: this.props.taskStartedNumByNode,
        type: 'donut'
      },
      donut: {
        title: 'Task Started on Node'
      }
    });
    this.executor_node_chart = c3.generate({
      bindto: '#morris-executor-dount',
      data: {
        columns: this.props.executorNumByNode,
        type: 'donut'
      },
      donut: {
        title: 'Executor on Node'
      }
    });
  }
}

export default ClusterSummary;
