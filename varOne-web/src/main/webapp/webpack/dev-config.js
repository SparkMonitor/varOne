import path from 'path';
import webpack from 'webpack';

const WEBPACK_DEV_SERVER_PORT = parseInt(process.env.PORT, 10) + 1 || 3001;
const PUBLIC_PATH = `http://localhost:${WEBPACK_DEV_SERVER_PORT}/assets/`;

export default {
  server: {
    port: WEBPACK_DEV_SERVER_PORT,
    options: {
      publicPath: PUBLIC_PATH,
      hot: true,
      stats: {
        assets: true,
        colors: true,
        version: false,
        hash: false,
        timings: true,
        chunks: false,
        chunkModules: false
      }
    }
  },
  webpack: {
    entry: [
      `webpack-dev-server/client?http://localhost:${WEBPACK_DEV_SERVER_PORT}`,
      'webpack/hot/only-dev-server',
      'bootstrap-loader',
      './app/main.js'
    ],

    publicPath: PUBLIC_PATH,

    output: {
      path: path.join(__dirname, '../build'),
      filename: 'app.js',
      publicPath: PUBLIC_PATH
    },

    module: {
      preLoaders: [
        { test: /\.js$/, exclude: /node_modules/, loader: 'eslint' }
      ],
      loaders: [
        { test: /\.js$/, loaders: [ 'react-hot', 'babel' ], exclude: /node_modules/ },
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
      new webpack.HotModuleReplacementPlugin(),
      new webpack.NoErrorsPlugin(),
      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        'window.jQuery': 'jquery',
        'root.jQuery': 'jquery'
      }),
      new webpack.DefinePlugin({
        'process.env': {
          BROWSER: JSON.stringify(true),
          NODE_ENV: JSON.stringify('development')
        }
      })
    ],

    resolve: {
      extensions: [ '', '.js', '.jsx' ],
      // root: [path.join(__dirname, "public", "javascripts")],
      modulesDirectories: [ 'node_modules' ]
    }
  }
};
