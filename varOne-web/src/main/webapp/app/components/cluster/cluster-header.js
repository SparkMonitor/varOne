import React from 'react';

class ClusterHeader extends React.Component {
  render(){
    return(
      <div className="row">
          <div className="col-lg-12">
              <h1 className="page-header">Dashboard&nbsp;&nbsp;
                <i className="fa fa-x fa-bar-chart" data-toggle="modal" data-target="#metricsModal"></i>
              </h1>
          </div>
      </div>
    );
  }
}

export default ClusterHeader;
