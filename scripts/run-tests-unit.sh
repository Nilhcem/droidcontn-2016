#!/bin/bash

# avoid virtual desktop jumps at mac terminal on test run
export JAVA_TOOL_OPTIONS='-Djava.awt.headless=true'

./gradlew :app:assembleProductionUnittest
./gradlew :app:testProductionUnittest

echo "test reports: $(pwd)/app/build/reports/tests/productionUnittest/index.html"
