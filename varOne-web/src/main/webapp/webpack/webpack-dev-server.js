'use strict';

require('babel/register');

var debug = require('debug')('dev');
var webpack = require('webpack');
var WebpackDevServer = require('webpack-dev-server');

var devConfig = require('./dev-config');

var compiler = webpack(devConfig.webpack);
var devServer = new WebpackDevServer(compiler, devConfig.server.options);

devServer.listen(devConfig.server.port, 'localhost', function () {
  debug('webpack-dev-server listen on port %s', devConfig.server.port);
});
