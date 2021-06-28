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

docker build -t nodejs-services:latest -f $script_dir/Dockerfile $script_dir
docker image tag nodejs-services fernandodumont/nodejs-services:latest
docker push fernandodumont/nodejs-services:latest
