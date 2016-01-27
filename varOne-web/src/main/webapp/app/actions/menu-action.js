import alt from '../alt';
import VarOneAction from '../actions/varOne-action';
import request from 'superagent';
class MenuAction {

  async fetchRunningJob() {
    try {
      const response = await request.get('/varOne-web/rest/job')
                                    .set('Accept', 'application/json');
      const result = JSON.parse(response.text);
      this.dispatch(result);
    } catch (e) {
      VarOneAction.showFailMessage(e.response.text);
    }
  }

}

export default alt.createActions(MenuAction);
