#!/bin/bash

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

if [[ -z "${VARONE_WAR}" ]]; then
  if [[ -d "${VARONE_HOME}/varOne-web/src/main/webapp" ]]; then
    export VARONE_WAR="${VARONE_HOME}/varOne-web/src/main/webapp"
  else
    # export VARONE_WAR=${VARONE_HOME}/varOne-web-0.1.0-beta.war
    export VARONE_WAR=$(find -L "${VARONE_HOME}" -name "varOne-web*.war")
  fi
fi

VARONE_CLASSPATH+="${VARONE_CONF_DIR}:"
VARONE_CLASSPATH+=$(find -L "${VARONE_HOME}" -name "varOne-server*.jar")
VARONE_SERVER=com.varone.server.VarOneServer

function addJarInDir(){
  if [[ -d "${1}" ]]; then
    VARONE_CLASSPATH="${1}/*:${VARONE_CLASSPATH}"
  fi
}

addJarInDir "${VARONE_HOME}/lib"

$(exec java -cp $VARONE_CLASSPATH $VARONE_SERVER "$@")
