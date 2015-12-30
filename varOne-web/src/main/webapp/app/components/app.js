import React from 'react';
import Home from './home';

import VarOneConfigModal from './commons/varOne-conf-modal';
import VarOneStore from '../stores/varOne-store';
import VarOneAction from '../actions/varOne-action';
import connectToStores from 'alt/utils/connectToStores';


@connectToStores
class App extends React.Component {

  static propTypes = {
    msg: React.PropTypes.string,
    port: React.PropTypes.string,
    inputPort: React.PropTypes.string
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

  componentDidUpdate() {
    if(this.isPortDefined()) {
      $('#varOneConfigModal').modal('show');
    }
  }

  render(){
    var content = null;
    if(this.isPortDefined()) {
      content = (
        <div>
          <VarOneConfigModal />
        </div>
      );
    } else {
      $('#varOneConfigModal').modal('hide');
      content = <Home />;
    }

    return (
      <div>
        {content}
      </div>
    );
  }

  isPortDefined() {
    return !this.props.port || this.props.port === "";
  }
}

export default App;
