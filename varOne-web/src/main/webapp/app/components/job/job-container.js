import React from 'react';
import JobHeader from './job-header';
import JobSummary from './job-summary';
import JobMetrics from './job-metrics';
import MetricsSettingModal from '../commons/metrics-setting-modal';
import JobAction from '../../actions/job-action';
import JobStore from '../../stores/job-store';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
export default class JobContainer extends React.Component{
  fetchInterval = null

  static propTypes = {
    data: React.PropTypes.object,
    appId: React.PropTypes.string
  }
  static getStores(props) {
    return [JobStore];
  }
  static getPropsFromStores(props) {
    return JobStore.getState();
  }

  constructor(props){
    super(props);
    this.selectMetrics = [];
  }

  componentWillMount(){
    // this.fetchInterval = setInterval(()=>{
        JobAction.fetchJobDashBoard(this.props.appId, this.selectMetrics);
    // }, 5000);
  }

  componentWillUnmount(){
    clearInterval(this.fetchInterval);
  }

  componentDidUpdate(){
    clearInterval(this.fetchInterval);
    this.fetchInterval = setInterval(()=>{
        JobAction.fetchJobDashBoard(this.props.appId, this.selectMetrics);
    }, 6000);
  }

  handleModalSubmit(selectMetrics){
    this.selectMetrics = selectMetrics;
    clearInterval(this.fetchInterval);
    JobAction.fetchJobDashBoard(this.props.appId, selectMetrics);
  }

  render(){
    if(null !== this.props.data){
      return(
        <div id="page-wrapper">
          <JobHeader appId={this.props.appId}/>
          <MetricsSettingModal modalTarget="Job" onModalSubmit={this.handleModalSubmit.bind(this)}/>
          <div>
            <JobSummary
                summaryInfos={this.props.data.displaySummaryInfo}/>
            <JobMetrics
                taskStartedNumByNode={this.props.data.taskStartedNumByNode}
                executorNumByNode={this.props.data.executorNumByNode}
                metrics={this.props.data.metrics}/>
          </div>
        </div>
      );
    } else {
      return null;
    }
  }
}
