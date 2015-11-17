import React from 'react';
import NodesAction from '../../actions/node-action';
import NodeStore from '../../stores/node-store';
import MetricsSettingModal from '../commons/metrics-setting-modal';
import NodeHeader from './nodes-header';
import NodeMetrics from './nodes-metrics';
import connectToStores from 'alt/utils/connectToStores';

@connectToStores
class NodesContainer extends React.Component{

  fetchInterval = null

  static propTypes = {
    // data: React.PropTypes.object
  }
  static getStores(props) {
    return [NodeStore];
  }
  static getPropsFromStores(props) {
    return NodeStore.getState();
  }

  constructor(props){
    super(props);
    this.selectMetrics = [];
    this.selectNode = null;
    this.handleNodeSelect = this.handleNodeSelect.bind(this);
    this.handleModalSubmit = this.handleModalSubmit.bind(this);
  }

  componentWillMount(){
    NodesAction.fetchNodes();
  }

  componentWillUnmount(){
    this.clearInterval();
  }

  componentDidUpdate(){
    this.clearInterval();
    this.fetchInterval = setInterval(()=>{
      if(this.selectNode !== null)
        NodesAction.fetchNodeDashBoard(this.selectNode, this.selectMetrics);
    }, 6000);
  }

  handleNodeSelect(node){
    this.selectNode = node;
    this.clearInterval();
    if(this.selectNode !== null)
      NodesAction.fetchNodeDashBoard(node, this.selectMetrics);
  }

  handleModalSubmit(selectMetrics){
    this.selectMetrics = selectMetrics;
    this.clearInterval();
    if(this.selectNode !== null)
      NodesAction.fetchNodeDashBoard(this.selectNode, selectMetrics);
  }

  clearInterval(){
    if(null !== this.fetchInterval)
      clearInterval(this.fetchInterval);
  }

  renderNodeContent(){
    if(this.props.data != null){
      return (
        <div>
          <NodeMetrics metrics={this.props.data.metrics}/>
        </div>
      );
    } else {
      return null;
    }
  }

  render(){
    var content = this.renderNodeContent();
    return (
      <div id="page-wrapper">
        <NodeHeader nodes={this.props.nodes} onNodeSelect={this.handleNodeSelect}/>
        <MetricsSettingModal modalTarget="Cluster" onModalSubmit={this.handleModalSubmit}/>
        <div>
          {content}
        </div>
      </div>
    );
  }
}


export default NodesContainer;
