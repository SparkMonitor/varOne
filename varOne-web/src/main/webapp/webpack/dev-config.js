import path from 'path';
import webpack from 'webpack';

import writeBundle from './utils/writeBundle';

const WEBPACK_DEV_SERVER_PORT = parseInt(process.env.PORT) + 1 || 3001;
const PUBLIC_PATH = `http://localhost:${WEBPACK_DEV_SERVER_PORT}/assets/`;
const BS_ASSETS = path.resolve(__dirname, './node_modules/bootstrap-sass/assets/stylesheets');

export default {
  server:{
    port: WEBPACK_DEV_SERVER_PORT,
    options:{
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
  webpack:{
    entry:[
      `webpack-dev-server/client?http://localhost:${WEBPACK_DEV_SERVER_PORT}`,
      'webpack/hot/only-dev-server',
      './app/main.js'
    ],

    publicPath: PUBLIC_PATH,

    output:{
      path: path.join(__dirname, '../build'),
      filename: 'app.js',
      publicPath: PUBLIC_PATH
    },

    module: {
      loaders: [
        { test: /\.js$/, loaders: ['react-hot', 'babel'], exclude: /node_modules/},/*,
        { test: /\.scss$/, loader: "style-loader!css-loader!sass-loader?includePaths[]="+BS_ASSETS }*/
        {test: /\.less$/, loader: 'style-loader!css-loader!less-loader'},
        {test: /\.css$/, loader: 'style-loader!css-loader'}
      ]
    },

    plugins: [
      new webpack.HotModuleReplacementPlugin(),
      new webpack.NoErrorsPlugin(),
      new webpack.DefinePlugin({
        'process.env': {
          BROWSER: JSON.stringify(true),
          NODE_ENV: JSON.stringify('development')
        }
      }),
      function(){
        // this.plugin('done', writeBundle);
      }
    ],

    resolve: {
      extensions: ['', '.js', '.jsx'],
      // root: [path.join(__dirname, "public", "javascripts")],
      modulesDirectories: ['node_modules']
    }
  }
}
