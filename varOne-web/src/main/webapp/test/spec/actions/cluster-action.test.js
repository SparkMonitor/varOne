import fauxJax from 'faux-jax';

import alt from '../../../app/alt';
import ClusterAction from '../../../app/actions/cluster-action';

const should = chai.should();

const mockResponse = {
  nodeNum: 5,
  jobNum: 0,
  taskNum: 0,
  executorNum: 0,
  metricProps: [],
  taskStartedNumByNode: {
    'server-a5': 0,
    'server-a1': 0,
    'server-a2': 0,
    'server-a3': 0,
    'server-a4': 0
  },
  executorNumByNode: {
    'server-a5': 0,
    'server-a1': 0,
    'server-a2': 0,
    'server-a3': 0,
    'server-a4': 0
  },
  propToMetrics: {}
};

describe('Cluster Action', () => {
  let dispatcherSpy;
  let fetchTotalNodeDashBoardSuccessfulSpy;

  beforeEach(() => {
    function respond(request) {
      request.respond(
        200,
        { 'Content-Type': 'application/json' },
        JSON.stringify(mockResponse)
      );
    }
    fauxJax.install();
    fauxJax.on('request', respond);
    dispatcherSpy = sinon.spy(alt.dispatcher, 'dispatch');
    fetchTotalNodeDashBoardSuccessfulSpy =
      sinon.spy(ClusterAction, 'fetchTotalNodeDashBoardSuccessful');
  });

  afterEach(() => {
    fauxJax.restore();
    alt.dispatcher.dispatch.restore();
    fetchTotalNodeDashBoardSuccessfulSpy.restore();
  });

  it('dispatches correct data', async () => {
    const selectMetrics = [];
    const period = '30m';

    await ClusterAction.fetchTotalNodeDashBoard(selectMetrics, period);
    const args = dispatcherSpy.args[0][0];
    const response = args.data.result;
    should.exist(response);
    response.nodeNum.should.eql(mockResponse.nodeNum);
    Object.keys(response.taskStartedNumByNode).length
      .should.eql(Object.keys(mockResponse.taskStartedNumByNode).length);
    Object.keys(response.executorNumByNode).length
      .should.eql(Object.keys(mockResponse.executorNumByNode).length);
  });

  it('fires fetchTotalNodeDashBoardSuccessful', async () => {
    const selectMetrics = [];
    const period = '30m';

    await ClusterAction.fetchTotalNodeDashBoard(selectMetrics, period);
    fetchTotalNodeDashBoardSuccessfulSpy.calledOnce.should.be.ok;
  });
});
