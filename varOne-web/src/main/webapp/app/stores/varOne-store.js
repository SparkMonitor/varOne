import alt from '../alt';
import VarOneAction from '../actions/varOne-action';

class VarOneStore {

  constructor() {
    this.bindActions(VarOneAction);
    this.msg = null;
    this.showLogClick = false;
    this.failMessage = null;
  }

  onShowLogStatus() {
    this.showLogClick = true;
  }

  onShowFailMessage(failMessage) {
    this.failMessage = this.failMessage + failMessage;
  }
}

export default alt.createStore(VarOneStore);
