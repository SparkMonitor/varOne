require('babel/register');

const debug = require('debug')('dev');
const webpack = require('webpack');
const WebpackDevServer = require('webpack-dev-server');

const devConfig = require('./dev-config');

const compiler = webpack(devConfig.webpack);
const devServer = new WebpackDevServer(compiler, devConfig.server.options);

devServer.listen(devConfig.server.port, 'localhost', function () {
  debug('webpack-dev-server listen on port %s', devConfig.server.port);
});
