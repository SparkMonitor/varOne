import React from 'react';
import JobSummaryBlock from './job-summary-block';

class JobSummary extends React.Component {
  render(){

    let summaryContents = this.props.summaryInfos.map((summary) => {
      return(
        <JobSummaryBlock  key={summary.displayName}
                          title={summary.displayName}
                          panel={summary.panel}
                          icon={summary.icon}
                          value={summary.value}/>
      );
    });


    return(
      <div classNameName="row">
        {summaryContents}
      </div>
    );
  }
}

JobSummary.propTypes = {
  summaryInfos: React.PropTypes.array
}

export default JobSummary;
