import React from 'react';
import VarOneStore from '../../stores/varOne-store';
import VarOneAction from '../../actions/varOne-action';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
class Header extends React.Component {

  static getStores(props) {
    return [VarOneStore];
  }

  static getPropsFromStores(props) {
    return VarOneStore.getState();
  }

  handleModalClick = e => {
    VarOneAction.changeStatus();
  }

  render(){
    return(
      <ul className="nav navbar-top-links navbar-right">
        <li className="dropdown">
            <a className="dropdown-toggle varOne-setting">
                <i className="fa fa-2x fa-cog fa-spin" data-toggle="modal" onClick={this.handleModalClick} data-target="#varOneConfigModal"></i>
            </a>
        </li>
      </ul>
    );
  }
}

export default Header;
