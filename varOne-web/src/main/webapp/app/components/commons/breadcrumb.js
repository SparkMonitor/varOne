import React from 'react';

export default class BreadCrumb extends React.Component {
  static propTypes = {
    content: React.PropTypes.array,
    active: React.PropTypes.string,
    onCrumbSelect: React.PropTypes.func
  }

  handleCrumbClick = (e) => {
    this.props.onCrumbSelect(e.currentTarget.innerText);
  }

  renderContent = () => {
    return this.props.content.map((item) => {
      if(item === this.props.active)
        return <li key={item} className="active">{item}</li>;
      else
        return <li key={item} onClick={this.handleCrumbClick}><a href="#">{item}</a></li>;
    });
  }

  render(){
    var content = this.renderContent();
    return <ol className="breadcrumb">
      {content}
    </ol>;
  }
}
