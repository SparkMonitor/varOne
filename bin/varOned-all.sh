#!/bin/bash

USAGE="Usage: bin/varOned-all.sh [start|stop]"

if [[ "$1" == "" ]]; then
  echo ${USAGE}
  exit 1
fi

bin=$(dirname "${BASH_SOURCE-$0}")
bin=$(cd "${bin}">/dev/null; pwd)

if [ -L ${BASH_SOURCE-$0} ]; then
  FWDIR=$(dirname $(readlink "${BASH_SOURCE-$0}"))
else
  FWDIR=$(dirname "${BASH_SOURCE-$0}")
fi

if [[ -z "${VARONE_HOME}" ]]; then
  export VARONE_HOME="$(cd "${FWDIR}/.."; pwd)"
fi

if [[ -z "${VARONE_CONF_DIR}" ]]; then
  export VARONE_CONF_DIR="${VARONE_HOME}/conf"
fi

export HOSTLIST="${VARONE_CONF_DIR}/varonedaemond"

remote_cmd="source /etc/profile; cd ${VARONE_HOME}; $bin/varOned.sh ${1}"

for varoned in `cat "$HOSTLIST"`; do
  ssh $varoned "$remote_cmd"
done
