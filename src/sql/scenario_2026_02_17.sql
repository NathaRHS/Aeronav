-- =============================================
-- Scenario: 17/02/2026 (selon capture ecran)
-- =============================================
-- Donnees:
-- - Vehicules: vehicule1, vehicule2, vehicule3 (Diesel)
-- - Parametres: temps_attente=30, vitesse_moyenne=50
-- - Hotels: hotel0 (aeroport), hotel1, hotel2, hotel3
-- - Distances:
--   hotel0->hotel1=90, hotel0->hotel2=65, hotel0->hotel3=45
--   hotel1->hotel2=10, hotel2->hotel3=5, hotel3->hotel1=20
-- - Reservations:
--   Client1 16 pers 10:00 hotel1
--   Client2 8  pers 11:00 hotel2
--   Client3 18 pers 12:00 hotel3

USE reservationnavettejpa;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE reservation;
TRUNCATE TABLE voiture;
TRUNCATE TABLE distanceHotel;()
TRUNCATE TABLE hotel;
SET FOREIGN_KEY_CHECKS = 1;

-- Parametres
INSERT INTO parametre_systeme (cle_parametre, valeur_parametre)
VALUES
('TEMPS_ATTENTE_MINUTES', '30'),
('VITESSE_MOYENNE_KMH', '50')
ON DUPLICATE KEY UPDATE valeur_parametre = VALUES(valeur_parametre);

-- Types carburant (on force au moins Diesel)
INSERT INTO type_carburant (id_type_carburant, libelle, priorite_allocation)
VALUES
(1, 'Diesel', 1),
(2, 'Essence', 2),
(3, 'Hybride', 3),
(4, 'Electrique', 4)
ON DUPLICATE KEY UPDATE
libelle = VALUES(libelle),
priorite_allocation = VALUES(priorite_allocation);

-- Hotels
INSERT INTO hotel (id_hotel, nom, adresse, distance_from_aeroport, is_aeroport) VALUES
(1, 'hotel0', 'Aeroport', 0, 1),
(2, 'hotel1', 'Adresse hotel1', 90, 0),
(3, 'hotel2', 'Adresse hotel2', 65, 0),
(4, 'hotel3', 'Adresse hotel3', 45, 0);

-- Distances entre hotels
INSERT INTO distanceHotel (idDistance, idHotelDepart, idHotelArrivee, distanceEntreHotel) VALUES
(1, 1, 2, 90), -- hotel0 -> hotel1
(2, 1, 3, 65), -- hotel0 -> hotel2
(3, 1, 4, 45), -- hotel0 -> hotel3
(4, 2, 3, 10), -- hotel1 -> hotel2
(5, 3, 4, 5),  -- hotel2 -> hotel3
(6, 4, 2, 20); -- hotel3 -> hotel1

-- Vehicules (noms exacts demandes)
INSERT INTO voiture (id_voiture, marque, nb_places, id_type_carburant, type_carburant, nb_trajet) VALUES
(1, 'vehicule1', 20, 1, 'Diesel', 0),
(2, 'vehicule2', 16, 1, 'Diesel', 0),
(3, 'vehicule3', 10, 1, 'Diesel', 0);

-- Reservations (date = 17/02/2026)
INSERT INTO reservation (nom, prenom, nombre_personnes, date_heure_arrivee, id_hotel, is_treated, is_treated_for_voiture) VALUES
('Client1', 'Test', 16, '2026-02-17 10:00:00', 2, 0, 0),
('Client2', 'Test', 8,  '2026-02-17 11:00:00', 3, 0, 0),
('Client3', 'Test', 18, '2026-02-17 12:00:00', 4, 0, 0);

-- Verifications rapides
-- SELECT * FROM parametre_systeme;
-- SELECT id_hotel, nom, is_aeroport FROM hotel ORDER BY id_hotel;
-- SELECT * FROM distanceHotel ORDER BY idDistance;
-- SELECT id_voiture, marque, nb_places, type_carburant FROM voiture ORDER BY id_voiture;
-- SELECT nom, nombre_personnes, date_heure_arrivee, id_hotel FROM reservation ORDER BY date_heure_arrivee;
