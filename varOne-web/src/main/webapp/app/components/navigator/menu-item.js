import React from 'react';
import classSet from 'classnames';

var renderCollapseArrow = () =>{
  return(
    <span className="fa arrow"></span>
  );
};


export default class MenuItem extends React.Component {

  static propTypes = {
    text: React.PropTypes.string,
    icon: React.PropTypes.string,
    collapse: React.PropTypes.bool,
    children: React.PropTypes.array,
    jobItemClickCB: React.PropTypes.func,
    dimensionItemClickCB: React.PropTypes.func
  }

  handleSubMenuItemClick(e, applicationId){
    this.props.jobItemClickCB(applicationId);
    e.stopPropagation();
  }

  renderSubMenu(){
    let li = this.props.children.map((item) => {
      return (<li onClick={e => this.handleSubMenuItemClick(e, item)}><a href='#' style={{color: 'white'}}>{item}</a></li>);
    });
    return(
      <ul id="running-jobs" className="nav nav-second-level">
        {li}
      </ul>
    );
  }

  handleMenuItemClick(e){
    if(!this.props.collapse){
      this.props.dimensionItemClickCB(this.props.text);
    }
  }

  render(){
    let iconClass = classSet('fa', 'fa-fw', this.props.icon);
    let collapseArrow = this.props.collapse?renderCollapseArrow():null;
    let subMenu = this.props.collapse?this.renderSubMenu(this.props.children):null;
    return(
      <li onClick={e => this.handleMenuItemClick(e)}>
        <a href="#" style={{color: 'white'}}><i className={iconClass}></i> {this.props.text}{collapseArrow}</a>
        {subMenu}
      </li>
    );
  }
}
