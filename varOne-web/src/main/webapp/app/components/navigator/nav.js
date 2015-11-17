import React from 'react';
import Brand from './brand';
import Header from './header';
import LeftSide from './left-side';


class Nav extends React.Component {

  static propTypes = {
    jobItemClickCB: React.PropTypes.func,
    dimensionItemClickCB: React.PropTypes.func
  }

  render(){
    return(
      <nav className="navbar navbar-default navbar-static-top" role="navigation" style={{marginBottom: 0}}>
          <Brand/>
          <div>
            <Header/>
            <LeftSide
                jobItemClickCB={this.props.jobItemClickCB}
                dimensionItemClickCB={this.props.dimensionItemClickCB}/>
          </div>
      </nav>
    );
  }
}

export default Nav;
