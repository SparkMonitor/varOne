'use strict'
import fs from 'fs';
import path from 'path';
import debug from 'debug';

const filepath = path.resolve(__dirname, '../../server/webpack-stats.json');

export default function (stats) {
  const publicPath = this.options.output.publicPath;
  var statsObj = stats.toJson();

  let getTrunk = (name, ext) =>{
    let chunks = statsObj.assetsByChunkName[name];
    if(!(Array.isArray(chunks))){
      chunks = [chunks];
    }
    return chunks
      .filter(chunk => ext.test(path.extname(chunk))) // filter by extension
      .map(chunk => `${publicPath}${chunk}`);
  }

  let scripts = getTrunk("main", /js/);

  const content = {scripts};
  fs.writeFileSync(filepath, JSON.stringify(content));
  debug('dev')('`webpack-stats.json` updated');
}
