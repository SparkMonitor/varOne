const K = 1024;
const M = 1048576;
const G = 1073741824;
const T = 1099511627776;


function getRoundToN(num, pos) {
  const N = Math.pow(10, pos);
  return Math.round(num * N) / N;
}


export function byteFormatter(d) {
  let result = d;
  if (result < M) {
    result = result / K;
    result = getRoundToN(result, 2);
    return result + ' KB';
  } else if (result >= M && result < G) {
    result = result / M;
    result = getRoundToN(result, 2);
    return result + ' MB';
  } else if (result >= G && result < T) {
    result = result / G;
    result = getRoundToN(result, 2);
    return result + ' GB';
  } else {
    result = result / T;
    result = getRoundToN(result, 2);
    return result + ' TB';
  }
}

export function percentageFormatter(d) {
  let result = d;
  result = getRoundToN(result, 2);
  return result * 100 + ' %';
}

export function millisFormatter(d) {
  const second = d / 1000;
  if (second < 60) {
    return second + ' second';
  } else if (second >= 60) {
    return second / 60 + ' minute';
  }
}

export function dateFormatter(timestamp) {
  return new Date(timestamp * 1);
}
