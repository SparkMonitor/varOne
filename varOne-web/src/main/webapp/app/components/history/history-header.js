import React, { PropTypes } from 'react';

export default class HistoryHeader extends React.Component {

  static propTypes = {
    children: PropTypes.element
  }

  render() {
    const headerStyle = {
      borderBottom: '1px solid #e7ecf1'
    };

    return (
      <div style={ headerStyle }>
        { this.props.children }
      </div>
    );
  }
}
