require('../../../node_modules/metismenu/dist/metisMenu.min.css');

import React from 'react';
import metisMenu from 'metismenu';

export default class Menu extends React.Component {

  constructor(props){
    super(props);
  }

  componentDidUpdate(){
    // $('#side-menu').empty();
    $('#side-menu').metisMenu();
    $("#running-jobs").parent().addClass("active");
    $("#running-jobs").addClass("in");
  }

  render(){
    return (
      <ul className="nav" id="side-menu">
          {this.props.children}
      </ul>
    );
  }
}
