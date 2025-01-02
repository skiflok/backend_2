#!/bin/bash

docker compose down
mvn clean install
docker rmi backend
mvn jib:dockerBuild
