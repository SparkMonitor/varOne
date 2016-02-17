#!/bin/bash

USAGE="Usage: bin/varOned.sh [start|stop]"

if [[ "$1" == "" ]]; then
  echo ${USAGE}
  exit 1
elif [[ "$1" != "start" && "$1" != "stop" ]]; then
  echo ${USAGE}
  exit 1
fi

if [[ $SPARK_HOME == ""  ]]; then
  echo Make sure you have
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

if [[ -f "${VARONE_CONF_DIR}/varOne-env.sh" ]]; then
  . "${VARONE_CONF_DIR}/varOne-env.sh"
fi

VARONED_CLASSPATH+="${VARONE_CONF_DIR}"
VARONED_SERVER=com.varone.node.SPMNode

function addJarInDir(){
  if [[ -d "${1}" ]]; then
    VARONED_CLASSPATH="${1}/*:${VARONED_CLASSPATH}"
  fi
}

addJarInDir "${VARONE_HOME}/lib"


if [[ "$1" == "start" ]]; then
  $(nohup java -cp $VARONED_CLASSPATH $VARONED_SERVER -d "${SPARK_HOME}/conf" -p 8000 >varOned.log 2>&1 &)
elif [[ "$1" == "stop" ]]; then
  PID="$(ps -ef | grep SPMNode | grep -v grep | awk '{ print $2 }')"
  if [[ $PID == ""  ]]; then
    echo No Process to kill.
  else
    $(exec kill -9 $PID)
  fi
fi