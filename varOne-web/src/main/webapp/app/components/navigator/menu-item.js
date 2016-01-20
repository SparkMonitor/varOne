import React, { PropTypes } from 'react';

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
    if (this.props.collapse) {
      return (
        <ul id='running-jobs' className='dropdown-menu'>
          {
            this.props.children.map((item) => {
              return (
                <li onClick={ e => this.handleSubMenuItemClick(e, item) }>
                  <a href='#'>{ item }</a>
                </li>
              );
            })
          }
        </ul>
      );
    } else {
      return null;
    }
  }

  handleMenuItemClick = () => {
    if (!this.props.collapse) {
      this.props.dimensionItemClickCB(this.props.text);
    }
  }

  renderDisplayText() {
    if (this.props.collapse) {
      return (
        <a className='dropdown-toggle'
          data-toggle='dropdown' href='#' role='button'
          aria-haspopup='true' aria-expanded='false'>
          { this.props.text } <span className='caret'></span>
        </a>
      );
    } else {
      return <a href='#'>{ this.props.text }</a>;
    }
  }

  render() {
    const text = this.renderDisplayText();
    const subMenu = this.renderSubMenu();
    return (
      <li onClick={ this.handleMenuItemClick } role='presentation' className='nav-link'>
        { text }{ subMenu }
      </li>
    );
  }
}
