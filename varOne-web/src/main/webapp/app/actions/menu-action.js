import alt from '../alt';
import request from 'superagent';
class MenuAction {

  async fetchRunningJob(){
    let response = await request.get('/varOne-web/rest/job')
                              .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch(result);
  }

  async fetchVarOneConfig() {
    let response = await request.get('/varOne-web/rest/varOne/conf')
                              .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch(result);
  }

}

export default alt.createActions(MenuAction);
