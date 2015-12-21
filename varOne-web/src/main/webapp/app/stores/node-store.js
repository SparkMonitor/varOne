import alt from '../alt';
import Const from '../utils/consts';
import NodeAction from '../actions/node-action';

class NodeStore {

  constructor(){
    this.bindActions(NodeAction);
		this.nodes = [];
    this.data = null;
    this.period = Const.shared.timeperiod[0];
  }

  onFetchNodes(result){
    this.nodes = result;
  }

  onFetchNodeDashBoard({ node, result, period }){
    var data = {};
    data.metrics = [];

    result.metricProps.forEach(metric => {
      let o = {};
      o.id = metric.property;
      o.title = metric.title;
      o.format = metric.format;
      o.x = ['x'];
      let values =  result.propToMetrics[metric.property];
      let metricsValues = [node];
      for(let i=0;i<values.length;i++){
        o.x.push(
          parseInt(values[i].time)
        );
        metricsValues.push(values[i].value);
      }
      o.value = metricsValues;
      data.metrics.push(o);
    });

    this.data = data;
    this.period = period;
  }

}

export default alt.createStore(NodeStore);
