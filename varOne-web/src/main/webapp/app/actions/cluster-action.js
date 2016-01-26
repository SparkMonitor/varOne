import alt from '../alt';
import request from 'superagent';

class ClusterAction {
	constructor() {
  this.generateActions('fetchTotalNodeDashBoardSuccessful', 'fetchTotalNodeDashBoardFail');
	}

  async fetchTotalNodeDashBoard(selectMetrics, period) {
    try {
      if (selectMetrics && selectMetrics.length === 0) selectMetrics = undefined;
      else selectMetrics = selectMetrics.join(',');
      const response = await request.get('/varOne-web/rest/cluster')
                                    .query({ metrics: selectMetrics, period })
                                    .set('Accept', 'application/json');
      const result = JSON.parse(response.text);
      console.log(result);
//      this.dispatch({ result, period });
      this.actions.fetchTotalNodeDashBoardSuccessful({ result, period });
    } catch (e) {
//      alert(e.response.text);
      this.actions.fetchTotalNodeDashBoardFail(e.response.text);
    }
  }

}

export default alt.createActions(ClusterAction);
