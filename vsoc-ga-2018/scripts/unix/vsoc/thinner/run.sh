#!/usr/bin/env bash
SCRIPTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
java -Xmx1g \
 -cp ${SCRIPTDIR}:$HOME/prj/vsoc/vsoc-ga-2018/trainga/target/scala-2.12/trainga-assembly-0.0.1-SNAPSHOT.jar \
 vsoc.ga.trainga.thinner.ThinnerMain $@