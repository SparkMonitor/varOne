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

  const scripts = getTrunk("main", /js/);
  const style = getChunks('app', /css/);

  const imagesRegex = /\.(jpe?g|png|gif|svg)$/;
  const images = json.modules
    .filter(module => imagesRegex.test(module.name))
    .map(image => {
      return {
        original: image.name,
        compiled: `${publicPath}${image.assets[0]}`
      };
    });

  const content = { script, style, images };
  fs.writeFileSync(filepath, JSON.stringify(content));
  debug('dev')('`webpack-stats.json` updated');
}
