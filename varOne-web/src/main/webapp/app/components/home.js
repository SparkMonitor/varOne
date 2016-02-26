require('../styles/sb-admin-2.css');
require('../styles/spm/spm.css');
require('../../node_modules/c3/c3.min.css');
require('../../node_modules/react-bootstrap-table/css/react-bootstrap-table-all.min.css');
import React, { PropTypes } from 'react';
import Const from '../utils/consts';
import Nav from './navigator/nav';
import VarOneAction from '../actions/varOne-action';
import VarOneStore from '../stores/varOne-store';
import ClusterContainer from './cluster/cluster-container';
import HistoryContainer from './history/history-container';
import NodesContainer from './nodes/nodes-container';
import JobContainer from './job/job-container';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
class Home extends React.Component {

  static propTypes = {
    timerInterval: PropTypes.string
  }

  state = {
    container: Const.menu.cluster,
    applicationId: null
  }
  constructor(props) {
    super(props);
    VarOneAction.fetchTimerInterval();
  }
  static getStores() {
    return [ VarOneStore ];
  }

  static getPropsFromStores() {
    return VarOneStore.getState();
  }

  handleJobItemClick = applicationId => {
    this.setState({
      container: Const.menu.runningJobs,
      applicationId
    });
  }

  handleDimensionItemClick = dimension => {
    this.setState({
      container: dimension
    });
  }

  handleNodeClick = () => {
    this.setState({
      container: Const.menu.nodes
    });
  }

  renderContainer() {
    const timer = this.props.timerInterval;
    if (this.state.container === Const.menu.cluster) {
      return <ClusterContainer nodeClickCB={ this.handleNodeClick } timerInterval={ timer }/>;
    } else if (this.state.container === Const.menu.runningJobs) {
      return <JobContainer appId={ this.state.applicationId } timerInterval= { timer }/>;
    } else if (this.state.container === Const.menu.nodes) {
      return <NodesContainer timerInterval = { timer }/>;
    } else if (this.state.container === Const.menu.history) {
      return <HistoryContainer />;
    }
  }

  render() {
    return (
      <div id='wrapper'>
        <Nav
          jobItemClickCB={ this.handleJobItemClick }
          dimensionItemClickCB={ this.handleDimensionItemClick }/>
        { this.renderContainer() }
      </div>
    );
  }
}

export default Home;
