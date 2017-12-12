#!/bin/sh

sudo docker stop tir
sudo docker rm tir
git pull
mvn clean compile package
sudo docker build -t tirandule .
sudo docker run --name=tir -p 8080:8080 tirandule > log &
