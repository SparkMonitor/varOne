var webpack = require('webpack');

module.exports = function(config) {
  config.set({

    // milliseconds
    browserNoActivityTimeout: 40000,

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['mocha', 'sinon', 'chai'],


    // list of files / patterns to load in the browser
    files: [
      'test/setup.js',
      'test/**/*_test.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
      'test/setup.js': ['webpack', 'sourcemap'],
      'test/**/*_test.js': ['webpack', 'sourcemap']
    },


    webpack: {
      devtool: 'inline-source-map',
      cache: true,
      resolve: {
        extensions: ['', '.js'],
        modulesDirectories: ['node_modules', 'app'],
        fallback: __dirname
      },
      module: {
        preLoaders: [],
        loaders: [
          { test: /\.js/, loaders: ['babel'], exclude: /node_modules/ },
        ],
        plugins: [
          new webpack.DefinePlugin({
            'process.env.NODE_ENV': JSON.stringify('test')
          })
        ]
      }
    },


    webpackMiddleware: {
      progress: false,
      stats: false,
      debug: false,
      noInfo: true,
      silent: true
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['dots'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: false,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['PhantomJS'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: true,

    plugins: [
      'karma-mocha',
      'karma-chai',
      'karma-sinon',
      'karma-webpack',
      'karma-sourcemap-loader',
      'karma-phantomjs-launcher'
    ]
  });
};
