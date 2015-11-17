import React from 'react';

class NodeMetrics extends React.Component {


  render(){

    var metricsRows = this.props.metrics.map((metric) => {
      return(
        <tr key={metric.id}>
          <td>{metric.title}</td>
          <td>{metric.value}</td>
        </tr>
      );
    });

    return(
      <div className="row">
        <div className="col-lg-12">
          <div className="panel panel-default">
            <div className="panel-heading">Metrics Table</div>
            <div className="panel-body">
              <table className="table table-bordered">
                <thead>
                  <tr>
                    <th>Metric Name</th>
                    <th>Metric Value</th>
                  </tr>
                </thead>
                <tbody>
                  {metricsRows}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default NodeMetrics;
