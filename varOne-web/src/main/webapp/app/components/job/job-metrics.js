import React, { PropTypes } from 'react';
import JobMetric from './job-metric';

class JobMetrics extends React.Component {

  static propTypes = {
    metrics: PropTypes.array
  }

  render() {
    const { metrics } = this.props;
    const metricCharts = [];
    for (const metric in metrics) {
      if (metrics.hasOwnProperty(metric)) {
        metricCharts.push((
          <JobMetric key={ metric } metric={ metrics[metric] }/>
        ));
      }
    }

    const rowNum = Math.ceil(metricCharts.length / 2);
    let mIdx = 0;

    const metricRows = Array.from(new Array(rowNum), (x, i) => {
      return (
        <div key={ 'mr' + i } className='row'>
          <div className='col-xs-6 col-md-6'>
            { metricCharts[mIdx++] }
          </div>
          <div className='col-xs-6 col-md-6'>
            { metricCharts[mIdx++] }
          </div>
        </div>
      );
    });

    return (
      <div className='row'>
        <div>
          { metricRows }
        </div>
      </div>
    );
  }
}

export default JobMetrics;
