import React, { PropTypes } from 'react';

export default class ProcessBar extends React.Component {

  static propTypes = {
    successPercent: PropTypes.string,
    children: PropTypes.element
  }

  render() {
    const style = {
      width: this.props.successPercent
    };

    const progressStyle = {
      marginBottom: '0px'
    };
    return (
      <div className='progress' style={ progressStyle }>
        <div
          className='progress-bar progress-bar-success'
          role='progressbar'
          style= { style }>
          { this.props.children }
        </div>
      </div>
    );
  }
}
