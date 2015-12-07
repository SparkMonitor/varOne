import React from 'react';
import JobMetric from './job-metric';
import c3 from 'c3';

class JobMetrics extends React.Component {

  task_started_chart = null;
  executor_node_chart = null;

  componentDidMount(){
    this.defaultCharting();
  }

  componentDidUpdate(){
    this.reloadCharting();
  }

  render(){

    var metricCharts = [];
    for(let metric in this.props.metrics){
      metricCharts.push((
        <JobMetric key={metric} metric={this.props.metrics[metric]}/>
      ));
    }

    return(
      <div className="row">
          <div className="col-lg-8">
              {metricCharts}
          </div>

          <div className="col-lg-4">
              <div className="panel panel-default">
                  <div className="panel-heading">
                      <i className="fa fa-pie-chart fa-fw"></i> Task Started on Node
                  </div>

                  <div className="panel-body">
                      <div id="morris-task-donut"></div>
                  </div>
              </div>

              <div className="panel panel-default">
                  <div className="panel-heading">
                      <i className="fa fa-pie-chart fa-fw"></i> Executor on Node
                  </div>
                  <div className="panel-body">
                      <div id="morris-executor-dount"></div>
                  </div>
              </div>
          </div>
      </div>
    );
  }

  reloadCharting(){
    this.task_started_chart.load({
        columns: this.props.taskStartedNumByNode
    });

    this.executor_node_chart.load({
        columns: this.props.executorNumByNode
    });
  }

  defaultCharting(){
    this.task_started_chart = c3.generate({
        bindto: '#morris-task-donut',
        data: {
            columns: this.props.taskStartedNumByNode,
            type : 'donut'
        },
        donut: {
            title: 'Task Started on Node'
        }
    });
    this.executor_node_chart = c3.generate({
        bindto: '#morris-executor-dount',
        data: {
            columns: this.props.executorNumByNode,
            type : 'donut'
        },
        donut: {
            title: 'Executor on Node'
        }
    });

  }
}

export default JobMetrics;
