import React from 'react';

class Header extends React.Component {
  render(){
    return(
      <ul className="nav navbar-top-links navbar-right">
        <li className="dropdown">
            <a className="dropdown-toggle varOne-setting">
                <i className="fa fa-2x fa-cog fa-spin" data-toggle="modal" data-target="#varOneConfigModal"></i>
            </a>
        </li>
      </ul>
    );
  }
}

export default Header;
