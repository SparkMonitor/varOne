import alt from '../alt';
import request from 'superagent';

class VarOneAction {

  constructor() {
    this.generateActions('showLogStatus');
    this.generateActions('showFailMessage');
  }

  async fetchTimerInterval() {
    const response = await request.get('/varOne-web/rest/varone/timer')
                                  .set('Accept', 'application/json');
    const result = JSON.parse(response.text);
    this.dispatch(result);
  }

}

export default alt.createActions(VarOneAction);
