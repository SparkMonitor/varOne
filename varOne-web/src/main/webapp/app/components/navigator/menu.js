/* global $:true */
require('../../../node_modules/metismenu/dist/metisMenu.min.css');
require('metismenu');

import React, { PropTypes } from 'react';

export default class Menu extends React.Component {

  static propTypes = {
    children: PropTypes.element
  }

  constructor(props) {
    super(props);
  }

  componentDidUpdate() {
    $('#side-menu').metisMenu();
    $('#running-jobs').parent().addClass('active');
    $('#running-jobs').addClass('in');
  }

  render() {
    return (
      <ul className='nav' id='side-menu'>
        { this.props.children }
      </ul>
    );
  }
}
