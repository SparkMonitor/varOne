import alt from '../alt';
import Const from '../utils/consts';
import ClusterAction from '../actions/cluster-action';

class ClusterStore {

  constructor(){
    this.bindActions(ClusterAction);
		this.data = null;
  }

  onFetchTotalNodeDashBoard(result){
    var data = {};
    data.metrics = {};

    result.metricProps.forEach(metric => {
      data.metrics[metric.property] = {};
      data.metrics[metric.property].id = metric.property;
      data.metrics[metric.property].title = metric.title;
      data.metrics[metric.property].value = [];
      let host2Metrics = result.propToMetrics[metric.property];
      for(let host in host2Metrics){
        data.metrics[metric.property].value.push([
          host,host2Metrics[host]
        ]);
      }
    });

    var taskDounts = [];
    for(let host in result.taskStartedNumByNode){
      taskDounts.push([host, result.taskStartedNumByNode[host]]);
    }

    var executorDounts = [];
    for(let host in result.executorNumByNode){
      executorDounts.push([host, result.executorNumByNode[host]]);
    }

    var displaySummaryInfo = [{
        displayName: Const.node.summary.NODE_QTY,
        value: result.nodeNum,
        icon: "fa-cloud",
        panel: "panel-primary"
      },{
        displayName: Const.node.summary.RUNNING_JOB,
        value: result.jobNum,
        icon: "fa-tasks",
        panel: "panel-green"
      },{
        displayName: Const.node.summary.RUNNING_EXECUTOR,
        value: result.executorNum,
        icon: "fa-list-ol",
        panel: "panel-yellow"
      },{
        displayName: Const.node.summary.RUNNING_TASK,
        value: result.taskNum,
        icon: "fa-th",
        panel: "panel-red"
      }
    ];

    data.displaySummaryInfo   = displaySummaryInfo;
    data.taskStartedNumByNode = taskDounts;
    data.executorNumByNode    = executorDounts;
    this.data = data;
  }

}

export default alt.createStore(ClusterStore);
