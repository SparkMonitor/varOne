import alt from '../alt';
import VarOneAction from '../actions/varOne-action';

class VarOneStore {

  constructor() {
    this.bindActions(VarOneAction);
    this.msg = null;
    this.showLogClick = false;
    this.failMessage = null;
    this.timerInterval = null;
  }

  onShowLogStatus() {
    this.showLogClick = true;
  }

  onShowFailMessage(failMessage) {
    this.failMessage = this.failMessage + failMessage;
  }

  onFetchTimerInterval(timerInterval) {
    this.timerInterval = timerInterval;
  }
}

export default alt.createStore(VarOneStore);
