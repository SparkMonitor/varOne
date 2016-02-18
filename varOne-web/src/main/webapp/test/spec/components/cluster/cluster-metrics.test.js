import React from 'react';
import ReactDOM from 'react-dom';
import stubApp from '../../../utils/stub-app';
import ClusterMetrics from '../../../../app/components/cluster/cluster-metrics';

const should = chai.should();

const mockMetrics = {
  metricProp1: {
    id: 'mock_id1',
    title: 'mock_title1',
    value: [ [ 's1', '0', '0', '0' ], [ 's1', '0', '20', '40' ], [ 's1', '0', '5', '9' ] ],
    x: [ 'x', 1454292960000, 1454292990000, 1454293020000 ],
    format: 'OPS'
  },
  metricProp2: {
    id: 'mock_id2',
    title: 'mock_title2',
    value: [ [ 's1', '0', '0', '0' ], [ 's1', '0', '20', '40' ], [ 's1', '0', '5', '9' ] ],
    x: [ 'x', 1454292960000, 1454292990000, 1454293020000 ],
    format: 'BYTE'
  },
  metricProp3: {
    id: 'mock_id3',
    title: 'mock_title3',
    value: [ [ 's1', '0', '0', '0' ], [ 's1', '0', '20', '40' ], [ 's1', '0', '5', '9' ] ],
    x: [ 'x', 1454292960000, 1454292990000, 1454293020000 ],
    format: 'OPS'
  }
};

describe('Cluster Metrics', () => {
  let node;
  let instance;

  beforeEach(() => {
    const stub = stubApp()(ClusterMetrics, {
      metrics: mockMetrics
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
    (dom.className.indexOf('row') !== -1).should.be.ok;
    dom.childNodes.length.should.eql(1);

    const rows = dom.childNodes[0];

    should.exist(rows);
    rows.childNodes.length.should.eql(Math.ceil(Object.keys(mockMetrics).length / 2));
  });
});
