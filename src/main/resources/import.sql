-- Parametrage systeme
INSERT INTO parametre_systeme (cle_parametre, valeur_parametre)
SELECT 'VITESSE_MOYENNE_KMH', '50'
WHERE NOT EXISTS (
    SELECT 1 FROM parametre_systeme WHERE cle_parametre = 'VITESSE_MOYENNE_KMH'
);

-- Referentiel type carburant
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

-- Hotels
INSERT INTO hotel (nom, adresse, distance_from_aeroport, is_aeroport) VALUES
('Hotel Resort Paris', '123 Rue de la Paix, Paris', 25, 0),
('Hotel Luxe Lyon', '456 Boulevard Central, Lyon', 15, 0),
('Hotel Prestige Marseille', '789 Avenue de la Mer, Marseille', 30, 0);

-- Reservations pour le 2025-12-05
INSERT INTO reservation (nom, prenom, nombre_personnes, date_heure_arrivee, id_hotel, is_treated, is_treated_for_voiture) VALUES
('Dupont', 'Jean', 2, '2025-12-05 08:00:00', 1, 0, 0),
('Martin', 'Sophie', 3, '2025-12-05 08:15:00', 1, 0, 0),
('Bernard', 'Pierre', 1, '2025-12-05 08:30:00', 2, 0, 0),
('Thomas', 'Marie', 4, '2025-12-05 08:45:00', 1, 0, 0),
('Robert', 'Anne', 2, '2025-12-05 09:00:00', 3, 0, 0),
('Richard', 'Luc', 2, '2025-12-05 09:10:00', 2, 0, 0),
('Petit', 'Claude', 3, '2025-12-05 09:25:00', 1, 0, 0),
('Durand', 'Isabelle', 1, '2025-12-05 09:40:00', 3, 0, 0),
('Lefevre', 'Marc', 5, '2025-12-05 09:55:00', 2, 0, 0),
('Michel', 'Nathalie', 2, '2025-12-05 10:10:00', 1, 0, 0),
('Garcia', 'Paul', 3, '2025-12-05 10:25:00', 3, 0, 0),
('Rodriguez', 'Veronique', 2, '2025-12-05 10:40:00', 2, 0, 0),
('Martinez', 'Christian', 4, '2025-12-05 10:55:00', 1, 0, 0),
('Hernandez', 'Jacqueline', 1, '2025-12-05 11:10:00', 2, 0, 0),
('Lopez', 'Denis', 2, '2025-12-05 11:25:00', 3, 0, 0),
('Gonzalez', 'Christine', 3, '2025-12-05 11:40:00', 1, 0, 0),
('Wilson', 'Serge', 2, '2025-12-05 11:55:00', 2, 0, 0),
('Anderson', 'Dominique', 4, '2025-12-05 12:10:00', 3, 0, 0),
('Taylor', 'Francine', 1, '2025-12-05 12:25:00', 1, 0, 0),
('Thomas', 'Bernard', 2, '2025-12-05 12:40:00', 2, 0, 0),
('Leclerc', 'Olivier', 3, '2025-12-05 21:00:00', 1, 0, 0);

-- Reservations pour 2025-12-06
INSERT INTO reservation (nom, prenom, nombre_personnes, date_heure_arrivee, id_hotel, is_treated, is_treated_for_voiture) VALUES
('Dubois', 'Laurent', 2, '2025-12-06 07:30:00', 1, 0, 0),
('Moreau', 'Anne', 3, '2025-12-06 08:00:00', 2, 0, 0),
('Simon', 'Marc', 1, '2025-12-06 08:45:00', 3, 0, 0),
('Laurent', 'Claire', 4, '2025-12-06 09:15:00', 1, 0, 0),
('Blanc', 'Jacques', 2, '2025-12-06 10:00:00', 2, 0, 0);

-- Reservations pour 2025-12-07
INSERT INTO reservation (nom, prenom, nombre_personnes, date_heure_arrivee, id_hotel, is_treated, is_treated_for_voiture) VALUES
('Fournier', 'Philippe', 2, '2025-12-07 09:00:00', 1, 0, 0),
('Girard', 'Marie', 3, '2025-12-07 09:30:00', 3, 0, 0),
('Bonnet', 'Jean', 1, '2025-12-07 10:15:00', 2, 0, 0),
('Fontaine', 'Sophie', 5, '2025-12-07 11:00:00', 1, 0, 0);

-- Voitures
INSERT INTO voiture (marque, nb_places, id_type_carburant, type_carburant, nb_trajet) VALUES
('Toyota Prius', 4, 2, 'Essence', 0),
('Honda Civic', 5, 1, 'Diesel', 0),
('Mercedes Sprinter', 8, 1, 'Diesel', 0),
('BMW Serie 3', 5, 2, 'Essence', 0),
('Audi A4', 5, 1, 'Diesel', 0),
('Renault Scenic', 7, 2, 'Essence', 0),
('Peugeot 5008', 8, 1, 'Diesel', 0),
('Citroen C5', 5, 2, 'Essence', 0);

-- Aeroport
INSERT INTO hotel (nom, adresse, distance_from_aeroport, is_aeroport)
VALUES ('Aeroport', 'Aeroport International', 0, 1);

-- Distances
INSERT INTO distanceHotel (idHotelDepart, idHotelArrivee, distanceEntreHotel) VALUES
(4, 1, 10),
(1, 2, 15),
(4, 2, 20),
(4, 3, 25),
(1, 3, 18),
(2, 3, 12);
