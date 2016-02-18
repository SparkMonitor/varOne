// import React from 'react';
// import {HistoryLocation} from 'react-router';
//
// import router from './utils/router';
//
// router.run((Handler) => {
//   React.render(<Handler path={window.location.pathname}/>, document.getElementById('content'));
// });

require('babel/polyfill');

import React from 'react';
import ReactDOM from 'react-dom';
// import { Router, Route } from 'react-router';


// React.render((
//   <Router>
//     <Route path='/' component={require('./components/app')}>
//       <Route path='test' component={require('./components/home')} />
//     </Route>
//   </Router>
// ), document.getElementById('content'));


import App from './components/app';
ReactDOM.render((
  <App />
), document.getElementById('content'));
