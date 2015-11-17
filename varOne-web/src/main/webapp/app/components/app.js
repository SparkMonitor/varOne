import React from 'react';

// class App extends React.Component {
//   render(){
//     return(
//       <div>
//         {this.props.children}
//       </div>
//     );
//   }
// }

import Home from './home';

class App extends React.Component {
  render(){
    return(
      <Home />
    );
  }
}

export default App;



// <div>
//   <h1>App</h1>
//   <ul>
//     <li><Link to="/home">Home</Link></li>
//   </ul>
//   {this.props.children}
// </div>
