import React from 'react';

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
