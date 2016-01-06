import React from 'react';
import c3 from 'c3';
import { byte_format, percentage_format, millis_format } from '../../utils/data-format';

export default class ClusterMetric extends React.Component{

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
    var columns = [this.props.metric.x];

    for(let i=0;i<this.props.metric.value.length;i++){
      columns.push(this.props.metric.value[i]);
    }
    this.cluster_metrics_chart.load({
        columns: columns
    });
  }

  defaultCharting(){
    var columns = [this.props.metric.x];

    for(let i=0;i<this.props.metric.value.length;i++){
      columns.push(this.props.metric.value[i]);
    }

    var formatter;
    if(this.props.metric.format === "BYTE")
      formatter = byte_format;
    else if(this.props.metric.format === "PERCENTAGE")
      formatter = percentage_format;
    else if(this.props.metric.format === "MILLIS")
      formatter = millis_format;

    this.cluster_metrics_chart = c3.generate({
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
            },
            y: {
              padding: {bottom: 1},
              tick: {
                format: formatter
              }
            }
        }
    });
  }
}
