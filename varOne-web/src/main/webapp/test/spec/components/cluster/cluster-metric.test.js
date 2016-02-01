import React from 'react';
import ReactDOM from 'react-dom';
import TestUtils from 'react-addons-test-utils';
import stubApp from '../../../utils/stub-app';
import ClusterMetric from '../../../../app/components/cluster/cluster-metric';

const should = chai.should();

const mockMetric = {
  id: 'mock_id',
  title: 'mock_title',
  value: [ [ 's1', '0', '0', '0' ], [ 's1', '0', '20', '40' ], [ 's1', '0', '5', '9' ] ],
  x: [ 'x', 1454292960000, 1454292990000, 1454293020000 ],
  format: 'OPS'
};

describe('Cluster Metric', () => {
  let node;
  let instance;
  let spy;

  beforeEach(() => {
    spy = sinon.spy();
    ClusterMetric.prototype.defaultCharting = spy;
    const stub = stubApp()(ClusterMetric, {
      metric: mockMetric
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
    (dom.className.indexOf('panel-info') !== -1).should.be.ok;
    dom.childNodes.length.should.eql(2);

    const barChartIcon = TestUtils.findRenderedDOMComponentWithClass(instance, 'fa-bar-chart-o');
    const heading = dom.childNodes[0];
    const chartPanel = dom.childNodes[1].childNodes[0];

    should.exist(barChartIcon);
    should.exist(chartPanel);
    heading.childNodes.length.should.eql(3);
    heading.childNodes[1].textContent.should.eql(mockMetric.title);
    (chartPanel.id === mockMetric.id).should.be.ok;
    spy.should.have.been.calledOnce;
  });
});
