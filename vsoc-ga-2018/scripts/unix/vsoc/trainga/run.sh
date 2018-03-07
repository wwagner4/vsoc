#!/usr/bin/env bash
java -Xmx7g \
 -Dlogfile.name=$HOME/work/work-vsoc-ga-2018/logs/vsoc-ga-2018.log
 -cp log4j.properties:$HOME/prj/vsoc/vsoc-ga-2018/trainga/target/scala-2.12/trainga-assembly-0.0.1-SNAPSHOT.jar \
 vsoc.ga.trainga.ga.TrainGaMain $@