var path = require('path');
var node_modules_dir = path.resolve(__dirname, 'node_modules');

var config = {
  entry: path.resolve(__dirname, '../app/main.js'),
  output: {
		 path: path.resolve(__dirname, '../dist'),
		 filename: 'app.js'
  },
  module: {
      loaders: [
        { test: /\.js$/, loaders: ['react-hot', 'babel'], exclude: /node_modules/},/*,
        { test: /\.scss$/, loader: "style-loader!css-loader!sass-loader?includePaths[]="+BS_ASSETS }*/
        {test: /\.less$/, loader: 'style-loader!css-loader!less-loader'},
        {test: /\.css$/, loader: 'style-loader!css-loader'}
      ]
    },
    resolve: {
      extensions: ['', '.js', '.jsx'],
      // root: [path.join(__dirname, "public", "javascripts")],
      modulesDirectories: ['node_modules']
    }

};

module.exports = config;