# API de recherche

## Définition

L'API de recherche de MySpectacle permet la récupération d'entités sur la base de critères de recherche ou filtres.
Les entités pouvant être recherchées sont les spectacles, les salles, les artistes et notamment les représentations.

## URL

L'API est disponible depuis la section "recherche" :  
http://&lt;site MySpectacle&gt;/api/recherche/&lt;entité&gt;  
où &lt;entité&gt; est l'un des mots clés suivants :
* artistes
* salles
* spectacles
* representations

## Utilisation

### Données échangées

L'utilisation de l'API se fait exclusivement via POST, en transmettant comme données un texte au format JSON décrivant la requête.  
Chaque paquet JSON doit être de la forme suivante :

```json
{
	"page": 1,
	"per_page": 7,
	"filters": {
		"nom filtre 1": "valeur 1",
		"nom filtre 2": {
			"attribut": "valeur"
		},
		"nom filtre 3": 1337
	}
}
```

où :
* **page** est le numéro de page *(entier positif)*
* **per_page** est le nombre d'entités par page *(entier positif)*
* **filters** est la liste des filtres de recherche *(object vide ou non, ouchaque clé correspond à un filtre)*

L'API renverra alors un paquet JSON contenant le résultat de la requête dans ce format :

```json
{
	"pages": 5,
	"total": 32,
	"entities": [
		"liste des entités"
	]
}
```

où :
* **pages** est le nombre total de pages
* **per_page** est le nombre total d'entités correspondant aux filtres
* **entities** est la liste des entités

Si une erreur quelconque survient, par exemple lorsque la requête est erronée, un message d'erreur sera renvoyé :

```json
{
	"error": "message d'erreur"
}
```

### Filtres pour les représentations

Chaque filtre prend un certain type de données, voici la liste des différents filtres disponibles pour les représentations :

* **name** : nom du spectacle  
  *type : chaîne de caractères non vide*  
  *Le nom est sensible à la casse (pour le moment), et correspondront tous les spectacles dont le nom contient cette chaîne de caractères*
* **date** : date de représentation  
  *type : object contenant deux champs date au format YYYY-MM-dd*
* **city** : la ville où a lieu la représentation  
  *type : chaîne de caractères*
  *La ville est sensible à la casse et sera évaluée à l'égalité*
* **genre** : le genre du spectacle  
  *type : liste de genres (chaînes de caractères)*  
  *valeurs : parmi {CONCERT, THEATRE, HUMOUR, SPECTACLE, EXPOSITION, SALON, OPERA, LOISIR, FESTIVAL, CIRQUE, SPORT, CABARET, MUSEE, MONUMENT, PARC, CINEMA, SPECTACLEENFANT}*
* **theme** : le thème du spectacle  
  *type : chaîne de caractères non vide*
  *Le thème est sensible à la casse (pour le moment), et correspondront tous les spectacles dont le thème contient cette chaîne de caractères*
* **public** : l'audience visée  
  *type : liste de publics (chaînes de caractères)*  
  *valeurs : parmi {FAMILLE, COUPLE, AMIS, ENFANT, ADOLESCENT, ADULTE}*
* **avail_seats** : nombre total de places libres  
  *type : entier positif ou nul*  
  *Seules les représentations ayant au moins n places libres seront renvoyées*
* **avail_seats_balcon** : nombre de places balcon libres  
  *type : entier positif ou nul*  
  *Seules les représentations ayant au moins n places libres seront renvoyées*
* **avail_seats_fosse** : nombre de places fosse libres  
  *type : entier positif ou nul*  
  *Seules les représentations ayant au moins n places libres seront renvoyées*
* **avail_seats_orchestre** : nombre de places orchestre libres  
  *type : entier positif ou nul*  
  *Seules les représentations ayant au moins n places libres seront renvoyées*

### Exemples

Les exemples suivants illustrent le format du paquet JSON à envoyer à l'API :

1. On souhaite trouver des représentations ayant lieu entre le 25 novembre 2017 et le 28 novembre 2017, de type théatre :
```json
{
	"page": 1,
	"per_page": 7,
	"filters": {
		"date": {
			"from": "2017-11-25",
			"to": "2017-11-28"
		},
		"genre": [
			"THEATRE"
		]
	}
}
```

2. On souhaite trouver des représentations du spectacle "XYZ" dans la ville de Grenoble :
```json
{
	"page": 1,
	"per_page": 7,
	"filters": {
		"name": "XYZ",
		"city": "Grenoble"
	}
}
```

3. On souhaite trouver les spectacles pour enfant et/ou pour la famille à Grenoble la semaine du 18 au 23 décembre 2017 ayant au moins 5 places de libre dans la partie fosse :
```json
{
	"page": 1,
	"per_page": 7,
	"filters": {
		"public": [
			"ENFANT",
			"FAMILLE"
		],
		"city": "Grenoble",
		"date": {
			"from": "2017-12-18",
			"to": "2017-12-23"
		},
		"avail_seats_fosse": 5
	}
}
```
