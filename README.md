# AeroNav — Gestion des reservations et allocation de navettes

Application web Java / Spring Boot pour la gestion d'un service de navettes entre un aeroport
et des hotels. Elle enregistre les reservations clients, gere la flotte de vehicules et les
hotels, puis **optimise automatiquement l'affectation des voitures** en regroupant les trajets
compatibles pour une date donnee.

> Projet academique (ITU, S5 - WEBD) construit autour d'un moteur de planification metier
> et d'une interface monochrome epuree.

---

## Sommaire

- [Apercu](#apercu)
- [Fonctionnalites](#fonctionnalites)
- [Algorithme d'allocation](#algorithme-dallocation)
- [Modele de donnees](#modele-de-donnees)
- [Stack technique](#stack-technique)
- [Demarrage](#demarrage)
- [Structure du projet](#structure-du-projet)
- [Pages et routes](#pages-et-routes)
- [API REST](#api-rest)

---

## Apercu

Le coeur de l'application est un moteur de planification qui, pour une date donnee, repartit les
reservations dans les vehicules disponibles selon des regles de **capacite**, de **disponibilite**
et de **proximite geographique**. Le resultat est consultable sous forme de planning groupe.

L'interface (Thymeleaf + CSS maison) suit un design **noir sur blanc, minimal et professionnel**.

---

## Fonctionnalites

| Module | Description |
|--------|-------------|
| **Reservations** | Liste, creation et suppression des reservations clients |
| **Hotels** | CRUD complet (nom, adresse, distance par rapport a l'aeroport) |
| **Voitures** | CRUD de la flotte (marque, nombre de places, type de carburant) |
| **Distances** | Gestion des distances entre hotels (regroupement des trajets) |
| **Planning** | Visualisation des groupes de reservations et voitures attribuees |
| **Optimisation** | Allocation automatique des vehicules selon l'algorithme metier |

---

## Algorithme d'allocation

Trois parametres constants pilotent l'allocation :

- **Temps d'attente** : 30 minutes (avant qu'une voiture soit liberee)
- **Vitesse moyenne** : 20 km/h
- **Distance de proximite** : 5 km (seuil de regroupement de deux hotels)

### Principe general

1. Recuperer et **trier les reservations par heure d'arrivee** (croissante).
2. Pour chaque reservation, chercher les voitures **disponibles et liberees a temps**.
3. Filtrer par **capacite** (places libres >= nombre de personnes).
4. **Selectionner** la meilleure voiture (criteres ci-dessous).
5. Mettre a jour l'etat du vehicule : places restantes et heure de liberation
   (`heure d'arrivee + 30 min`).

### Criteres de selection (dans l'ordre)

1. Plus **petite capacite** suffisante
2. A egalite : voiture ayant **deja roule** (plus de trajets)
3. A egalite : **preference carburant** (Diesel prioritaire)
4. A egalite : choix **aleatoire**

### Regroupement de clients

Un client rejoint une voiture deja affectee si, et seulement si :

- il reste **assez de places** ;
- son heure d'arrivee est anterieure a `heure de liberation + temps d'attente` ;
- il vise le **meme hotel**, ou un hotel a **moins de 5 km**.

---

## Modele de donnees

| Entite | Champs principaux | Relations |
|--------|-------------------|-----------|
| `Hotel` | nom, adresse, distanceFromAeroport, isAeroport | 1 hotel → N reservations |
| `Reservation` | nom, prenom, nombrePersonnes, dateHeureArrivee | N reservations → 1 hotel |
| `Voiture` | marque, nbPlaces, typeCarburant, nbTrajet | N voitures → 1 type carburant |
| `DistanceHotel` | idHotelDepart, idHotelArrivee, distanceEntreHotel | relie deux hotels |
| `Planning` | heureDepart, heureLiberee, dureeMinutes | associe 1 reservation a 1 voiture (non persiste) |

---

## Stack technique

- **Backend** : Java 8+, Spring Boot 2.7.18, Spring Framework 5.3.33, Spring MVC
- **Persistance** : JPA (javax 2.2), Hibernate 5.6.15, MySQL 8
- **Vues** : Thymeleaf, HTML5, CSS3, JavaScript (Fetch API)
- **Build** : Maven

---

## Demarrage

### Prerequis

- Java 8 ou superieur
- Maven
- MySQL 8

### Installation

1. Creer la base de donnees :

   ```sql
   CREATE DATABASE reservationnavettejpa;
   ```

2. Adapter les identifiants dans `src/main/resources/application.properties` si besoin
   (par defaut : utilisateur `root`, mot de passe `tiger`, port serveur `8082`).

3. Compiler et lancer :

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. Ouvrir [http://localhost:8082/](http://localhost:8082/).

> Le schema est genere automatiquement par Hibernate (`ddl-auto=update`). Des jeux de donnees
> d'exemple sont disponibles dans `Tables copy.sql`.

---

## Structure du projet

```
src/main/
├── java/com/example/app/
│   ├── controller/        # Controleurs REST + pages (Spring MVC)
│   ├── service/           # Logique metier (optimisation, allocation)
│   ├── entity/            # Entites JPA (Hotel, Reservation, Voiture, ...)
│   ├── repository/        # Acces aux donnees (+ impl/)
│   ├── config/            # Configuration Spring (Web, DataSource, ...)
│   └── WebApplication.java # Point d'entree Spring Boot
└── resources/
    ├── templates/         # Vues Thymeleaf (+ fragments/header.html)
    ├── css/app.css        # Design system (monochrome)
    ├── img/logo.png       # Logo AeroNav
    ├── welcome.html       # Page d'accueil
    ├── index.html         # Tableau de bord
    └── application.properties
```

---

## Pages et routes

| Route | Description |
|-------|-------------|
| `/` | Redirige vers la page d'accueil |
| `/planning` | Planning des reservations par date |
| `/reservations` | Liste des reservations |
| `/reservations/creation` | Creation d'une reservation |
| `/hotels` | Gestion des hotels (CRUD) |
| `/voitures` | Gestion des voitures (CRUD) |
| `/voitures/creation` | Creation d'une voiture |
| `/distance-hotels` | Gestion des distances entre hotels |
| `/documentation` | Documentation in-app |

---

## API REST

### Reservations — `/api/reservations`

| Methode | Chemin | Description |
|---------|--------|-------------|
| `GET` | `/` | Liste de toutes les reservations |
| `GET` | `/{id}` | Detail d'une reservation |
| `POST` | `/` | Creer une reservation |
| `PUT` | `/{id}` | Mettre a jour |
| `DELETE` | `/{id}` | Supprimer |
| `GET` | `/filtered?date=YYYY-MM-DD` | Reservations d'une date |
| `POST` | `/optimize/{date}` | Optimiser l'allocation des voitures |
| `GET` | `/groupes` | Vue groupee (page) |

### Hotels — `/api/hotels`

`GET /` · `GET /{id}` · `POST /` · `PUT /{id}` · `DELETE /{id}` · `GET /{id}/reservations`

### Voitures — `/api/voitures`

`GET /` · `GET /{id}` · `POST /` · `PUT /{id}` · `DELETE /{id}` · `GET /plus-petite-place`

### Autres

- `CRUD` `/api/distance-hotels`
- `GET` `/api/type-carburants`

---

*Projet ITU — S5 WEBD. Conçu autour d'un moteur d'allocation de navettes JPA + Spring.*
