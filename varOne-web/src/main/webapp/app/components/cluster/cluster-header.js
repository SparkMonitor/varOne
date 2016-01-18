import React, { PropTypes } from 'react';
import TimePeriodPill from '../commons/time-period-pills';

class ClusterHeader extends React.Component {

  static propTypes = {
    period: PropTypes.string,
    onPeriodSelect: PropTypes.func
  }

  render() {
    return (
      <div className='row'>
          <div className='col-md-2'>
              <h2 className='page-header'>Dashboard&nbsp;&nbsp;
                <i className='fa fa-x fa-bar-chart'
                  data-toggle='modal'
                  data-target='#metricsModal'></i>
              </h2>
          </div>
          <div className='col-md-5'>
              <h4 className='page-header'>
                <TimePeriodPill
                  active={ this.props.period }
                  onPeriodSelect={ this.props.onPeriodSelect }/>
              </h4>
          </div>
      </div>
    );
  }
}

export default ClusterHeader;
