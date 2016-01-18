import alt from '../alt';
import request from 'superagent';

class NodeAction {

  constructor() {
    this.generateActions('changeTimePeriod');
  }

  async fetchNodes() {
    const response = await request.get('/varOne-web/rest/nodes')
                                .set('Accept', 'application/json');
    const result = JSON.parse(response.text);
    this.dispatch(result);
  }

  async fetchNodeDashBoard(node, selectMetrics, period) {
    if (selectMetrics && selectMetrics.length === 0) selectMetrics = undefined;
    else selectMetrics = selectMetrics.join(',');
    const response = await request.get(`/varOne-web/rest/nodes/${node}`)
                                .query({ metrics: selectMetrics, period })
                                .set('Accept', 'application/json');
    const result = JSON.parse(response.text);
    this.dispatch({ node, result, period });
  }

}

export default alt.createActions(NodeAction);
