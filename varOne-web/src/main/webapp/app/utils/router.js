import Router, {HistoryLocation} from 'react-router';
import routes from '../routes';

let config = {routes};
config.location = HistoryLocation;

let router = Router.create(config);
export default router;
