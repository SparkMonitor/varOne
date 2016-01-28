import alt from '../alt';
import MenuAction from '../actions/menu-action';
import request from 'superagent';
class VarOneAction {

  constructor() {
    this.generateActions('changePort');
    this.generateActions('changeStatus');
    this.generateActions('showLogStatus');
    this.generateActions('showFailMessage');
  }

  async fetchVarOneConfig() {
    try {
      const response = await request.get('/varOne-web/rest/varOne/conf')
                                    .set('Accept', 'application/json');
      const result = JSON.parse(response.text);
      this.dispatch(result);
    } catch (e) {
      this.showFailMessage(e.response.text);
      MenuAction.changErrorMessageIcon(true);
    }
  }

  async updateVarOneConf({ port }) {
    try {
      const response = await request.post('/varOne-web/rest/varOne/conf')
                                    .send({ port })
                                    .set('Accept', 'application/json');
      const result = JSON.parse(response.text);
      result.port = port;
      this.dispatch(result);
    } catch (e) {
      this.showFailMessage(e.response.text);
      MenuAction.changErrorMessageIcon(true);
    }
  }


}

export default alt.createActions(VarOneAction);
