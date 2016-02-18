import alt from '../alt';
class VarOneAction {

  constructor() {
    this.generateActions('showLogStatus');
    this.generateActions('showFailMessage');
  }
}

export default alt.createActions(VarOneAction);
