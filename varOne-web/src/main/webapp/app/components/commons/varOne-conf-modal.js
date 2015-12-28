import React from 'react';
import Const from '../../utils/consts';
import VarOneStore from '../../stores/varOne-store';
import VarOneAction from '../../actions/varOne-action';
import connectToStores from 'alt/utils/connectToStores';


@connectToStores
export default class VarOneConfigModal extends React.Component{

  static propTypes = {
    msg: React.PropTypes.string,
    port: React.PropTypes.string
  }

  static getStores(props) {
    return [VarOneStore];
  }

  static getPropsFromStores(props) {
    return VarOneStore.getState();
  }

  componentWillMount() {
    VarOneAction.fetchVarOneConfig();
  }

  handlePortChange(e) {
    VarOneAction.changePort(e.target.value);
  }

  handleSubmitBtnClick(e) {
    const port = this.refs.port.value;
    VarOneAction.updateVarOneConf({ port });
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
              <h4 style={{color: 'red'}}>{ this.props.msg }</h4>
              <div className="container-fluid">
                varOne-node port: <input ref="port" type="text" value={ this.props.port } onChange={e => this.handlePortChange(e)}></input>
              </div>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-primary" onClick={e => this.handleSubmitBtnClick(e)}>Save</button>
              <button type="button" className="btn btn-default" data-dismiss="modal" >Close</button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
