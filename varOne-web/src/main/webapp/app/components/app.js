/* global $:true */

import React from 'react';
import Home from './home';

import VarOneConfigModal from './commons/varOne-conf-modal';
import VarOneStore from '../stores/varOne-store';
import VarOneAction from '../actions/varOne-action';
import connectToStores from 'alt/utils/connectToStores';


@connectToStores
class App extends React.Component {

  static propTypes = {
    port: React.PropTypes.string,
    fromUserClick: React.PropTypes.bool
  }

  static getStores() {
    return [ VarOneStore ];
  }

  static getPropsFromStores() {
    return VarOneStore.getState();
  }

  componentWillMount() {
    VarOneAction.fetchVarOneConfig();
  }

  componentDidUpdate() {
    if (this.isPortDefined()) {
      $('#varOneConfigModal').modal('show');
    }
  }

  render() {
    let content = null;
    if (this.isPortDefined()) {
      content = (
        <div>
          <VarOneConfigModal />
        </div>
      );
    } else {
      if (!this.props.fromUserClick) {
        $('#varOneConfigModal').modal('hide');
      }
      content = <Home />;
    }

    return (
      <div>
        { content }
      </div>
    );
  }

  isPortDefined() {
    return !this.props.port || this.props.port === '';
  }
}

export default App;
