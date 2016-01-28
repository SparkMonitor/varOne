import alt from '../alt';
import VarOneAction from '../actions/varOne-action';
import MenuAction from '../actions/menu-action';
import request from 'superagent';

class NodeAction {

  constructor() {
    this.generateActions('changeTimePeriod');
  }

  async fetchNodes() {
    try {
      const response = await request.get('/varOne-web/rest/nodes')
                                    .set('Accept', 'application/json');
      const result = JSON.parse(response.text);
      this.dispatch(result);
    } catch (e) {
      VarOneAction.showFailMessage(e.response.text);
      MenuAction.changErrorMessageIcon(true);
    }
  }

  async fetchNodeDashBoard(node, selectMetrics, period) {
    try {
      if (selectMetrics && selectMetrics.length === 0) selectMetrics = undefined;
      else selectMetrics = selectMetrics.join(',');
      const response = await request.get(`/varOne-web/rest/nodes/${node}`)
                                    .query({ metrics: selectMetrics, period })
                                    .set('Accept', 'application/json');
      const result = JSON.parse(response.text);
      this.dispatch({ node, result, period });
    } catch (e) {
      VarOneAction.showFailMessage(e.response.text);
      MenuAction.changErrorMessageIcon(true);
    }
  }

}

export default alt.createActions(NodeAction);
