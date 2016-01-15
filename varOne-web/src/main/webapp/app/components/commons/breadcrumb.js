import React, { PropTypes } from 'react';

export default class BreadCrumb extends React.Component {

  static propTypes = {
    content: PropTypes.array,
    active: PropTypes.string,
    onCrumbSelect: PropTypes.func
  }

  handleCrumbClick = e => {
    this.props.onCrumbSelect(e.currentTarget.innerText);
  }

  renderContent = () => {
    return this.props.content.map(item => {
      if (item === this.props.active) {
        return <li key={ item } className='active'>{ item }</li>;
      } else {
        return <li key={ item } onClick={ this.handleCrumbClick }><a href='#'>{ item }</a></li>;
      }
    });
  }

  render() {
    const content = this.renderContent();
    return (
      <ol className='breadcrumb'>
        { content }
      </ol>
    );
  }
}
