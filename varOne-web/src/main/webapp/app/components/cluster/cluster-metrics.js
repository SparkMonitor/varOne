import React from 'react';
import ClusterMetric from './cluster-metric';

class ClusterMetrics extends React.Component {

  componentDidMount(){
    this.defaultCharting();
  }

  componentDidUpdate(){
    this.defaultCharting();
  }
  render(){

    var metricCharts = [];
    for(let metric in this.props.metrics){
      metricCharts.push((
        <ClusterMetric key={metric} metric={this.props.metrics[metric]}/>
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
                      <div ref="morris-executor-dount" id="morris-executor-dount"></div>
                  </div>
              </div>
          </div>
      </div>
    );
  }

  defaultCharting(){
    var morrisDount = document.getElementById("morris-executor-dount");
    while (morrisDount.firstChild) {
        morrisDount.removeChild(morrisDount.firstChild);
    }
    morrisDount = document.getElementById("morris-task-donut");
    while (morrisDount.firstChild) {
        morrisDount.removeChild(morrisDount.firstChild);
    }

    Morris.Donut({
        element: 'morris-task-donut',
        data: this.props.taskStartedNumByNode,
        resize: true
    });

    Morris.Donut({
        element: 'morris-executor-dount',
        data: this.props.executorNumByNode,
        resize: true
    });
  }
}

export default ClusterMetrics;
