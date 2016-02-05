#!/bin/bash
USAGE="Usage: bin/release.sh version_num"

if [[ "$1" == "" ]]; then
  echo "ERROR : please give a release number"
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

gradle clean build shadowJar -x test

if [[ -d "${VARONE_HOME}/varOne-${1}" ]]; then
  rm -rf "${VARONE_HOME}/varOne-${1}"
fi

export VARONE_RELEASE_DIR="${VARONE_HOME}/varOne-${1}"

mkdir ${VARONE_RELEASE_DIR}
mkdir ${VARONE_RELEASE_DIR}/conf
mkdir ${VARONE_RELEASE_DIR}/bin
cp "${VARONE_HOME}/bin/varOne.sh" "${VARONE_RELEASE_DIR}/bin"
cp "${VARONE_HOME}/conf/varOne-env.sh.template" "${VARONE_RELEASE_DIR}/conf"
cp "${VARONE_HOME}/conf/varOne-site.xml.template" "${VARONE_RELEASE_DIR}/conf"
cp -r "${VARONE_HOME}/docs" "${VARONE_RELEASE_DIR}"
cp -r "${VARONE_HOME}/CONTRIBUTING.md" "${VARONE_RELEASE_DIR}"
cp -r "${VARONE_HOME}/LICENSE" "${VARONE_RELEASE_DIR}"
cp -r "${VARONE_HOME}/README.md" "${VARONE_RELEASE_DIR}"
cp -r "${VARONE_HOME}/varOne-server/build/lib" "${VARONE_RELEASE_DIR}"
cp "${VARONE_HOME}/varOne-server/build/libs/varOne-server-${1}.jar" "${VARONE_RELEASE_DIR}"
cp "${VARONE_HOME}/varOne-web/build/libs/varOne-web-${1}.war" "${VARONE_RELEASE_DIR}"

tar zcvf "varOne-${1}.tgz" varOne-${1}
rm -rf ${VARONE_RELEASE_DIR}
echo "Release varOne@${1} successfully."
