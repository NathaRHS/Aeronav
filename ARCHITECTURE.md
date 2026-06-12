# 🏨 Système de Gestion Hôtel - Architecture MVC

## 📋 Vue d'ensemble

Application Spring JPA pour la gestion intégrée de :
- 📋 Réservations
- 🏩 Hôtels
- 🚗 Véhicules et optimisation d'itinéraires

## 🏗️ Architecture du Projet

### Structure MVC

```
src/main/java/com/example/app/
├── controller/              # Contrôleurs REST
│   ├── ReservationController.java
│   ├── HotelController.java
│   └── VoitureController.java
├── service/                 # Logique métier
│   ├── ReservationService.java
│   ├── VoitureService.java
│   └── VoitureStateService.java
├── entity/                  # Entités JPA
│   ├── Reservation.java
│   ├── Hotel.java
│   ├── Voiture.java
│   └── Planning.java
├── repository/              # Accès aux données
│   ├── ReservationRepository.java
│   ├── HotelRepository.java
│   ├── VoitureRepository.java
│   └── impl/               # Implémentations
├── config/                  # Configuration Spring
│   ├── WebConfig.java
│   └── DispatcherServletConfig.java
└── Main.java / WebApplication.java

src/main/resources/
├── index.html              # Dashboard principal
├── welcome.html            # Page d'accueil
├── application.properties  # Configuration application
├── applicationContext.xml  # Configuration Spring
└── persistence.xml         # Configuration JPA
```

## 🎯 Endpoints API

### Réservations
- `GET /api/reservations` - Récupérer toutes les réservations
- `GET /api/reservations/{id}` - Récupérer une réservation
- `POST /api/reservations` - Créer une réservation
- `PUT /api/reservations/{id}` - Mettre à jour une réservation
- `DELETE /api/reservations/{id}` - Supprimer une réservation
- `GET /api/reservations/filtered?date=2024-01-01` - Filtrer par date
- `POST /api/reservations/optimize/{date}` - Optimiser les voitures

### Hôtels
- `GET /api/hotels` - Récupérer tous les hôtels
- `GET /api/hotels/{id}` - Récupérer un hôtel
- `POST /api/hotels` - Créer un hôtel
- `PUT /api/hotels/{id}` - Mettre à jour un hôtel
- `DELETE /api/hotels/{id}` - Supprimer un hôtel
- `GET /api/hotels/{id}/reservations` - Récupérer les réservations d'un hôtel

### Véhicules
- `GET /api/voitures` - Récupérer tous les véhicules
- `GET /api/voitures/{id}` - Récupérer un véhicule
- `POST /api/voitures` - Créer un véhicule
- `PUT /api/voitures/{id}` - Mettre à jour un véhicule
- `DELETE /api/voitures/{id}` - Supprimer un véhicule
- `GET /api/voitures/plus-petite-place` - Récupérer le véhicule avec le moins de places

## 🚀 Démarrage

### Prérequis
- Java 8+
- Maven
- MySQL

### Installation

1. **Configurer la base de données MySQL**
   ```sql
   CREATE DATABASE hotel_db;
   ```

2. **Mettre à jour `application.properties`**
   ```properties
   spring.datasource.username=votre_utilisateur
   spring.datasource.password=votre_mot_de_passe
   ```

3. **Compiler et exécuter**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   OU via l'exec-maven-plugin :
   ```bash
   mvn compile exec:java -Dexec.mainClass="com.example.app.WebApplication"
   ```

4. **Accéder à l'application**
   - Page d'accueil: http://localhost:8080/welcome.html
   - Dashboard: http://localhost:8080/index.html

## 🎨 Interface Utilisateur

### Dashboard Principal
- **Statistiques** : Nombre total de réservations, hôtels et véhicules
- **Tabs de gestion** :
  - Réservations (lister, créer, optimiser)
  - Hôtels (lister, créer)
  - Véhicules (lister, créer)

### Fonctionnalités
- ✅ CRUD complet pour les 3 entités
- ✅ Interface responsive moderne
- ✅ Optimisation automatique des voitures par date
- ✅ Gestion des associations (hôtel ↔ réservation)
- ✅ Filtrage et recherche

## 🔄 Flux de Données

```
Frontend (HTML/JS) 
    ↓↑ API REST (JSON)
Controller (Spring)
    ↓↑ Service Layer
Business Logic
    ↓↑ Repository
Database (MySQL/JPA)
```

## 🛠️ Technologies Utilisées

- **Framework**: Spring Framework 5.3.33
- **ORM**: Hibernate 5.6.15, JPA 2.2
- **Base de données**: MySQL 8.0
- **Build Tool**: Maven
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **API**: REST avec Spring MVC

## 📝 Notes d'Implémentation

### Controllers
- Architecture RESTful pure
- Response Entity pour gestion des codes HTTP
- CrossOrigin pour éviter les erreurs CORS
- Gestion d'erreurs avec HttpStatus

### Services
- Logique métier indépendante des contrôleurs
- Optimisation des voitures par date et nombre de personnes
- Gestion des contextes Spring pour les repositories

### Vue (Frontend)
- Dashboard avec onglets pour chaque section
- Formulaires réactifs
- Appels API asynchrones (Fetch API)
- Gestion dynamique du contenu sans rechargement

## 🎓 Améliorations Possibles

- [ ] Authentification et autorisation (Spring Security)
- [ ] Pagination des listes
- [ ] Validation côté serveur (Bean Validation)
- [ ] Tests unitaires et d'intégration
- [ ] Documentation Swagger/OpenAPI
- [ ] Caching avec Redis
- [ ] Notifications en temps réel (WebSocket)
- [ ] Export PDF/Excel
- [ ] Gestion des images pour les hôtels
- [ ] Historique des modifications

## 📞 Support

Pour toute question ou problème, consultez les logs d'application dans:
- Console d'exécution Maven
- Fichier `logback.xml` pour la configuration logging

---

**Créé en 2026** | Système de gestion hôtel JPA + Spring
