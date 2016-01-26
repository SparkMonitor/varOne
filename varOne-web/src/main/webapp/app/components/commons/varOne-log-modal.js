import React, { PropTypes } from 'react';
import VarOneStore from '../../stores/varOne-store';
import connectToStores from 'alt/utils/connectToStores';


@connectToStores
export default class VarOneLogModal extends React.Component {

  static propTypes = {
    msg: PropTypes.string,
    port: PropTypes.string,
    inputPort: PropTypes.string,
    failMessage: PropTypes.string
  }

  static getStores() {
    return [ VarOneStore ];
  }

  static getPropsFromStores() {
    return VarOneStore.getState();
  }

  render() {
    const failResultMessage = this.props.failMessage;
    return (
      <div id='varOneLogModal' className='modal fade'>
        <div className='modal-dialog modal-lg'>
          <div className='modal-content'>
            <div className='modal-header'>
              <button type='button' className='close' data-dismiss='modal'>&times;</button>
              <h4 className='modal-title'>varOne Log Message</h4>
            </div>
            <div className='modal-body'>
              <textarea ref='textFailMessage' rows='10' cols='90' value={ failResultMessage }>
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
