import React from 'react';
import VarOneStore from '../../stores/varOne-store';
import VarOneAction from '../../actions/varOne-action';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
class Header extends React.Component {

  static getStores() {
    return [ VarOneStore ];
  }

  static getPropsFromStores() {
    return VarOneStore.getState();
  }

  handleModalClick = () => VarOneAction.changeStatus();

  render() {
    return (
      <ul className='nav navbar-top-links navbar-right'>
        <li className='dropdown'>
          <a className='dropdown-toggle varOne-setting'>
            <i className='fa fa-2x fa-cog fa-spin'
              data-toggle='modal'
              data-target='#varOneConfigModal'
              onClick={ this.handleModalClick }></i>
          </a>
        </li>
      </ul>
    );
  }
}

export default Header;
