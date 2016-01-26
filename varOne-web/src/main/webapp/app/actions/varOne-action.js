import alt from '../alt';
import request from 'superagent';
class VarOneAction {

  constructor() {
    this.generateActions('changePort');
    this.generateActions('changeStatus');
    this.generateActions('showLogStatus');
  }

  async fetchVarOneConfig() {
    try {
      const response = await request.get('/varOne-web/rest/varOne/conf')
                                    .set('Accept', 'application/json');
      const result = JSON.parse(response.text);
      this.dispatch(result);
    } catch (e) {
      alert(e.response.text);
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
      alert(e.response.text);
    }
  }

}

export default alt.createActions(VarOneAction);
