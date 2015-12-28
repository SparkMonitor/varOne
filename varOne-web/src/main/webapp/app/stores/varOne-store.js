import alt from '../alt';
import Const from '../utils/consts';
import VarOneAction from '../actions/varOne-action';

class VarOneStore {

  constructor(){
    this.bindActions(VarOneAction);
    this.port = null;
    this.msg = null;
  }

  onChangePort(port) {
    this.port = port;
  }

  onFetchVarOneConfig({ port }) {
    this.port = port;
  }

  onUpdateVarOneConf({ ok, error }) {
    if(ok) {
      this.msg = "Update varOne configuration succesful.";
    } else {
      this.msg = error;
    }
  }

}

export default alt.createStore(VarOneStore);
