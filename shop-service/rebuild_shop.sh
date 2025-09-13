#!/bin/bash

docker ps -a -q --filter "ancestor=shop-service" | xargs -r docker stop
docker ps -a -q --filter "ancestor=shop-service" | xargs -r docker rm

docker rmi shop-service

mvn clean install -DskipTests
mvn jib:dockerBuild