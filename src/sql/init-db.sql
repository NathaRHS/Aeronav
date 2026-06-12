-- ==========================================
-- Script d'initialisation de la base de données
-- Gestion Hôtel - Hotel Management System
-- ==========================================

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hotel_db;

-- Table Hotel
CREATE TABLE IF NOT EXISTS Hotel (
    idHotel BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    adresse TEXT NOT NULL,
    distanceFromAeroport INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table Voiture
CREATE TABLE IF NOT EXISTS Voiture (
    idVoiture BIGINT AUTO_INCREMENT PRIMARY KEY,
    marque VARCHAR(100) NOT NULL,
    modele VARCHAR(100) NOT NULL,
    immatriculation VARCHAR(50) NOT NULL UNIQUE,
    nbPlaces INT NOT NULL,
    etat VARCHAR(50) DEFAULT 'ACTIF',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table Reservation
CREATE TABLE IF NOT EXISTS Reservation (
    idReservation BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    nombrePersonnes INT NOT NULL,
    dateHeureArrivee DATETIME NOT NULL,
    isTreated BOOLEAN DEFAULT FALSE,
    isTreatedForVoiture BOOLEAN DEFAULT FALSE,
    idHotel BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idHotel) REFERENCES Hotel(idHotel) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table Planning (association Reservation - Voiture)
CREATE TABLE IF NOT EXISTS Planning (
    idPlanning BIGINT AUTO_INCREMENT PRIMARY KEY,
    idReservation BIGINT NOT NULL,
    idVoiture BIGINT,
    dateAssignation DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idReservation) REFERENCES Reservation(idReservation) ON DELETE CASCADE,
    FOREIGN KEY (idVoiture) REFERENCES Voiture(idVoiture) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- Données de test
-- ==========================================

-- Insérer des hôtels
INSERT INTO Hotel (nom, adresse, distanceFromAeroport) VALUES
('Hotel Royal', '123 Rue Principale, Antananarivo', 15),
('Hotel Plage Paradis', 'Bord de mer, Nosy Be', 45),
('Hotel Montagne', 'Route de montagne, Ambositra', 200);

-- Insérer des voitures
INSERT INTO Voiture (marque, modele, immatriculation, nbPlaces, etat) VALUES
('Toyota', 'Hiace', 'MG-2001-AA', 14, 'ACTIF'),
('Mercedes', 'Sprinter', 'MG-2002-AA', 16, 'ACTIF'),
('Ford', 'Transit', 'MG-2003-AA', 12, 'ACTIF'),
('Volkswagen', 'Transporter', 'MG-2004-AA', 9, 'ACTIF');

-- Insérer des réservations
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel) VALUES
('Rakoto', 'Jean', 5, '2026-02-01 14:30:00', 1),
('Ramiandrisoa', 'Marie', 3, '2026-02-01 16:45:00', 1),
('Andrianampoinimerina', 'Pierre', 8, '2026-02-02 10:00:00', 2);

-- ==========================================
-- Indexes pour optimisation
-- ==========================================
CREATE INDEX idx_reservation_hotel ON Reservation(idHotel);
CREATE INDEX idx_reservation_date ON Reservation(dateHeureArrivee);
CREATE INDEX idx_planning_reservation ON Planning(idReservation);
CREATE INDEX idx_planning_voiture ON Planning(idVoiture);

-- ==========================================
-- Fin du script
-- ==========================================
