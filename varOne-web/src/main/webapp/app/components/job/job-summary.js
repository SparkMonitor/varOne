import React, { PropTypes } from 'react';
import JobSummaryBlock from './job-summary-block';

class JobSummary extends React.Component {

  static propTypes = {
    summaryInfos: PropTypes.array
  }

  render() {
    const summaryContents = this.props.summaryInfos.map(summary => {
      return (
        <JobSummaryBlock
          key={ summary.displayName }
          title={ summary.displayName }
          panel={ summary.panel }
          icon={ summary.icon }
          value={ summary.value }/>
      );
    });

    return (
      <div classNameName='row'>
        { summaryContents }
      </div>
    );
  }
}
export default JobSummary;
