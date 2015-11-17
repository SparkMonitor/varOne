import alt from '../alt';
import Const from '../utils/consts';
import JobAction from '../actions/job-action';

class JobStore {

  constructor(){
    this.bindActions(JobAction);
		this.data = null;
  }

  onFetchJobDashBoard(result){
    let data = {};
    data.metrics = {};

    result.metricProps.forEach(metric => {
      data.metrics[metric.property] = {};
      data.metrics[metric.property].id = metric.property;
      data.metrics[metric.property].title = metric.title;
      data.metrics[metric.property].value = [];
      let host2Metrics = result.propToMetrics[metric.property];
      for(let host in host2Metrics){
        data.metrics[metric.property].value.push({
          x: host,
          y: host2Metrics[host]
        });
      }
    });

    var taskDounts = [];
    for(let host in result.taskStartedNumByNode){
      taskDounts.push({
        label: host,
        value: result.taskStartedNumByNode[host]
      });
    }

    var executorDounts = [];
    for(let host in result.executorNumByNode){
      executorDounts.push({
        label: host,
        value: result.executorNumByNode[host]
      });
    }

    var displaySummaryInfo = [{
        displayName: Const.job.summary.EXECUTOR_QTY,
        value: result.executorNum,
        icon: "fa-truck",
        panel: "panel-primary"
      },{
        displayName: Const.job.summary.TASK_QTY,
        value: result.taskNum,
        icon: "fa-align-left",
        panel: "panel-green"
      },{
        displayName: Const.job.summary.COMPLETED_TASK_QTY,
        value: result.completedTaskNum,
        icon: "fa-check",
        panel: "panel-yellow"
      },{
        displayName: Const.job.summary.FAILED_TASK_QTY,
        value: result.failedTaskNum,
        icon: "fa-exclamation-triangle",
        panel: "panel-red"
      }
    ];


    data.displaySummaryInfo   = displaySummaryInfo;
    data.taskStartedNumByNode = taskDounts;
    data.executorNumByNode    = executorDounts;
    this.data = data;
  }

}

export default alt.createStore(JobStore);
