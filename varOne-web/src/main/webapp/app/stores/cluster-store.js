import alt from '../alt';
import Const from '../utils/consts';
import ClusterAction from '../actions/cluster-action';

class ClusterStore {

  constructor(){
    this.bindActions(ClusterAction);
		this.data = null;
    this.period = Const.shared.timeperiod[0];
  }

  onFetchTotalNodeDashBoard({ result, period }){
    var data = {};
    data.metrics = {};

    result.metricProps.forEach(metric => {
      let isCollectX = false;
      data.metrics[metric.property] = {};
      data.metrics[metric.property].id = metric.property;
      data.metrics[metric.property].title = metric.title;
      data.metrics[metric.property].value = [];
      data.metrics[metric.property].x = ['x'];
      data.metrics[metric.property].format = metric.format;
      let host2Metrics = result.propToMetrics[metric.property];
      for(let host in host2Metrics){
        let metricsValues = [host];
        for(let i=0;i<host2Metrics[host].length;i++){
          if(!isCollectX){
            data.metrics[metric.property].x.push(
              parseInt(host2Metrics[host][i].time)
            );
          }
          metricsValues.push(host2Metrics[host][i].value);
        }
        isCollectX = true;
        data.metrics[metric.property].value.push(metricsValues);
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
    this.period = period;
    this.data = data;
  }

}

export default alt.createStore(ClusterStore);
