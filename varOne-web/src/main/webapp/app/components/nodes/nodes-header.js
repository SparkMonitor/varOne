import React from 'react';
import TimePeriodPill from '../commons/time-period-pills'

class NodeHeader extends React.Component {

  static propTypes = {
    nodes: React.PropTypes.array,
    period: React.PropTypes.string,
    onNodeSelect: React.PropTypes.func,
    onPeriodSelect: React.PropTypes.func
  }

  state = {
    selected: 'Select Node'
  }

  constructor(props){
    super(props);
  }

  handleNodeItemClick = (e) => {
    this.props.onNodeSelect(e.target.text);
    this.setState({
      selected: e.target.text
    });
  }

  render(){

    let nodes = this.props.nodes.map((node) => {
      return (
        <li key={node} onClick={this.handleNodeItemClick}><a href="#">{node}</a></li>
      );
    });

    return(
      <div className="row">
          <div className="col-md-3">
              <h1 className="page-header">
                Nodes&nbsp;&nbsp;
                <div className="btn-group">
                  <button type="button" className="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    {this.state.selected} <span className="caret"></span>
                  </button>
                  <ul className="dropdown-menu">
                    {nodes}
                  </ul>
                  &nbsp;
                  <i className="fa fa-x fa-pencil-square-o" data-toggle="modal" data-target="#metricsModal"></i>
                </div>
              </h1>
          </div>
          <div className="col-md-5">
              <h3 className="page-header">
                <TimePeriodPill active={this.props.period} onPeriodSelect={this.props.onPeriodSelect}/>
              </h3>
          </div>
      </div>
    );
  }
}

export default NodeHeader;
