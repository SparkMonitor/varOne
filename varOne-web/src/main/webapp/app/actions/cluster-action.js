import alt from '../alt';
import request from 'superagent';

class ClusterAction {

  async fetchTotalNodeDashBoard(selectMetrics, period) {
    if (selectMetrics && selectMetrics.length === 0) selectMetrics = undefined;
    else selectMetrics = selectMetrics.join(',');
    const response = await request.get('/varOne-web/rest/cluster')
                                .query({ metrics: selectMetrics, period })
                                .set('Accept', 'application/json');
    const result = JSON.parse(response.text);
    this.dispatch({ result, period });
  }

}

export default alt.createActions(ClusterAction);
