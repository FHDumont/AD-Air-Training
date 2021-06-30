#!/bin/bash

current_dir=$(pwd)
script_dir=$(dirname $0)

if [ $script_dir = '.' ]
then
  script_dir="$current_dir"
fi

docker-compose -f $script_dir/docker-compose-application.yml down

docker system prune -f