# MySpectacle

Projet eCommerce réalisé à Polytech Grenoble dont le but est de créer un site de vente de place de spectacle.

## Installation et lancement (local)

L'installation requiert Docker et Maven.

```
git clone https://github.com/ECOM-MySpectacle/MySpectacle.git

cd MySpectacle

mvn clean compile package 

docker build -t tirandule .

docker run -p 8080:8080 -p 25:25 tirandule
```

L'application est alors disponible à [localhost:8080/MySpectacle](http://localhost:8080/MySpectacle)


## Installation et lancement (Amazon EC2)

```
ssh -i key.pem ubuntu@ec2-35-177-143-19.eu-west-2.compute.amazonaws.com
git clone https://github.com/ECOM-MySpectacle/MySpectacle/
cd MySpectacle 
./deployECv2.sh
```

L'application est alors disponible à [http://ec2-35-177-143-19.eu-west-2.compute.amazonaws.com:8080/MyApplication/pages/main.xhtml]http://ec2-35-177-143-19.eu-west-2.compute.amazonaws.com:8080/MyApplication/pages/main.xhtml
