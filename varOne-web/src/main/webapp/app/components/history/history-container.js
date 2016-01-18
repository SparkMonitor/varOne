import React, { PropTypes } from 'react';
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
    histories: PropTypes.array,
    job: PropTypes.array,
    stages: PropTypes.array,
    stageDetails: PropTypes.array,
    stageAggregator: PropTypes.array,
    metricCompletedTasks: PropTypes.array,
    completeTaskSize: PropTypes.number,
    breadcrumb: PropTypes.array,
    tab: PropTypes.string,
    selectApplicationId: PropTypes.string,
    jobId: PropTypes.number,
    jobs: PropTypes.array
  }

  static getStores() {
    return [ HistoryStore ];
  }

  static getPropsFromStores() {
    return HistoryStore.getState();
  }

  constructor(props) {
    super(props);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.tab === this.props.tab &&
      this.props.tab !== Const.history.tab.HISTORY_TAB) {
      HistoryAction.switchToHistoryTab();
    }
  }

  componentWillMount() {
    HistoryAction.fetchApplications();
  }

  handleCrumbSelect = tab => {
    if (tab === Const.history.tab.HISTORY_TAB) {
      HistoryAction.fetchApplications();
    } else if (tab === Const.history.tab.JOB_TAB) {
      HistoryAction.switchToJobTab();
    } else if (tab === Const.history.tab.STAGE_TAB) {
      HistoryAction.switchToStageTab();
    }
  }

  handleHistorySelect = id => {
    HistoryAction.fetchJobs(id);
  }

  handleJobSelect = jobId => {
    HistoryAction.fetchStages(this.props.selectApplicationId, jobId);
  }

  handleStageSelect = stageId => {
    HistoryAction.fetchStageDetails(this.props.selectApplicationId, stageId);
  }

  renderContent = () => {
    const { tab } = this.props;
    if (tab === Const.history.tab.HISTORY_TAB) {
      return (
        <HistoryList
          histories={ this.props.histories }
          onHistorySelect={ this.handleHistorySelect }/>
      );
    } else if (tab === Const.history.tab.JOB_TAB) {
      return (
        <JobsList
          jobs={ this.props.jobs }
          onJobSelect={ this.handleJobSelect }/>
      );
    } else if (tab === Const.history.tab.STAGE_TAB) {
      return (
        <StageList
          stages={ this.props.stages }
          onStageSelect={ this.handleStageSelect }/>
      );
    } else if (tab === Const.history.tab.STAGE_DETAILS_TAB) {
      return (
        <StageDetailsList
          completeTaskSize={ this.props.completeTaskSize }
          metricCompletedTasks={ this.props.metricCompletedTasks }
          stageDetails={ this.props.stageDetails }
          stageAggregator={ this.props.stageAggregator }/>
      );
    } else {
      return null;
    }
  }

  render() {
    return (
      <div id='page-wrapper'>
        <HistoryHeader>
          <BreadCrumb
            onCrumbSelect={ this.handleCrumbSelect }
            content={ this.props.breadcrumb }
            active={ this.props.tab }/>
        </HistoryHeader>
        <div>
          { this.renderContent() }
        </div>
      </div>
    );
  }
}

export default HistoryContainer;
