import React, { PropTypes } from 'react';
import c3 from 'c3';
import { byteFormatter, percentageFormatter, millisFormatter } from '../../utils/data-format';

export default class ClusterMetric extends React.Component {

  cluster_metrics_chart = null;

  static propTypes = {
    metric: PropTypes.object
  }

  componentDidMount() {
    this.defaultCharting();
  }

  componentDidUpdate() {
    this.reloadCharting();
  }

  render() {
    return (
      <div className='panel panel-info'>
        <div className='panel-heading'>
          <i className='fa fa-bar-chart-o fa-fw'></i>{ this.props.metric.title }
          <div className='pull-right'></div>
        </div>

        <div className='panel-body'>
          <div id={ this.props.metric.id }></div>
        </div>
      </div>
    );
  }

  reloadCharting() {
    const columns = [ this.props.metric.x ];

    for (let i = 0; i < this.props.metric.value.length; i++) {
      columns.push(this.props.metric.value[i]);
    }
    this.cluster_metrics_chart.load({ columns });
  }

  defaultCharting() {
    let formatter;
    const columns = [ this.props.metric.x ];

    for (let i = 0; i < this.props.metric.value.length; i++) {
      columns.push(this.props.metric.value[i]);
    }

    if (this.props.metric.format === 'BYTE') formatter = byteFormatter;
    else if (this.props.metric.format === 'PERCENTAGE') formatter = percentageFormatter;
    else if (this.props.metric.format === 'MILLIS') formatter = millisFormatter;

    this.cluster_metrics_chart = c3.generate({
      bindto: '#' + this.props.metric.id,
      data: {
        x: 'x',
        columns
      },
      axis: {
        x: {
          type: 'timeseries',
          tick: {
            format: '%H:%M:%S'
          }
        },
        y: {
          padding: { bottom: 1 },
          tick: {
            format: formatter
          }
        }
      }
    });
  }
}
