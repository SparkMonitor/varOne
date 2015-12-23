import alt from '../alt';
import Const from '../utils/consts';
import HistoryAction from '../actions/history-action';

class HistoryStore {

  constructor(){
    this.bindActions(HistoryAction);
    this.selectApplicationId;
    this.jobId;
    this.histories = [];
    this.jobs = [];
    this.stages = [];
    this.stageDetails = [];
    this.tab = Const.history.tab.HISTORY_TAB;
    this.breadcrumb = [Const.history.tab.HISTORY_TAB];
  }

  onFetchApplications(result){
    this.tab = Const.history.tab.HISTORY_TAB;
    this.breadcrumb = [Const.history.tab.HISTORY_TAB];
    this.histories = result;
  }

  onFetchJobs(result){
    this.tab = Const.history.tab.JOB_TAB;
    this.breadcrumb = [Const.history.tab.HISTORY_TAB, Const.history.tab.JOB_TAB];
    this.jobs = result.jobs;
    this.selectApplicationId = result.applicationId;
  }

  onFetchStages(result){
    this.tab = Const.history.tab.STAGE_TAB;
    this.breadcrumb = [
      Const.history.tab.HISTORY_TAB,
      Const.history.tab.JOB_TAB,
      Const.history.tab.STAGE_TAB];
    this.stages = result.stages;
    this.jobId  = result.jobId;
  }
  
  onFetchStageDetails(result){
	this.tab = Const.history.tab.STAGE_DETAILS_TAB;
	this.breadcrumb = [
    Const.history.tab.HISTORY_TAB, 
	Const.history.tab.JOB_TAB, 
	Const.history.tab.STAGE_TAB,
	Const.history.tab.STAGE_DETAILS_TAB];
	this.stageDetails = result.stageDetails.tasks;
	this.stageAggregator = result.stageDetails.aggregatorExecutor;
	this.completeTaskSize = result.stageDetails.completeTaskSize;
  }

  onSwitchToJobTab(){
    this.tab = Const.history.tab.JOB_TAB;
    this.breadcrumb = [Const.history.tab.HISTORY_TAB, Const.history.tab.JOB_TAB];
  }
  
  onSwitchToStageTab(){
	this.tab = Const.history.tab.STAGE_TAB;
	this.breadcrumb = [Const.history.tab.HISTORY_TAB, Const.history.tab.JOB_TAB, Const.history.tab.STAGE_TAB];
  }

  onSwitchToHistoryTab(){
    this.tab = Const.history.tab.HISTORY_TAB;
    this.breadcrumb = [Const.history.tab.HISTORY_TAB];
  }

}

export default alt.createStore(HistoryStore);
