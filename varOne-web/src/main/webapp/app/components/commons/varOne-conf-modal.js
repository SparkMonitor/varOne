/* global $:true */
import React, { PropTypes } from 'react';
import VarOneStore from '../../stores/varOne-store';
import VarOneAction from '../../actions/varOne-action';
import connectToStores from 'alt/utils/connectToStores';


@connectToStores
export default class VarOneConfigModal extends React.Component {

  static propTypes = {
    msg: PropTypes.string,
    port: PropTypes.string,
    inputPort: PropTypes.string
  }

  static getStores() {
    return [ VarOneStore ];
  }

  static getPropsFromStores() {
    return VarOneStore.getState();
  }

  componentDidMount() {
    $('#varOneConfigModal').on('shown.bs.modal', () => {
      VarOneAction.fetchVarOneConfig();
    });
  }

  handlePortChange = e => {
    VarOneAction.changePort(e.target.value);
  }

  handleSubmitBtnClick = () => {
    const port = this.refs.port.value;
    VarOneAction.updateVarOneConf({ port });
  }

  render() {
    return (
      <div id='varOneConfigModal' className='modal fade' role='dialog'>
        <div className='modal-dialog modal-lg'>
          <div className='modal-content'>
            <div className='modal-header'>
              <button type='button' className='close' data-dismiss='modal'>&times;</button>
              <h4 className='modal-title'>varOne configuration</h4>
            </div>
            <div className='modal-body'>
              <h6 style={ { color: 'red' } }>{ this.props.msg }</h6>
              <div className='container-fluid'>
                varOne-node port:
                <input ref='port' type='text' value={ this.props.inputPort }
                  onChange={ this.handlePortChange }></input>
              </div>
            </div>
            <div className='modal-footer'>
              <button
                type='button'
                className='btn btn-primary'
                onClick={ this.handleSubmitBtnClick }>
                  Save
              </button>
              <button type='button' className='btn btn-default' data-dismiss='modal' >Close</button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
