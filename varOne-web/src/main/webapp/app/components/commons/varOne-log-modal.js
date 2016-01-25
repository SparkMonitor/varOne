import React, { PropTypes } from 'react';
import VarOneStore from '../../stores/varOne-store';
import VarOneAction from '../../actions/varOne-action';
import connectToStores from 'alt/utils/connectToStores';


@connectToStores
export default class VarOneLogModal extends React.Component {

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

  handlePortChange = e => {
    VarOneAction.changePort(e.target.value);
  }

  handleSubmitBtnClick = () => {
    const port = this.refs.port.value;
    VarOneAction.updateVarOneConf({ port });
  }

  render() {
    return (
      <div id='varOneLogModal' className='modal fade'>
        <div className='modal-dialog modal-lg'>
          <div className='modal-content'>
            <div className='modal-header'>
              <button type='button' className='close' data-dismiss='modal'>&times;</button>
              <h4 className='modal-title'>varOne Log Message</h4>
            </div>
            <div className='modal-body'>
              <textarea rows='10' cols='90'>
              </textarea>
            </div>
            <div className='modal-footer'>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
