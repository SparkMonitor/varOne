import React from 'react';
import Const from '../../utils/consts';

export default class TimePeriodPills extends React.Component {
  static propTypes = {
    active: React.PropTypes.string,
    onPeriodSelect: React.PropTypes.func
  }

  handlePillClick = (e) => {
    this.props.onPeriodSelect(e.currentTarget.textContent);
  }

  renderContent = () => {
    return Const.shared.timeperiod.map((period) => {
      if(period === this.props.active)
        return <li key={period} role="presentation" className="active"><a href="#">{period}</a></li>;
      else
        return <li key={period} role="presentation" onClick={this.handlePillClick}><a href="#">{period}</a></li>;
    });
  }

  render(){
    var content = this.renderContent();
    return <ul className="nav nav-pills">
      {content}
    </ul>
  }
}
