import React from 'react';
import Const from '../../utils/consts';
import ClusterSummaryBlock from './cluster-summary-block';

class ClusterSummary extends React.Component {

  handleSummaryBlockClick(title){
    if(title === Const.node.summary.NODE_QTY){
      this.props.onNodeClick();
    }
  }

  render(){

    let summaryContents = this.props.summaryInfos.map((summary) => {
      return(
        <ClusterSummaryBlock key={summary.displayName}
                          title={summary.displayName}
                          panel={summary.panel}
                          icon={summary.icon}
                          value={summary.value}
                          onSummaryClick={(title) => this.handleSummaryBlockClick(title)}/>
      );
    });


    return(
      <div classNameName="row">
        {summaryContents}
      </div>
    );
  }
}

ClusterSummary.propTypes = {
  summaryInfos: React.PropTypes.array,
  onNodeClick: React.PropTypes.func
}

export default ClusterSummary;
