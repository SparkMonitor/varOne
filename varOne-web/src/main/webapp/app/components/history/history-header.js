import React from 'react';

export default class HistoryHeader extends React.Component {

  render(){
    var headerStyle = {
      borderBottom: '1px solid #e7ecf1'
    };

    return (
      <div style={headerStyle}>
        {this.props.children}
      </div>
    );
  }
}
