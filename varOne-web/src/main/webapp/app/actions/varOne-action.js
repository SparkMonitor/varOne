import alt from '../alt';
import request from 'superagent';
class VarOneAction {

  constructor() {
    this.generateActions('changePort');
    this.generateActions('changeStatus');
  }

  async fetchVarOneConfig() {
    const response = await request.get('/varOne-web/rest/varOne/conf')
                              .set('Accept', 'application/json');
    const result = JSON.parse(response.text);
    this.dispatch(result);
  }

  async updateVarOneConf({ port }) {
    const response = await request.post('/varOne-web/rest/varOne/conf')
                              .send({ port })
                              .set('Accept', 'application/json');
    const result = JSON.parse(response.text);
    result.port = port;
    this.dispatch(result);
  }

}

export default alt.createActions(VarOneAction);
