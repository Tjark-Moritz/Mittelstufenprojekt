#!/bin/bash

# set -o xtrace

PG_IMAGE_NAME=$1
IMG_NAME=$2
IMG_TAG=$3
IMG_FQN="$IMG_NAME:$IMG_TAG"

CONTAINER_NAME="$IMG_NAME-$IMG_TAG-container"

echo 'killing any existing container that is running w same name'
docker kill $CONTAINER_NAME

echo 'running postgres container and bootstrapping schema/data... please wait.'
docker container run \
  --rm \
  --interactive \
  --tty \
  --detach \
  --volume ${PWD}/data:/var/lib/postgresql/data \
  --volume ${PWD}/init-scripts:/docker-entrypoint-initdb.d \
  --name $CONTAINER_NAME \
  --entrypoint /bin/bash \
  --env POSTGRES_DB=database \
  --env POSTGRES_PASSWORD=password \
  --env PGDATA=data \
$PG_IMAGE_NAME

docker container exec -d $CONTAINER_NAME sh -c 'docker-entrypoint.sh postgres >> bootstrap.log 2>&1'

echo 'waiting for container...this may take a while'
grep -q 'IPv4' <(docker exec $CONTAINER_NAME tail -f /bootstrap.log)

# echo 'removing the initialization SQL files'
docker container exec $CONTAINER_NAME rm -rf /docker-entrypoint-initdb.d/*

echo 'stopping pg'
docker container exec -u postgres $CONTAINER_NAME pg_ctl stop -D /data

# commit it.
echo 'committing the container to a new image'

docker container commit \
--change='CMD postgres' \
--change='ENTRYPOINT ["docker-entrypoint.sh"]' \
--change='USER postgres' \
$CONTAINER_NAME $IMG_FQN

# cleanup!
docker kill $CONTAINER_NAME

echo "successfully built $IMG_FQN"