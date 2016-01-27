import webpack from 'webpack';

let coverage;
let reporters;
if (process.env.CIRCLECI) {
  coverage = {
    type: 'lcov',
    dir: process.env.CIRCLE_ARTIFACTS
  };
  reporters = ['coverage', 'coveralls'];
} else {
  coverage = {
    type: 'html',
    dir: 'coverage/'
  };
  reporters = ['progress', 'coverage'];
}

export default function(config) {
  config.set({
    browsers: ['Chrome'],
    browserNoActivityTimeout: 30000,
    frameworks: ['mocha', 'chai'],
    files: ['tests.webpack.js'],
    preprocessors: { 'tests.webpack.js': ['webpack'] },
    reporters: reporters,
    coverageReporter: coverage,
    webpack: {
      entry: [
        'bootstrap-loader',
        './app/main.js'
      ],
      devtool: 'inline',
      module: {
        preLoaders: [
          { test: /\.js$/, exclude: /node_modules/, loader: 'eslint' }
        ],
        loaders: [
          { test: /\.js$/, loader: 'babel', exclude: /node_modules/ },
          { test: /\.scss$/, loaders: [ 'style', 'css', 'sass' ] },
          { test: /\.(woff2?|ttf|eot|svg)$/, loader: 'url?limit=10000' },
          { test: /bootstrap-sass\/assets\/javascripts\//, loader: 'imports?jQuery=jquery' },
          {
            test: /\.(jpe?g|png|gif|svg|woff|woff2|eot|ttf)(\?v=[0-9].[0-9].[0-9])?$/,
            loader: 'file?name=[sha512:hash:base64:7].[ext]',
            exclude: /node_modules\/(?!font-awesome)/
          },
          { test: /\.css$/, loader: 'style-loader!css-loader' }
        ]
      },
      plugins: [
        new webpack.ProvidePlugin({
          $: 'jquery',
          jQuery: 'jquery',
          'window.jQuery': 'jquery',
          'root.jQuery': 'jquery'
        }),
        new webpack.DefinePlugin({
          'process.env': {
            BROWSER: JSON.stringify(true),
            NODE_ENV: JSON.stringify('test')
          }
        })
      ]
    },
    webpackServer: { noInfo: true }
  });
}
