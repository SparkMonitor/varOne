import React, { PropTypes } from 'react';
import Menu from './menu';
import MenuItem from './menu-item';
import VarOneAction from '../../actions/varOne-action';
import MenuStore from '../../stores/menu-store';
import MenuAction from '../../actions/menu-action';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
class Header extends React.Component {

  static propTypes = {
    runningJobs: PropTypes.string,
    leftSideMenu: PropTypes.array,
    jobItemClickCB: PropTypes.func,
    dimensionItemClickCB: PropTypes.func,
    errorFlag: PropTypes.boolean
  }

  static getStores() {
    return [ MenuStore ];
  }

  static getPropsFromStores() {
    return MenuStore.getState();
  }

  componentWillMount() {
    this.fetchInterval = setInterval(() => {
      MenuAction.fetchRunningJob();
    }, 2000);
  }

  componentWillUnmount() {
    clearInterval(this.fetchInterval);
  }

  handleModalClick = () => VarOneAction.changeStatus();
  handleLogModalClick = () => VarOneAction.showLogStatus();
  render() {
    let errorMessageicon = 'fa fa-2x fa-cog fa fa-bell';
    if (this.props.errorFlag === true) {
      errorMessageicon = 'fa fa-2x fa-warning';
    }
    const menuItems = this.props.leftSideMenu.map(item => {
      return (
        <MenuItem
          text={ item.name }
          icon={ item.icon }
          collapse={ item.collapse }
          children={ item.children }
          jobItemClickCB={ this.props.jobItemClickCB }
          dimensionItemClickCB={ this.props.dimensionItemClickCB }/>
      );
    });

    return (
      <div>
        <Menu>
          { menuItems }
        </Menu>
        <ul className='nav navbar-top-links navbar-right'>
          <li className='dropdown'>
            <a className='dropdown-toggle varOne-setting'>
              <i className={ errorMessageicon }
                data-toggle='modal'
                data-target='#varOneLogModal'
                onClick={ this.handleLogModalClick }></i>
            </a>
          </li>
          <li className='dropdown'>
            <a className='dropdown-toggle varOne-setting'>
              <i className='fa fa-2x fa-cog fa-spin'
                data-toggle='modal'
                data-target='#varOneConfigModal'
                onClick={ this.handleModalClick }></i>
            </a>
          </li>
        </ul>
      </div>
    );
  }
}

export default Header;
