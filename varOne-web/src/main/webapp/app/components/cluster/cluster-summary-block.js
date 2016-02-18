import React, { PropTypes } from 'react';
import classSet from 'classnames';

class ClusterSummaryBlock extends React.Component {

  static propTypes = {
    title: PropTypes.string,
    icon: PropTypes.string,
    panel: PropTypes.string,
    value: PropTypes.number,
    onSummaryClick: PropTypes.func
  }

  handleSummaryClick = () => this.props.onSummaryClick(this.props.title);

  render() {
    const iconClass = classSet('fa', 'fa-5x', this.props.icon);
    const panelClass = classSet('panel', this.props.panel);
    return (
      <div className={ panelClass }>
        <div className='panel-heading'>
          <div className='row'>
            <div className='col-xs-3'>
              <i className={ iconClass }></i>
            </div>
            <div className='col-xs-9 text-right'>
              <div className='huge'>{ this.props.value }</div>
              <div className='medium'>{ this.props.title }</div>
            </div>
          </div>
        </div>
        <a href='#' onClick={ this.handleSummaryClick }>
          <div className='panel-footer'>
            <span className='pull-left'>View Details</span>
            <span className='pull-right'><i className='fa fa-arrow-circle-right'></i></span>
            <div className='clearfix'></div>
          </div>
        </a>
      </div>
    );
  }
}
export default ClusterSummaryBlock;
