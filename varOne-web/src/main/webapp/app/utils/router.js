import Router, { HistoryLocation } from 'react-router';
import routes from '../routes';

const config = { routes };
config.location = HistoryLocation;

export default Router.create(config);
