import React, { PropTypes } from 'react';
import classSet from 'classnames';

class JobSummaryBlock extends React.Component {

  static propTypes = {
    title: PropTypes.string,
    icon: PropTypes.string,
    panel: PropTypes.string,
    value: PropTypes.number
  }

  render() {
    const iconClass = classSet('fa', 'fa-5x', this.props.icon);
    const panelClass = classSet('panel', this.props.panel);
    return (
      <div className='col-lg-3 col-md-6'>
          <div className={ panelClass }>
              <div className='panel-heading'>
                  <div className='row'>
                      <div className='col-xs-3'>
                          <i className={ iconClass }></i>
                      </div>
                      <div className='col-xs-9 text-right'>
                          <div className='huge'>{ this.props.value }</div>
                          <div>{ this.props.title }</div>
                      </div>
                  </div>
              </div>
              <a href='#'>
                  <div className='panel-footer'>
                      <span className='pull-left'>View Details</span>
                      <span className='pull-right'>
                        <i className='fa fa-arrow-circle-right'></i>
                      </span>
                      <div className='clearfix'></div>
                  </div>
              </a>
          </div>
      </div>
    );
  }
}
export default JobSummaryBlock;
