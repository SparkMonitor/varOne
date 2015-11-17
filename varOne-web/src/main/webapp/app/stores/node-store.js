import alt from '../alt';
import Const from '../utils/consts';
import NodeAction from '../actions/node-action';

class NodeStore {

  constructor(){
    this.bindActions(NodeAction);
		this.nodes = [];
    this.data = null;
  }

  onFetchNodes(result){
    this.nodes = result;
  }

  onFetchNodeDashBoard(result){
    var data = {};
    data.metrics = [];

    result.metricProps.forEach(metric => {
      let o = {};
      o.id = metric.property;
      o.title = metric.title;
      o.value = result.propToMetrics[metric.property];
      data.metrics.push(o);
    });

    this.data = data;
  }

}

export default alt.createStore(NodeStore);
