#! /bin/bash

set -x
java -server -Xmx100m -jar target/achilles-0.2.0-standalone.jar $*
