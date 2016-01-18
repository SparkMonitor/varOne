import alt from '../alt';
import request from 'superagent';

class HistoryAction {

  constructor() {
    this.generateActions('switchToJobTab');
    this.generateActions('switchToHistoryTab');
    this.generateActions('switchToStageTab');
  }

  async fetchApplications() {
    const response = await request.get('/varOne-web/rest/history')
                                .set('Accept', 'application/json');
    const result = JSON.parse(response.text);
    this.dispatch(result);
  }

  async fetchJobs(applicationId) {
    const response = await request.get(`/varOne-web/rest/history/${applicationId}/jobs`)
                                .set('Accept', 'application/json');
    const jobs = JSON.parse(response.text);
    const result = { jobs, applicationId };
    this.dispatch(result);
  }

  async fetchStages(applicationId, jobId) {
    const response = await request.get(`/varOne-web/rest/history/${applicationId}/${jobId}/stages`)
                                .set('Accept', 'application/json');
    const stages = JSON.parse(response.text);
    const result = { stages, jobId };
    this.dispatch(result);
  }

  async fetchStageDetails(applicationId, stageId) {
    const response = await request
      .get(`/varOne-web/rest/history/${applicationId}/jobs/${stageId}`)
      .set('Accept', 'application/json');
    const stageDetails = JSON.parse(response.text);
    const result = { stageDetails };
    this.dispatch(result);
  }
}

export default alt.createActions(HistoryAction);
