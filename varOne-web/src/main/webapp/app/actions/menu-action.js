import alt from '../alt';
import request from 'superagent';
class MenuAction {

  async fetchRunningJob(){
    let response = await request.get('http://localhost:8080/Spark_Monitor_Web/rest/job')
                              .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch(result);
  }

}

export default alt.createActions(MenuAction);
