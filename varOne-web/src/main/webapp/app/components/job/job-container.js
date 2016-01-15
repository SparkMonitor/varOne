import React, { PropTypes } from 'react';
import JobHeader from './job-header';
import JobSummary from './job-summary';
import JobMetrics from './job-metrics';
import MetricsSettingModal from '../commons/metrics-setting-modal';
import JobAction from '../../actions/job-action';
import JobStore from '../../stores/job-store';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
export default class JobContainer extends React.Component {
  fetchInterval = null

  static propTypes = {
    data: PropTypes.object,
    appId: PropTypes.string,
    period: PropTypes.string
  }
  static getStores() {
    return [ JobStore ];
  }
  static getPropsFromStores() {
    return JobStore.getState();
  }

  constructor(props) {
    super(props);
    this.selectMetrics = [];
  }

  componentWillMount() {
    JobAction.fetchJobDashBoard(this.props.appId, this.selectMetrics, this.props.period);
  }

  componentWillUnmount() {
    clearInterval(this.fetchInterval);
  }

  componentDidUpdate() {
    clearInterval(this.fetchInterval);
    this.fetchInterval = setInterval(() => {
      JobAction.fetchJobDashBoard(this.props.appId, this.selectMetrics, this.props.period);
    }, 6000);
  }

  handleModalSubmit = selectMetrics => {
    this.selectMetrics = selectMetrics;
    clearInterval(this.fetchInterval);
    JobAction.fetchJobDashBoard(this.props.appId, selectMetrics, this.props.period);
  }

  handlePeriodSelect = period => {
    clearInterval(this.fetchInterval);
    JobAction.fetchJobDashBoard(this.props.appId, this.selectMetrics, period);
  }

  render() {
    if (this.props.data !== null) {
      return (
        <div id='page-wrapper'>
          <JobHeader
            appId={ this.props.appId }
            period={ this.props.period }
            onPeriodSelect={ this.handlePeriodSelect }/>
          <MetricsSettingModal modalTarget='Job' onModalSubmit={ this.handleModalSubmit }/>
          <div>
            <JobSummary
              summaryInfos={ this.props.data.displaySummaryInfo }/>
            <JobMetrics
              taskStartedNumByNode={ this.props.data.taskStartedNumByNode }
              executorNumByNode={ this.props.data.executorNumByNode }
              metrics={ this.props.data.metrics }/>
          </div>
        </div>
      );
    } else {
      return null;
    }
  }
}
