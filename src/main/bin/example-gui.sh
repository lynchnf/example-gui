#!/bin/bash
base_dir=$(dirname "$0")
cd $base_dir
java -jar ../lib/example-gui-${pom.version}.jar
exit 0
