import React from 'react';
import Const from '../../utils/consts';
import HistoryHeader from './history-header';
import HistoryList from './history-list';
import JobsList from './job-list';
import StageList from './stage-list';
import StageDetailsList from './stageDetail-list';
import BreadCrumb from '../commons/breadcrumb';
import HistoryAction from '../../actions/history-action';
import HistoryStore from '../../stores/history-store';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
class HistoryContainer extends React.Component {

  static propTypes = {
    histories: React.PropTypes.array,
    job: React.PropTypes.array,
    stages: React.PropTypes.array,
    stageDetails: React.PropTypes.array,
    stageAggregator: React.PropTypes.array,
    breadcrumb: React.PropTypes.array,
    tab: React.PropTypes.string,
    selectApplicationId: React.PropTypes.string,
    jobId: React.PropTypes.number
  }
  static getStores(props) {
    return [HistoryStore];
  }
  static getPropsFromStores(props) {
    return HistoryStore.getState();
  }

  constructor(props){
    super(props);
    this.init = false;
  }

  componentWillReceiveProps(nextProps) {
    if(nextProps.tab === this.props.tab &&
      this.props.tab !== Const.history.tab.HISTORY_TAB){
      HistoryAction.switchToHistoryTab();
    }
  }

  componentWillMount(){
    HistoryAction.fetchApplications();
  }

  handleCrumbSelect = (tab) => {
    if(tab === Const.history.tab.HISTORY_TAB){
      HistoryAction.fetchApplications();
    } else if(tab === Const.history.tab.JOB_TAB){
      HistoryAction.switchToJobTab();
    } else if(tab === Const.history.tab.STAGE_TAB){
      HistoryAction.switchToStageTab();
    }
    
  }

  handleHistorySelect = (id) => {
    HistoryAction.fetchJobs(id);
  }

  handleJobSelect = (jobId) => {
    HistoryAction.fetchStages(this.props.selectApplicationId, jobId);
  }

  handleStageSelect = (stageId) => {
	HistoryAction.fetchStageDetails(this.props.selectApplicationId, stageId);
  }

  renderContent = () => {
    if(this.props.tab === Const.history.tab.HISTORY_TAB){
      return <HistoryList
                histories={this.props.histories}
                onHistorySelect={this.handleHistorySelect}/>
    } else if(this.props.tab === Const.history.tab.JOB_TAB){
      return <JobsList
                jobs={this.props.jobs}
                onJobSelect={this.handleJobSelect}/>;
    } else if(this.props.tab === Const.history.tab.STAGE_TAB){
      return <StageList
                stages={this.props.stages}
                onStageSelect={this.handleStageSelect}/>;
    } else if(this.props.tab === Const.history.tab.STAGE_DETAILS_TAB){
      return <StageDetailsList 
                stageDetails={this.props.stageDetails}
                stageAggregator={this.props.stageAggregator}/>;
    	
    } else {
    	
      return null;
    }
  }

  render(){
    var content = this.renderContent();
    return (
      <div id="page-wrapper">
        <HistoryHeader>
          <BreadCrumb
            onCrumbSelect={this.handleCrumbSelect}
            content={this.props.breadcrumb}
            active={this.props.tab}/>
        </HistoryHeader>
        <div>
          {content}
        </div>
      </div>
    );
  }
}

export default HistoryContainer;
