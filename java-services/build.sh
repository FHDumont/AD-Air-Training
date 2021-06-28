#!/bin/bash

current_dir=$(pwd)
script_dir=$(dirname $0)

if [ $script_dir = '.' ]
then
  script_dir="$current_dir"
elif [[ $script_dir != /* ]]
then
  script_dir="$current_dir/$script_dir"
fi

mvn -f $script_dir/pom.xml clean package -DskipTests=true

EXIT_STATUS=$?

if [[ $EXIT_STATUS -eq 0 ]]
then
	cp $script_dir/target/java-services.jar $script_dir/docker/java-services.jar
	docker build -t java-services -f $script_dir/docker/Dockerfile $script_dir/docker
	docker image tag java-services fernandodumont/java-services:latest
	docker push fernandodumont/java-services:latest
else
	echo "The build failed"
fi
