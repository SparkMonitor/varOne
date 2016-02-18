import React from 'react';
import ReactDOM from 'react-dom';
import TestUtils from 'react-addons-test-utils';
import stubApp from '../../../utils/stub-app';
import ClusterSummaryBlock from '../../../../app/components/cluster/cluster-summary-block';

chai.should();

const mockTitle = 'summary_title';
const mockIcon = 'fa-tasks';
const mockPanel = 'panel-green';
const mockValue = 3;

describe('Cluster Summary Block', () => {
  let node;
  let instance;
  let spy;

  beforeEach(() => {
    spy = sinon.spy();
    const stub = stubApp()(ClusterSummaryBlock, {
      title: mockTitle,
      icon: mockIcon,
      panel: mockPanel,
      value: mockValue,
      onSummaryClick: spy
    });
    node = window.document.createElement('div');
    instance = ReactDOM.render(React.createElement(stub), node);
  });

  afterEach(() => {
    if (instance) ReactDOM.unmountComponentAtNode(node);
  });

  it('should render correctly', () => {
    const dom = ReactDOM.findDOMNode(instance);
    const summaryIcon = TestUtils.scryRenderedDOMComponentsWithClass(instance, mockIcon);
    const summaryVal = TestUtils.scryRenderedDOMComponentsWithClass(instance, 'huge');
    const summaryTitle = TestUtils.scryRenderedDOMComponentsWithClass(instance, 'medium');

    dom.tagName.should.eql('DIV');
    dom.childNodes.length.should.eql(2);
    (dom.className.indexOf(mockPanel) !== -1).should.be.ok;
    summaryIcon.length.should.eql(1);
    summaryTitle.length.should.eql(1);
    summaryVal.length.should.eql(1);
    summaryTitle[0].textContent.should.eql(mockTitle);
    summaryVal[0].textContent.should.eql(mockValue.toString());
  });

  it('should summary block click', () => {
    const dom = ReactDOM.findDOMNode(instance);
    const summaryLink = dom.childNodes[1];
    TestUtils.Simulate.click(summaryLink);
    spy.should.have.been.calledOnce;
    spy.should.have.been.calledWith(mockTitle);
  });
});
