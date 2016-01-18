import React, { PropTypes } from 'react';
import NodeMetric from './nodes-metric';

class NodeMetrics extends React.Component {

  static propTypes = {
    metrics: PropTypes.array
  }

  render() {
    const metricCharts = this.props.metrics.map((metric, i) => {
      return (
        <NodeMetric key={ i } metric={ metric }/>
      );
    });

    return (
      <div className='row'>
        <div className='col-lg-12'>
          { metricCharts }
        </div>
      </div>
    );
  }
}
export default NodeMetrics;
