import React from 'react';
import c3 from 'c3';

export default class JobMetric extends React.Component{

  cluster_metrics_chart = null;

  static propTypes = {
    metric: React.PropTypes.object
  }

  componentDidMount(){
    this.defaultCharting();
  }

  componentDidUpdate(){
    this.reloadCharting();
  }

  render(){
    return(
      <div className="panel panel-default">
          <div className="panel-heading">
              <i className="fa fa-bar-chart-o fa-fw"></i>{this.props.metric.title}
              <div className="pull-right"></div>
          </div>

          <div className="panel-body">
              <div id={this.props.metric.id}></div>
          </div>

      </div>
    );
  }

  reloadCharting(){
    this.cluster_metrics_chart.load({
        columns: this.props.metric.value
    });
  }

  defaultCharting(){
    this.cluster_metrics_chart = c3.generate({
        bindto: '#'+this.props.metric.id,
        data: {
            columns: this.props.metric.value,
            type: 'bar'
        }
    });
  }
}
