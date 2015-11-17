import alt from '../alt';
import request from 'superagent';

class JobAction {
  async fetchJobDashBoard(appId, selectMetrics){

    if(selectMetrics && selectMetrics.length == 0) selectMetrics = undefined;
    else selectMetrics = selectMetrics.join(",");

    let response = await request.get('http://localhost:8080/Spark_Monitor_Web/rest/job/'+appId)
                                .query({metrics: selectMetrics})
                                .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch(result);
  }
}

export default alt.createActions(JobAction);
