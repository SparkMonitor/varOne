import React from 'react';
import NodeMetric from './nodes-metric';

class NodeMetrics extends React.Component {


  render(){
    var metricCharts = this.props.metrics.map((metric, i) => {
      return(
        <NodeMetric key={i} metric={metric}/>
      );
    });

    return(
      <div className="row">
          <div className="col-lg-12">
              {metricCharts}
          </div>
      </div>
    );
  }
}

export default NodeMetrics;
