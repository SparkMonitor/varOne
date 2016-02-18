import React from 'react';
import ReactDOM from 'react-dom';
import TestUtils from 'react-addons-test-utils';
import stubApp from '../../../utils/stub-app';
import ClusterSummary from '../../../../app/components/cluster/cluster-summary';
import Const from '../../../../app/utils/consts';

chai.should();

const mockSummaryInfos = [ {
  displayName: Const.node.summary.NODE_QTY,
  value: 5,
  icon: 'fa-cloud',
  panel: 'panel-primary'
}, {
  displayName: Const.node.summary.RUNNING_JOB,
  value: 0,
  icon: 'fa-tasks',
  panel: 'panel-green'
}, {
  displayName: Const.node.summary.RUNNING_EXECUTOR,
  value: 0,
  icon: 'fa-list-ol',
  panel: 'panel-yellow'
}, {
  displayName: Const.node.summary.RUNNING_TASK,
  value: 0,
  icon: 'fa-th',
  panel: 'panel-red'
} ];
const mockTaskStartedNumByNode = [ {
  server1: 3
}, {
  server2: 6
} ];
const mockExecutorNumByNode = [ {
  server1: 1
}, {
  server2: 1
} ];

describe('Cluster Summary', () => {
  let node;
  let instance;
  let spy;

  beforeEach(() => {
    spy = sinon.spy();
    const stub = stubApp()(ClusterSummary, {
      summaryInfos: mockSummaryInfos,
      taskStartedNumByNode: mockTaskStartedNumByNode,
      executorNumByNode: mockExecutorNumByNode,
      onNodeClick: spy
    });
    node = window.document.createElement('div');
    instance = ReactDOM.render(React.createElement(stub), node);
  });

  afterEach(() => {
    if (instance) ReactDOM.unmountComponentAtNode(node);
  });

  it('should render correctly', () => {
    const dom = ReactDOM.findDOMNode(instance);
    dom.tagName.should.eql('DIV');
    dom.childNodes.length.should.eql(3);
    (dom.className.indexOf('summary-header') !== -1).should.be.ok;

    dom.childNodes[0].childNodes.length.should.eql(Math.ceil(mockSummaryInfos.length / 2));
  });

  it('only nodes summary block should click', () => {
    const dom = ReactDOM.findDOMNode(instance);
    const nodeSummaryLink = dom.childNodes[0].childNodes[0].
      childNodes[0].childNodes[0].childNodes[1];
    TestUtils.Simulate.click(nodeSummaryLink);
    spy.should.have.been.calledOnce;
  });
});
