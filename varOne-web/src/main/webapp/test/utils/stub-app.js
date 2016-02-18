/* eslint react/display-name: 0 */
import React, { Component } from 'react';

export default function stubApp() {
  return function (DecoratedComponent, props) {
    return class Wrapper extends Component {

      render() {
        const customProps = { ...this.props, ...props };
        return (
            <DecoratedComponent { ...customProps } />
        );
      }
    };
  };
}
