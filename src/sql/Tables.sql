-- Active: 1771244146063@@127.0.0.1@3306@reservationnavettejpa

CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;


CREATE TABLE Client (
    idClient INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    telephone VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE distanceHotel(
    idDistance INT PRIMARY KEY AUTO_INCREMENT,
    idHotelDepart INT NOT NULL,
    idHotelArrivee INT NOT NULL,
    distanceEntreHotel INT NOT NULL,
    FOREIGN KEY (idHotelDepart) REFERENCES hotel(id_hotel) ON DELETE CASCADE,
    FOREIGN KEY (idHotelArrivee) REFERENCES Hotel(id_hotel) ON DELETE CASCADE
);

CREATE TABLE Hotel (
    idHotel INT PRIMARY KEY AUTO_INCREMENT,
    adresse VARCHAR(255) NOT NULL,
    nbChambres INT NOT NULL,
    nom VARCHAR(150),
    telephone VARCHAR(20),
    email VARCHAR(150)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Chambre (
    idChambre INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    prix DECIMAL(10,2) NOT NULL,
    idHotel INT NOT NULL,
    FOREIGN KEY (idHotel) REFERENCES Hotel(idHotel) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Chauffeur (
    idChauffeur INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    email VARCHAR(150)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Voiture (
    idVoiture INT PRIMARY KEY AUTO_INCREMENT,
    marque VARCHAR(50) NOT NULL,
    modele VARCHAR(50) NOT NULL,
    annee INT NOT NULL,
    immatriculation VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Avion (
    idAvion INT PRIMARY KEY AUTO_INCREMENT,
    modele VARCHAR(100) NOT NULL,
    capacite INT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Vol (
    idVol INT PRIMARY KEY AUTO_INCREMENT,
    idAvion INT NOT NULL,
    numeroVol VARCHAR(20) UNIQUE NOT NULL,
    HeureDepart DATETIME NOT NULL,
    HeureArrivee DATETIME NOT NULL,
    NbPassagers INT NOT NULL,
    compagnie VARCHAR(100),
    FOREIGN KEY (idAvion) REFERENCES Avion(idAvion) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Statut (
    idStatut INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Reservation (
    idReservation INT PRIMARY KEY AUTO_INCREMENT,
    idClient INT,
    idVol INT,
    idHotel INT,
    idChambre INT,
    DateArrivee DATE,
    DateDepart DATE,
    dateReservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_reservation INT AUTO_INCREMENT UNIQUE,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    nombre_personnes INT,
    date_heure_arrivee DATETIME,
    id_hotel INT,
    is_treated BOOLEAN DEFAULT 0,
    is_treated_for_voiture BOOLEAN DEFAULT 0,
    FOREIGN KEY (idClient) REFERENCES Client(idClient) ON DELETE CASCADE,
    FOREIGN KEY (idVol) REFERENCES Vol(idVol) ON DELETE SET NULL,
    FOREIGN KEY (idHotel) REFERENCES Hotel(idHotel) ON DELETE SET NULL,
    FOREIGN KEY (idChambre) REFERENCES Chambre(idChambre) ON DELETE SET NULL
);


CREATE TABLE StatutReservation (
    idStatutReservation INT PRIMARY KEY AUTO_INCREMENT,
    idStatut INT NOT NULL,
    idReservation INT NOT NULL,
    dateStatut TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idStatut) REFERENCES Statut(idStatut) ON DELETE CASCADE,
    FOREIGN KEY (idReservation) REFERENCES Reservation(idReservation) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE StatutPaiement (
    idStatutPaiement INT PRIMARY KEY AUTO_INCREMENT,
    idStatut INT NOT NULL,
    idReservation INT NOT NULL,
    montantPaye DECIMAL(10,2),
    datePaiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idStatut) REFERENCES Statut(idStatut) ON DELETE CASCADE,
    FOREIGN KEY (idReservation) REFERENCES Reservation(idReservation) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Planning (
    idPlanning INT PRIMARY KEY AUTO_INCREMENT,
    dateDebutheure DATETIME NOT NULL,
    idChauffeur INT NOT NULL,
    idVoiture INT NOT NULL,
    idVol INT,
    FOREIGN KEY (idChauffeur) REFERENCES Chauffeur(idChauffeur) ON DELETE CASCADE,
    FOREIGN KEY (idVoiture) REFERENCES Voiture(idVoiture) ON DELETE CASCADE,
    FOREIGN KEY (idVol) REFERENCES Vol(idVol) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;







--second table

Table Reservation
    id 
    nomClient
    idVol
    nombreDePassagers
    dateEtHeureArrivee
    idHotel
    

    
     

Table Hotel
    id 
    nom
    distanceAeroport



Table Voiture
    id 
    marque
    immatriculation
    nbPlaces
    IdTypeCarburant

Table Carburant
    id 
    typeCarburant











CREATE DATABASE IF NOT EXISTS ReservationNavetteJPA;
USE ReservationNavetteJPA;

CREATE TABLE voiture (
    idVoiture BIGINT PRIMARY KEY AUTO_INCREMENT,
    marque VARCHAR(255),
    nbPlaces INT NOT NULL,
    typeCarburant VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Données d'insertion pour la table voiture
INSERT INTO voiture (marque, nbPlaces, typeCarburant) VALUES
('Toyota', 5, 'Essence'),
('Mercedes', 7, 'Diesel'),
('Peugeot', 4, 'Essence'),
('Renault', 8, 'Diesel'),
('Audi', 6, 'Diesel'),
('Volkswagen', 5, 'Essence'),
('Fiat', 4, 'Essence'),
('Citroën', 7, 'Diesel'),
('Hyundai', 5, 'Essence'),
('Voiture C', 15, 'Essence'),
('Voiture D', 15, 'Essence');


INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel) VALUES ('Client1048', 'Test', 13, '2025-12-03 10:48:00', 2);
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel) VALUES ('Client1048', 'Test',2, '2025-12-03 10:50:00', 2);


INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel) VALUES ('Client1048', 'Test', 6, '2025-12-03 10:48:00', 1);


-- ==========================================================
-- DONNÉES DE TEST : 1 seule voiture dispo, hôtels différents
-- Objectif : vérifier que même si une voiture a des places,
-- on ne mélange PAS les hôtels (donc la 2e réservation échoue
-- si aucune autre voiture n'est dispo / ne revient dans 30 min).
--
-- Prérequis : Hotel id=1 et Hotel id=2 existent déjà.
-- Date de test proposée : 2025-12-04
-- ==========================================================

-- (Optionnel) Nettoyage des réservations du jour de test
-- DELETE FROM Reservation WHERE dateHeureArrivee >= '2025-12-04 00:00:00' AND dateHeureArrivee < '2025-12-05 00:00:00';

-- Étape A: à 09:10, on occupe TOUTES les voitures sauf "Voiture D"
-- (on utilise l'hôtel 1 pour avoir un retour tardif: +120 min)
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel) VALUES
('Occupe5_A',  'Test', 5,  '2025-12-04 09:10:00', 1),
('Occupe5_B',  'Test', 5,  '2025-12-04 09:10:00', 1),
('Occupe5_C',  'Test', 5,  '2025-12-04 09:10:00', 1),
('Occupe7_A',  'Test', 7,  '2025-12-04 09:10:00', 1),
('Occupe7_B',  'Test', 7,  '2025-12-04 09:10:00', 1),
('Occupe4_A',  'Test', 4,  '2025-12-04 09:10:00', 1),
('Occupe4_B',  'Test', 4,  '2025-12-04 09:10:00', 1),
('Occupe6_A',  'Test', 6,  '2025-12-04 09:10:00', 1),
('Occupe8_A',  'Test', 8,  '2025-12-04 09:10:00', 1),
('Occupe15_A', 'Test', 15, '2025-12-04 09:10:00', 1);

-- Étape B: à 10:00, seule "Voiture D" reste dispo (15 places)
-- Réservation 1 (hôtel 1) prend Voiture D et laisse des places restantes.
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel)
VALUES ('SeuleVoiture_10h00', 'Test', 10, '2025-12-04 10:00:00', 1);

-- Étape C: à 10:05, hôtel DIFFERENT (hôtel 2) et seulement 2 personnes.
-- La voiture aurait des places, mais comme l'hôtel est différent,
-- elle ne doit PAS être regroupée; et comme les autres voitures sont occupées
-- et ne reviennent pas dans 30 min, ça doit générer une "Pas assez de places".
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel)
VALUES ('HotelDiff_10h10', 'Test', 22, '2025-12-04 10:10:00', 2);


-- ==========================================================
-- DONNÉES DE TEST : carburant Diesel vs Essence (+ égalité)
-- Objectif : créer des cas où plusieurs voitures ont la même
-- capacité mais carburant différent (Diesel/Essence).
--
-- Date de test proposée : 2025-12-05
-- Prérequis : Hotel id=1 et Hotel id=2 existent.
-- ==========================================================

-- (Optionnel) Nettoyage du jour de test
-- DELETE FROM Reservation WHERE dateHeureArrivee >= '2025-12-05 00:00:00' AND dateHeureArrivee < '2025-12-06 00:00:00';

-- Ajouter 2 voitures de test (mêmes places, carburant différent)
-- Note: ce sont des ajouts, ça ne remplace pas vos voitures existantes.
INSERT INTO voiture (marque, nbPlaces, typeCarburant) VALUES
('Opel_Test_Diesel_4', 4, 'Diesel'),
('Seat_Test_Diesel_5', 5, 'Diesel');

-- SCÉNARIO A : 4 personnes -> choix entre 4 places Essence (Peugeot/Fiat)
-- et 4 places Diesel (Opel_Test_Diesel_4)
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel)
VALUES ('TestDiesel_A_4p', 'Test', 4, '2025-12-05 08:00:00', 1);

-- SCÉNARIO B : 5 personnes -> choix entre 5 places Essence (Toyota/Volkswagen/Hyundai)
-- et 5 places Diesel (Seat_Test_Diesel_5)
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel)
VALUES ('TestDiesel_B_5p', 'Test', 5, '2025-12-05 08:05:00', 2);

-- SCÉNARIO C : 7 personnes -> seules Mercedes/Citroën (Diesel) sont aptes (selon votre table)
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel)
VALUES ('TestDiesel_C_7p', 'Test', 7, '2025-12-05 08:10:00', 1);

-- SCÉNARIO D : mix “dernier recours” (si vous avez activé la règle)
-- On occupe presque toutes les voitures, puis on force une situation
-- où il reste des places sur une navette Diesel mais hôtel différent.
-- (Optionnel)
-- INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel) VALUES
-- ('Occupe_Test_8p', 'Test', 8, '2025-12-05 09:00:00', 1),
-- ('Occupe_Test_6p', 'Test', 6, '2025-12-05 09:00:00', 1),
-- ('Occupe_Test_5p', 'Test', 5, '2025-12-05 09:00:00', 1),
-- ('Occupe_Test_4p', 'Test', 4, '2025-12-05 09:00:00', 1);
-- INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel)
-- VALUES ('DernierRecours_10p', 'Test', 10, '2025-12-05 10:00:00', 1);
-- INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel)
-- VALUES ('DernierRecours_HotelDiff_2p', 'Test', 2, '2025-12-05 10:05:00', 2);



delete from Reservation;
delete from Voiture;

-- ==========================================
-- REFERENTIELS (nouveau)
-- ==========================================
CREATE TABLE IF NOT EXISTS type_carburant (
    id_type_carburant BIGINT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL UNIQUE,
    priorite_allocation INT NOT NULL DEFAULT 100
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS parametre_systeme (
    id_parametre BIGINT PRIMARY KEY AUTO_INCREMENT,
    cle_parametre VARCHAR(100) NOT NULL UNIQUE,
    valeur_parametre VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Ajouter la relation voiture -> type_carburant si la colonne n'existe pas encore
ALTER TABLE voiture ADD COLUMN IF NOT EXISTS id_type_carburant BIGINT NULL;

INSERT INTO type_carburant (id_type_carburant, libelle, priorite_allocation) VALUES
(1, 'Diesel', 1),
(2, 'Essence', 2),
(3, 'Hybride', 3),
(4, 'Electrique', 4)
ON DUPLICATE KEY UPDATE
libelle = VALUES(libelle),
priorite_allocation = VALUES(priorite_allocation);

INSERT INTO parametre_systeme (cle_parametre, valeur_parametre)
VALUES ('VITESSE_MOYENNE_KMH', '50')
ON DUPLICATE KEY UPDATE valeur_parametre = VALUES(valeur_parametre);
