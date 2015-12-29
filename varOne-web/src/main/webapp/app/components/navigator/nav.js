import React from 'react';
import Brand from './brand';
import Header from './header';
import LeftSide from './left-side';

import VarOneConfigModal from '../commons/varOne-conf-modal';

class Nav extends React.Component {

  static propTypes = {
    jobItemClickCB: React.PropTypes.func,
    dimensionItemClickCB: React.PropTypes.func
  }

  render(){
    return(
      <div>
        <nav className="navbar navbar-default navbar-static-top" role="navigation" style={{marginBottom: 0}}>
            <Brand/>
            <div>
              <Header/>
              <LeftSide
                  jobItemClickCB={this.props.jobItemClickCB}
                  dimensionItemClickCB={this.props.dimensionItemClickCB}/>
            </div>
        </nav>
        <VarOneConfigModal />
      </div>
    );
  }
}

export default Nav;
