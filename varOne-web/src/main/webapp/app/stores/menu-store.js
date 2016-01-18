import alt from '../alt';
import Const from '../utils/consts';
import MenuAction from '../actions/menu-action';

class MenuStore {

  constructor() {
    this.bindActions(MenuAction);
    this.runningJobs = [];
    this.leftSideMenu = [
      { name: Const.menu.cluster, icon: 'fa-cloud', collapse: false, children: [] },
      { name: Const.menu.nodes, icon: 'fa-cube', collapse: false, children: [] },
      { name: Const.menu.runningJobs, icon: 'fa-th-list', collapse: true, children: [] },
      { name: Const.menu.history, icon: 'fa-history', collapse: false, children: [] }
    ];
  }

  onFetchRunningJob(result) {
    this.runningJobs = result;
    this.leftSideMenu[2].children = result;
  }
}

export default alt.createStore(MenuStore);
