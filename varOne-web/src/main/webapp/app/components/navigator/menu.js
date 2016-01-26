import React, { PropTypes } from 'react';

export default class Menu extends React.Component {

  static propTypes = {
    children: PropTypes.element
  }

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <ul className='nav navbar-top-links navbar-left'>
        { this.props.children }
      </ul>
    );
  }
}
