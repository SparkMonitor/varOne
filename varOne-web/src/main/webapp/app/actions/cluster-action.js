import alt from '../alt';
import request from 'superagent';

class ClusterAction {

  async fetchTotalNodeDashBoard(selectMetrics){
    if(selectMetrics && selectMetrics.length == 0) selectMetrics = undefined;
    else selectMetrics = selectMetrics.join(",");
    let response = await request.get('http://localhost:8080/varOne-web/rest/cluster')
                                .query({metrics: selectMetrics})
                                .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch(result);
  }

}

export default alt.createActions(ClusterAction);
