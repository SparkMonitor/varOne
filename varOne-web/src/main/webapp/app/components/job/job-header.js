import React from 'react';

class JobHeader extends React.Component {

  static propTypes = {
    appId: React.PropTypes.string
  }

  render(){
    return(
      <div className="row">
          <div className="col-lg-12">
              <h1 className="page-header">{this.props.appId}&nbsp;&nbsp;
                <i className="fa fa-x fa-bar-chart" data-toggle="modal" data-target="#metricsModal"></i></h1>
          </div>
      </div>
    );
  }
}

export default JobHeader;
