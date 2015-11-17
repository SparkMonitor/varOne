import React from 'react';

export default class ClusterMetric extends React.Component{

  static propTypes = {
    metric: React.PropTypes.object
  }

  componentDidMount(){
    this._morrisCharting();
  }

  componentDidUpdate(){
    this._morrisCharting();
  }

  render(){
    return(
      <div className="panel panel-default">
          <div className="panel-heading">
              <i className="fa fa-bar-chart-o fa-fw"></i>{this.props.metric.title}
              <div className="pull-right"></div>
          </div>

          <div className="panel-body">
              <div id={this.props.metric.id}></div>
          </div>

      </div>
    );
  }

  _morrisCharting(){
    var morrisDount = document.getElementById(this.props.metric.id);
    while (morrisDount.firstChild) {
        morrisDount.removeChild(morrisDount.firstChild);
    }

    Morris.Bar({
      element: this.props.metric.id,
      data: this.props.metric.value,
      xkey: 'x',
      ykeys: ['y'],
      labels: [this.props.metric.title]
    });
  }
}
