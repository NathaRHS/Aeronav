-- =============================================
-- Nouveau jeu de donnees de test
-- Base: reservationnavettejpa
-- Objectif: tester regroupement + reaffectation des voitures
-- =============================================

USE reservationnavettejpa;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE reservation;
TRUNCATE TABLE voiture;
TRUNCATE TABLE distanceHotel;
TRUNCATE TABLE hotel;
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- PARAMETRES ET REFERENTIEL CARBURANT
-- =============================================
INSERT INTO parametre_systeme (cle_parametre, valeur_parametre)
SELECT 'VITESSE_MOYENNE_KMH', '50'
WHERE NOT EXISTS (
    SELECT 1 FROM parametre_systeme WHERE cle_parametre = 'VITESSE_MOYENNE_KMH'
);

INSERT INTO type_carburant (id_type_carburant, libelle, priorite_allocation)
SELECT 1, 'Diesel', 1
WHERE NOT EXISTS (SELECT 1 FROM type_carburant WHERE id_type_carburant = 1);

INSERT INTO type_carburant (id_type_carburant, libelle, priorite_allocation)
SELECT 2, 'Essence', 2
WHERE NOT EXISTS (SELECT 1 FROM type_carburant WHERE id_type_carburant = 2);

INSERT INTO type_carburant (id_type_carburant, libelle, priorite_allocation)
SELECT 3, 'Hybride', 3
WHERE NOT EXISTS (SELECT 1 FROM type_carburant WHERE id_type_carburant = 3);

INSERT INTO type_carburant (id_type_carburant, libelle, priorite_allocation)
SELECT 4, 'Electrique', 4
WHERE NOT EXISTS (SELECT 1 FROM type_carburant WHERE id_type_carburant = 4);

-- =============================================
-- HOTELS (id fixe pour un test deterministe)
-- 1 = Aeroport
-- =============================================
INSERT INTO hotel (id_hotel, nom, adresse, distance_from_aeroport, is_aeroport) VALUES
(1, 'Aeroport International', 'Terminal principal', 0, 1),
(2, 'Hotel Palm', 'Route Nord', 6, 0),
(3, 'Hotel City', 'Centre ville', 12, 0),
(4, 'Hotel Horizon', 'Boulevard Sud', 18, 0),
(5, 'Hotel Lagoon', 'Zone Est', 26, 0),
(6, 'Hotel Sunset', 'Colline Ouest', 34, 0);

-- =============================================
-- DISTANCES ENTRE HOTELS (km)
-- (une seule direction suffit, le repo gere A<->B)
-- =============================================
INSERT INTO distanceHotel (idHotelDepart, idHotelArrivee, distanceEntreHotel) VALUES
(1,2,6),
(1,3,12),
(1,4,18),
(1,5,26),
(1,6,34),
(2,3,8),
(2,4,13),
(2,5,21),
(2,6,30),
(3,4,7),
(3,5,15),
(3,6,24),
(4,5,10),
(4,6,18),
(5,6,11);

-- =============================================
-- VOITURES
-- =============================================
INSERT INTO voiture (id_voiture, marque, nb_places, id_type_carburant, type_carburant, nb_trajet) VALUES
(1, 'Toyota Yaris', 4, 2, 'Essence', 0),
(2, 'Honda Civic', 5, 1, 'Diesel', 0),
(3, 'Renault Scenic', 7, 2, 'Essence', 0),
(4, 'Peugeot 5008', 8, 1, 'Diesel', 0),
(5, 'Mercedes Sprinter', 12, 1, 'Diesel', 0);

-- =============================================
-- RESERVATIONS
-- Date de test: 2026-03-01
-- Fenetres rapprochees pour former des groupes de 30 min
-- =============================================
INSERT INTO reservation (nom, prenom, nombre_personnes, date_heure_arrivee, id_hotel, is_treated, is_treated_for_voiture) VALUES
('Rakoto', 'Aina', 2, '2026-03-01 07:50:00', 2, 0, 0),
('Rabe', 'Lova', 1, '2026-03-01 08:00:00', 3, 0, 0),
('Rasoanaivo', 'Mia', 3, '2026-03-01 08:10:00', 2, 0, 0),
('Randria', 'Kanto', 2, '2026-03-01 08:18:00', 4, 0, 0),

('Ranaivo', 'Soa', 4, '2026-03-01 09:00:00', 5, 0, 0),
('Raharisoa', 'Zo', 2, '2026-03-01 09:06:00', 3, 0, 0),
('Ravelo', 'Tiana', 1, '2026-03-01 09:20:00', 4, 0, 0),

('Razanaka', 'Mamy', 5, '2026-03-01 10:05:00', 6, 0, 0),
('Rabemanana', 'Noro', 2, '2026-03-01 10:10:00', 2, 0, 0),
('Ratsimba', 'Faly', 3, '2026-03-01 10:24:00', 3, 0, 0),

('Andriam', 'Hery', 2, '2026-03-01 11:30:00', 4, 0, 0),
('Ramaro', 'Tovo', 1, '2026-03-01 11:44:00', 2, 0, 0),

('Razan', 'Ivo', 4, '2026-03-01 12:35:00', 5, 0, 0),
('Rajaona', 'Miora', 2, '2026-03-01 12:40:00', 3, 0, 0),
('Rakotobe', 'Nina', 2, '2026-03-01 12:58:00', 2, 0, 0),

('Ratsiraka', 'Ony', 3, '2026-03-01 14:05:00', 6, 0, 0),
('Rami', 'Tahina', 2, '2026-03-01 14:20:00', 4, 0, 0),
('Rana', 'Sitraka', 1, '2026-03-01 14:28:00', 3, 0, 0);

-- =============================================
-- Verifications rapides
-- =============================================
-- SELECT id_hotel, nom, is_aeroport FROM hotel ORDER BY id_hotel;
-- SELECT * FROM distanceHotel ORDER BY idHotelDepart, idHotelArrivee;
-- SELECT date_heure_arrivee, nom, prenom, nombre_personnes, id_hotel FROM reservation ORDER BY date_heure_arrivee;
