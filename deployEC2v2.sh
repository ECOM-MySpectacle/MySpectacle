#!/bin/sh

echo "[DEPLOY] Suppression d'instance de container en cours d'execution."

sudo docker stop app
sudo docker rm app

echo "[DEPLOY] Pull des dernières modifications"

git pull

echo "[DEPLOY] Compilation et packaging de l'application "
mvn clean compile package

echo "[DEPLOY] Construction du conteneur"

sudo docker build -t tirandule .

echo "[DEPLOY] Déploiement du conteneur 'app', logs disponible dans le fichier log  "

sudo docker run --name=app -p 8080:8080 tirandule > log &
