import React, { PropTypes } from 'react';
import Const from '../../utils/consts';
import ClusterSummaryBlock from './cluster-summary-block';

class ClusterSummary extends React.Component {

  static propTypes = {
    summaryInfos: PropTypes.array,
    onNodeClick: PropTypes.func
  }

  handleSummaryBlockClick = title => {
    if (title === Const.node.summary.NODE_QTY) {
      this.props.onNodeClick();
    }
  }

  render() {
    const summaryContents = this.props.summaryInfos.map((summary) => {
      return (
        <ClusterSummaryBlock
          key={ summary.displayName }
          title={ summary.displayName }
          panel={ summary.panel }
          icon={ summary.icon }
          value={ summary.value }
          onSummaryClick={ this.handleSummaryBlockClick }/>
      );
    });


    return (
      <div classNameName='row'>
        { summaryContents }
      </div>
    );
  }
}

export default ClusterSummary;
