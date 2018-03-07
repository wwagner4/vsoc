#!/usr/bin/env bash
java -Xmx7g \
 -cp log4j.properties:$HOME/prj/vsoc/vsoc-ga-2018/trainga/target/scala-2.12/trainga-assembly-0.0.1-SNAPSHOT.jar \
 vsoc.ga.trainga.gui.GuiPopulationMain $@