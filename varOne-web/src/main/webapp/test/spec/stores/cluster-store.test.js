import alt from '../../../app/alt';
import Const from '../../../app/utils/consts';
import ClusterAction from '../../../app/actions/cluster-action';
import ClusterStore from '../../../app/stores/cluster-store';

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

const mockPeriod = Const.shared.timeperiod[2];

describe('Cluster Store', () => {
  it('listens for fetchTotalNodeDashBoardSuccessful action', () => {
    const action = ClusterAction.FETCH_TOTAL_NODE_DASH_BOARD_SUCCESSFUL;
    const taskStartedNumByNodeKeys = Object.keys(mockResponse.taskStartedNumByNode);
    const executorNumByNodeKeys = Object.keys(mockResponse.executorNumByNode);

    alt.dispatcher.dispatch({
      action,
      data: { result: mockResponse, period: mockPeriod }
    });

    const state = ClusterStore.getState();

    should.exist(state.data);
    should.exist(state.data.metrics);
    should.exist(state.data.displaySummaryInfo);
    should.exist(state.data.taskStartedNumByNode);
    should.exist(state.data.executorNumByNode);
    state.data.displaySummaryInfo.length.should.eql(4);

    state.data.taskStartedNumByNode.length.should.eql(taskStartedNumByNodeKeys.length);
    state.data.executorNumByNode.length.should.eql(executorNumByNodeKeys.length);

    let nodes = state.data.taskStartedNumByNode.map(x => x[0]);
    nodes.every((o) => taskStartedNumByNodeKeys.find(x => x === o)).should.be.ok;

    nodes = state.data.executorNumByNode.map(x => x[0]);
    nodes.every((o) => executorNumByNodeKeys.find(x => x === o)).should.be.ok;

    state.period.should.eql(mockPeriod);
  });
});
