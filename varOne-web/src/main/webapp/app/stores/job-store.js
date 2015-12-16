import alt from '../alt';
import Const from '../utils/consts';
import JobAction from '../actions/job-action';

class JobStore {

  constructor(){
    this.bindActions(JobAction);
		this.data = null;
    this.period = Const.shared.timeperiod[0];
  }

  onFetchJobDashBoard({ result, period }){
    let data = {};
    data.metrics = {};

    result.metricProps.forEach(metric => {
      let isCollectX = false;
      data.metrics[metric.property] = {};
      data.metrics[metric.property].id = metric.property;
      data.metrics[metric.property].title = metric.title;
      data.metrics[metric.property].value = [];
      data.metrics[metric.property].x = ['x'];
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
      taskDounts.push([
        host,result.taskStartedNumByNode[host]
      ]);
    }

    var executorDounts = [];
    for(let host in result.executorNumByNode){
      executorDounts.push([
        host,result.executorNumByNode[host]
      ]);
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
    this.period = period;
  }

}

export default alt.createStore(JobStore);
