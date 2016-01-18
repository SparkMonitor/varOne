import alt from '../alt';
import VarOneAction from '../actions/varOne-action';

class VarOneStore {

  constructor() {
    this.bindActions(VarOneAction);
    this.port = null;
    this.msg = null;
    this.inputPort = null;
    this.fromUserClick = false;
  }

  onChangePort(port) {
    this.inputPort = port;
  }

  onFetchVarOneConfig({ port }) {
    this.msg = null;
    this.port = port;
    this.inputPort = port;
  }

  onUpdateVarOneConf({ ok, error, port }) {
    if (ok) {
      this.port = port;
      this.inputPort = port;
      this.msg = 'Update varOne configuration succesful.';
    } else {
      this.msg = error;
    }
  }

  onChangeStatus() {
    this.fromUserClick = true;
  }
}

export default alt.createStore(VarOneStore);
