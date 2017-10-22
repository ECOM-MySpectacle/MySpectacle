# MySpectacle

Projet eCommerce réalisé à Polytech Grenoble dont le but est de créer un site de vente de place de spectacle.

## Installation et lancement 

L'installation requiert Docker et Maven.

```
git clone https://github.com/ECOM2017-MesLocationsVacances/MesLocationsVacances.git

cd MesLocationsVacances

mvn clean compile package 

docker build -t tirandule .

docker run -p 8080:8080 tirandule
```

L'application est alors disponible à [localhost:8080/MySpectacle](http://localhost:8080/MySpectacle)

