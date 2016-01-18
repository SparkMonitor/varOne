import React from 'react';

class Brand extends React.Component {
  render() {
    return (
      <div className='navbar-header'>
          <button type='button' className='navbar-toggle'
            data-toggle='collapse' data-target='.navbar-collapse'>
              <span className='sr-only'>Toggle navigation</span>
              <span className='icon-bar'></span>
              <span className='icon-bar'></span>
              <span className='icon-bar'></span>
          </button>
          <a className='navbar-brand'
            href='index.html' style={ { color: 'white' } }>Spark Monitor</a>
      </div>
    );
  }
}

export default Brand;
