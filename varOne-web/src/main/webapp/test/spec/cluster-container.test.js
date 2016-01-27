import { defer } from 'lodash';
import React from 'react';
import ReactDOM from 'react-dom';
import TestUtils from 'react-addons-test-utils';
import fauxJax from 'faux-jax';

import ClusterStore from '../../app/stores/cluster-store';
import ClusterContainer from '../../app/components/cluster/cluster-container';

chai.should();

describe('Cluster container', () => {
  let node;
  let instance;

  beforeEach(() => {
    function respond(request) {
      request.respond(
        200,
        { 'Content-Type': 'application/json' },
        `{
          "nodeNum": 5,
          "jobNum": 0,
          "taskNum": 0,
          "executorNum": 0,
          "metricProps": [],
          "taskStartedNumByNode": {
            "server-a5": 0,
            "server-a1": 0,
            "server-a2": 0,
            "server-a3": 0,
            "server-a4": 0
          },
          "executorNumByNode": {
            "server-a5": 0,
            "server-a1": 0,
            "server-a2": 0,
            "server-a3": 0,
            "server-a4": 0
          },
          "propToMetrics": {}
        }
        `
      );
    }

    fauxJax.install();
    fauxJax.on('request', respond);

    node = window.document.createElement('div');
    instance = ReactDOM.render(React.createElement(ClusterContainer), node);
  });

  afterEach(() => {
    fauxJax.restore();
    if (instance) {
      ReactDOM.unmountComponentAtNode(node);
    }
  });

  it('container should render correctly', (done) => {
    function handleChange() {
      defer(() => {
        const container = ReactDOM.findDOMNode(instance);
        container.id.should.eql('page-wrapper');
        container.tagName.should.eql('DIV');
        container.children.length.should.eql(3);
        ClusterStore.unlisten(handleChange);
        return done();
      });
    }

    ClusterStore.listen(handleChange);
  });

  it('container should change period', (done) => {
    let period;

    function handlePeriodChange() {
      defer(() => {
        (instance.state.period === period).should.be.ok;
        ClusterStore.unlisten(handlePeriodChange);
        done();
      });
    }

    function handleChange() {
      defer(() => {
        const container = ReactDOM.findDOMNode(instance);
        const periodCrumb = container.children[0].querySelectorAll('ul');
        periodCrumb.length.should.eql(1);
        const hour6Link = periodCrumb[0].childNodes[3].childNodes[0];
        period = hour6Link.textContent;

        ClusterStore.unlisten(handleChange);
        ClusterStore.listen(handlePeriodChange);
        TestUtils.Simulate.click(hour6Link);
      });
    }

    ClusterStore.listen(handleChange);
  });
});
