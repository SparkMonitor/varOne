import React from 'react';
import classSet from 'classnames';

class ClusterSummaryBlock extends React.Component {
  render(){

    let iconClass = classSet("fa", "fa-5x", this.props.icon);
    let panelClass = classSet("panel", this.props.panel);
    return(
      <div className="col-lg-3 col-md-6">
          <div className={panelClass}>
              <div className="panel-heading">
                  <div className="row">
                      <div className="col-xs-3">
                          <i className={iconClass}></i>
                      </div>
                      <div className="col-xs-9 text-right">
                          <div className="huge">{this.props.value}</div>
                          <div>{this.props.title}</div>
                      </div>
                  </div>
              </div>
              <a href="#" onClick={(e) => this.props.onSummaryClick(this.props.title)}>
                  <div className="panel-footer">
                      <span className="pull-left">View Details</span>
                      <span className="pull-right"><i className="fa fa-arrow-circle-right"></i></span>
                      <div className="clearfix"></div>
                  </div>
              </a>
          </div>
      </div>
    );
  }
}
ClusterSummaryBlock.propTypes = {
  title: React.PropTypes.string,
  icon: React.PropTypes.string,
  panel: React.PropTypes.string,
  onSummaryClick: React.PropTypes.func
};
export default ClusterSummaryBlock;
