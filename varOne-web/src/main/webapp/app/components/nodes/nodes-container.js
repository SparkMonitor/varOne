import React, { PropTypes } from 'react';
import NodesAction from '../../actions/node-action';
import NodeStore from '../../stores/node-store';
import MetricsSettingModal from '../commons/metrics-setting-modal';
import NodeHeader from './nodes-header';
import NodeMetrics from './nodes-metrics';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
class NodesContainer extends React.Component {

  fetchInterval = null

  static propTypes = {
    data: PropTypes.object,
    period: PropTypes.string,
    timerInterval: PropTypes.string,
    nodes: PropTypes.array
  }
  static getStores() {
    return [ NodeStore ];
  }
  static getPropsFromStores() {
    return NodeStore.getState();
  }

  constructor(props) {
    super(props);
    this.selectMetrics = [];
    this.selectNode = null;
    this.handleNodeSelect = this.handleNodeSelect.bind(this);
    this.handleModalSubmit = this.handleModalSubmit.bind(this);
  }

  componentWillMount() {
    NodesAction.fetchNodes();
  }

  componentWillUnmount() {
    this.clearInterval();
  }

  componentDidUpdate() {
    this.clearInterval();
    this.fetchInterval = setInterval(() => {
      if (this.selectNode !== null) {
        NodesAction.fetchNodeDashBoard(
          this.selectNode, this.selectMetrics, this.props.period);
      }
    }, this.props.timerInterval);
  }

  handleNodeSelect(node) {
    this.selectNode = node;
    this.clearInterval();
    if (this.selectNode !== null) {
      NodesAction.fetchNodeDashBoard(node, this.selectMetrics, this.props.period);
    }
  }

  handleModalSubmit(selectMetrics) {
    this.selectMetrics = selectMetrics;
    this.clearInterval();
    if (this.selectNode !== null) {
      NodesAction.fetchNodeDashBoard(this.selectNode, selectMetrics, this.props.period);
    }
  }

  handlePeriodSelect = period => {
    clearInterval(this.fetchInterval);
    if (this.selectNode !== null) {
      NodesAction.fetchNodeDashBoard(this.selectNode, this.selectMetrics, period);
    } else {
      NodesAction.changeTimePeriod(period);
    }
  }

  clearInterval() {
    if (this.fetchInterval !== null) {
      clearInterval(this.fetchInterval);
    }
  }

  renderNodeContent() {
    if (this.props.data !== null) {
      return (
        <div>
          <NodeMetrics metrics={ this.props.data.metrics }/>
        </div>
      );
    } else {
      return null;
    }
  }

  render() {
    return (
      <div id='page-wrapper'>
        <NodeHeader
          nodes={ this.props.nodes }
          period={ this.props.period }
          onNodeSelect={ this.handleNodeSelect }
          onPeriodSelect={ this.handlePeriodSelect }/>
        <MetricsSettingModal
          modalTarget='Cluster'
          onModalSubmit={ this.handleModalSubmit }/>
        <div>
          { this.renderNodeContent() }
        </div>
      </div>
    );
  }
}
export default NodesContainer;
