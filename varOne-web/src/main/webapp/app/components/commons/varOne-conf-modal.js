import React from 'react';
import Const from '../../utils/consts';

export default class VarOneConfigModal extends React.Component{

  static propTypes = {
    // onModalSubmit: React.PropTypes.func
  }

  constructor(props) {
		super(props);
  }

  handleSubmitBtnClick(e){
    // var selectMetrics = [];
    //
    // for(let metric in this.refs){
    //   let checkbox = this.refs[metric].getDOMNode();
    //   if(checkbox.checked){
    //     selectMetrics.push(checkbox.value);
    //   }
    // }
    //
    // this.props.onModalSubmit(selectMetrics);
  }

  render(){

    return(
      <div id="varOneConfigModal" className="modal fade" role="dialog">
        <div className="modal-dialog modal-lg">
          <div className="modal-content">
            <div className="modal-header">
              <button type="button" className="close" data-dismiss="modal">&times;</button>
              <h4 className="modal-title">varOne configuration</h4>
            </div>
            <div className="modal-body">
              <div className="container-fluid">
                
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
