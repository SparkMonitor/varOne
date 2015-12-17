import React from 'react';
import c3 from 'c3';

export default class NodeMetric extends React.Component{

  node_metrics_chart = null;

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
    var columns = [this.props.metric.x, this.props.metric.value];

    var currNode = this.props.metric.value[0];
    var datas = this.node_metrics_chart.data();
    var unload = datas.filter(data => data.id !== currNode).map(data => data.id);

    this.node_metrics_chart.load({
        columns: columns,
        unload: unload
    });
  }

  defaultCharting(){
    var columns = [this.props.metric.x, this.props.metric.value];
    this.node_metrics_chart = c3.generate({
        bindto: '#'+this.props.metric.id,
        data: {
            x: 'x',
            columns: columns
        },
        axis: {
            x: {
                type: 'timeseries',
                tick: {
                    format: '%H:%M:%S'
                }
            }
        }
    });
  }
}
