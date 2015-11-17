import React from 'react';

export default class ProcessBar extends React.Component {

  static propTypes = {
    successPercent: React.PropTypes.string
  }

  render(){
    var style = {
      width: this.props.successPercent
    };

    var progressStyle = {
      marginBottom: '0px'
    };
    return (
      <div className="progress" style={progressStyle}>
        <div className="progress-bar progress-bar-success" role="progressbar" style={style}>
          {this.props.children}
        </div>
      </div>
    );
  }
}
