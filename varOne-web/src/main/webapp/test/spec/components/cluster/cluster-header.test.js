import React from 'react';
import ReactDOM from 'react-dom';
import Const from '../../../../app/utils/consts';
import TestUtils from 'react-addons-test-utils';
import stubApp from '../../../utils/stub-app';
import ClusterHeader from '../../../../app/components/cluster/cluster-header';

const should = chai.should();

const mockPeriod = Const.shared.timeperiod[2];

describe('Cluster Header', () => {
  let node;
  let instance;
  let spy;

  beforeEach(() => {
    spy = sinon.spy();
    const stub = stubApp()(ClusterHeader, {
      period: mockPeriod,
      onPeriodSelect: spy
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
    dom.childNodes.length.should.eql(2);

    const barChartIcon = TestUtils.findRenderedDOMComponentWithClass(instance, 'fa-bar-chart');
    const heading = dom.childNodes[0].childNodes[0];
    const periodPills = dom.childNodes[1].childNodes[0];

    should.exist(barChartIcon);
    (heading.className.indexOf('page-header') !== -1).should.be.ok;
    (heading.textContent.indexOf('Dashboard') !== -1).should.be.ok;

    (periodPills.className.indexOf('page-header') !== -1).should.be.ok;
    periodPills.childNodes[0].tagName.should.eql('UL');
    periodPills.childNodes[0].childNodes.length.should.eql(Const.shared.timeperiod.length);
  });

  it('should fire handlePillClick if select a unactived period', () => {
    const dom = ReactDOM.findDOMNode(instance);
    const periodPills = dom.childNodes[1].childNodes[0];
    const hour1 = periodPills.childNodes[0].childNodes[1];
    TestUtils.Simulate.click(hour1);
    spy.should.have.been.calledOnce;
  });

  it('should not fire handlePillClick if select an actived period', () => {
    const dom = ReactDOM.findDOMNode(instance);
    const periodPills = dom.childNodes[1].childNodes[0];
    const hour2 = periodPills.childNodes[0].childNodes[2];
    TestUtils.Simulate.click(hour2);
    spy.should.not.have.been.calledOnce;
  });
});
