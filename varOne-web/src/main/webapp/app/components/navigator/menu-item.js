import React, { PropTypes } from 'react';
import classSet from 'classnames';

const renderCollapseArrow = () => {
  return (
    <span className='fa arrow'></span>
  );
};


export default class MenuItem extends React.Component {
  static propTypes = {
    text: PropTypes.string,
    icon: PropTypes.string,
    collapse: PropTypes.bool,
    children: PropTypes.array,
    jobItemClickCB: PropTypes.func,
    dimensionItemClickCB: PropTypes.func
  }

  handleSubMenuItemClick(e, applicationId) {
    this.props.jobItemClickCB(applicationId);
    e.stopPropagation();
  }

  renderSubMenu() {
    const li = this.props.children.map((item) => {
      return (
        <li onClick={ e => this.handleSubMenuItemClick(e, item) }>
          <a href='#' style={ { color: 'white' } }>{ item }</a>
        </li>
      );
    });
    return (
      <ul id='running-jobs' className='nav nav-second-level'>
        { li }
      </ul>
    );
  }

  handleMenuItemClick = () => {
    if (!this.props.collapse) {
      this.props.dimensionItemClickCB(this.props.text);
    }
  }

  render() {
    const iconClass = classSet('fa', 'fa-fw', this.props.icon);
    const collapseArrow = this.props.collapse ? renderCollapseArrow() : null;
    const subMenu = this.props.collapse ? this.renderSubMenu(this.props.children) : null;
    return (
      <li onClick={ this.handleMenuItemClick }>
        <a href='#' style={ { color: 'white' } }>
          <i className={ iconClass }></i> { this.props.text }{ collapseArrow }
        </a>
        { subMenu }
      </li>
    );
  }
}
