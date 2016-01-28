import alt from '../alt';
import VarOneAction from '../actions/varOne-action';
import request from 'superagent';
class MenuAction {
  constructor() {
    this.generateActions('changErrorMessageIcon');
  }
  async fetchRunningJob() {
    try {
      const response = await request.get('/varOne-web/rest/job')
                                    .set('Accept', 'application/json');
      const result = JSON.parse(response.text);
      this.dispatch(result);
    } catch (e) {
      VarOneAction.showFailMessage(e.response.text);
      this.changErrorMessageIcon(true);
    }
  }

}

export default alt.createActions(MenuAction);
