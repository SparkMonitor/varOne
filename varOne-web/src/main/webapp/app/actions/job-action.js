import alt from '../alt';
import request from 'superagent';

class JobAction {
  async fetchJobDashBoard(appId, selectMetrics, period){

    if(selectMetrics && selectMetrics.length == 0) selectMetrics = undefined;
    else selectMetrics = selectMetrics.join(",");

    let response = await request.get('/varOne-web/rest/job/'+appId)
                                .query({metrics: selectMetrics, period: period})
                                .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch({ result, period });
  }
}

export default alt.createActions(JobAction);
