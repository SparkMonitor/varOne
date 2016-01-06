import alt from '../alt';
import request from 'superagent';
class VarOneAction {

  constructor() {
    this.generateActions('changePort');
    this.generateActions('changeStatus');
  }

  async fetchVarOneConfig() {
    let response = await request.get('/varOne-web/rest/varOne/conf')
                              .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch(result);
  }

  async updateVarOneConf({ port }) {
    let response = await request.post('/varOne-web/rest/varOne/conf')
                              .send({ port })
                              .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    result.port = port;
    this.dispatch(result);
  }

}

export default alt.createActions(VarOneAction);
