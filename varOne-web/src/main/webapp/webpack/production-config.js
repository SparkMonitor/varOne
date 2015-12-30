var path = require('path');
var webpack = require('webpack');

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
    plugins: [
		//optimizations
		new webpack.optimize.DedupePlugin(),
		new webpack.optimize.OccurenceOrderPlugin(),
		new webpack.optimize.UglifyJsPlugin({
		  compress: {
		    warnings: false,
		    screw_ie8: true,
		    sequences: true,
		    dead_code: true,
		    drop_debugger: true,
		    comparisons: true,
		    conditionals: true,
		    evaluate: true,
		    booleans: true,
		    loops: true,
		    unused: true,
		    hoist_funs: true,
		    if_return: true,
		    join_vars: true,
		    cascade: true,
		    drop_console: true
		  },
		  output: {
		    comments: false
		  }
		})
    ],
    resolve: {
      extensions: ['', '.js', '.jsx'],
      // root: [path.join(__dirname, "public", "javascripts")],
      modulesDirectories: ['node_modules']
    }

};

module.exports = config;