import React, { PropTypes } from 'react';
import Menu from './menu';
import MenuItem from './menu-item';
import MenuStore from '../../stores/menu-store';
import MenuAction from '../../actions/menu-action';

import connectToStores from 'alt/utils/connectToStores';

@connectToStores
class LeftSide extends React.Component {

  fetchInterval = null

  static propTypes = {
    runningJobs: PropTypes.string,
    jobItemClickCB: PropTypes.func,
    dimensionItemClickCB: PropTypes.func,
    leftSideMenu: PropTypes.array
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

  constructor(props) {
    super(props);
  }

  render() {
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
      <div className='navbar-default sidebar' role='navigation'>
        <div className='sidebar-nav navbar-collapse'>
          <Menu>
            { menuItems }
          </Menu>
        </div>
      </div>
    );
  }
}
export default LeftSide;
