#!/bin/sh

# Setting versions
VERSION='1.0.1'

cd ..
./gradlew clean build -x test

ROOT_PATH=`pwd`
echo $ROOT_PATH

echo 'API Image Build...'
cd $ROOT_PATH/api && docker build -t api:$VERSION .
echo 'API Image build... done'

echo 'CONSUMER Image Build...'
cd $ROOT_PATH/consumer && docker build -t consumer:$VERSION .
echo 'CONSUMER Image build... done'

echo 'CSS Image Build...'
cd $ROOT_PATH/css && docker build -t css:$VERSION .
echo 'CSS Image build... done'

echo 'nginx Image Build...'
cd $ROOT_PATH/nginx && docker build -t nginx:$VERSION .
echo 'nginx Image build... done'


