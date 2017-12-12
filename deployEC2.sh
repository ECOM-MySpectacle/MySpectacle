#!/bin/sh

sudo yum install git

sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
sudo yum install -y apache-maven
mvn --version
 
sudo yum install docker -y
sudo service docker start

echo "Replace target/MyApplication.war by app/MyApplication.war in Dockerfile"


