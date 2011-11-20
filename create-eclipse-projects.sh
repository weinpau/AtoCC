#!/bin/sh
(cd Backend &&
  for directory in $(find | grep pom.xml)
  do
    PROJECT_DIR=$(dirname $directory)
    (cd $PROJECT_DIR && mvn pax:eclipse -DdownloadSources)
  done
)