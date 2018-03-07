#!/usr/bin/env bash
SCRIPTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
java -Xmx7g \
 -Dlogfile.name=$HOME/work/work-vsoc-ga-2018/logs/vsoc-ga-2018.log \
 -cp $SCRIPTDIR:$HOME/prj/vsoc/vsoc-ga-2018/trainga/target/scala-2.12/trainga-assembly-0.0.1-SNAPSHOT.jar \
 vsoc.ga.trainga.ga.TrainGaMain $@