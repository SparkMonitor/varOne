import React from 'react';
import Const from '../../utils/consts';

export default class MetricsSettingModal extends React.Component{

  static propTypes = {
    modalTarget: React.PropTypes.string,
    onModalSubmit: React.PropTypes.func
  }

  constructor(props) {
		super(props);
  }

  handleSubmitBtnClick(e){
    var selectMetrics = [];

    for(let metric in this.refs){
      let checkbox = this.refs[metric].getDOMNode();
      if(checkbox.checked){
        selectMetrics.push(checkbox.value);
      }
    }

    this.props.onModalSubmit(selectMetrics);
  }

  render(){
    var rowNum = Const.metrics.length/4+(Const.metrics.length%4==0?0:1);

    var checkboxs = Const.metrics.map((metric) => {
      return (
        <div className="col-md-3">
          <div className="checkbox">
            <label><input ref={metric.name} type="checkbox" value={metric.value}/>{metric.name}</label>
          </div>
        </div>
      );
    });

    var rows = [];
    for(let i=1;i<=rowNum;i++){
      let checkboxInRow = checkboxs.slice(4*i-3, 4*i+1);
      let aRow = (<div className="row">{checkboxInRow}</div>);
      rows.push(aRow);
    }


    return(
      <div id="metricsModal" className="modal fade" role="dialog">
        <div className="modal-dialog modal-lg">
          <div className="modal-content">
            <div className="modal-header">
              <button type="button" className="close" data-dismiss="modal">&times;</button>
              <h4 className="modal-title">{this.props.modalTarget} Metrics Choice</h4>
            </div>
            <div className="modal-body">
              <div className="container-fluid">
                {rows}
              </div>
            </div>
            <div className="modal-footer">
            <button type="button" className="btn btn-primary" data-dismiss="modal" onClick={e => this.handleSubmitBtnClick(e)}>Save</button>
              <button type="button" className="btn btn-default" data-dismiss="modal" >Close</button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
