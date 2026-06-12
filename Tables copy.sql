INSERT INTO voiture (marque, nbPlaces, typeCarburant) VALUES
('Diesel_4_A', 4, 'Diesel'),
('Diesel_4_B', 4, 'Diesel'),
('Diesel_5_A', 5, 'Diesel'),
('Diesel_7_A', 7, 'Diesel'),
('Diesel_8_A', 8, 'Diesel'),
('Diesel_15_A', 15, 'Diesel');

-- 3) REMPLIR : RÉSERVATIONS (tests Diesel)
-- Prérequis: Hotel id=1 et id=2 existent
INSERT INTO Reservation (nom, prenom, nombrePersonnes, dateHeureArrivee, idHotel) VALUES
('D4_0800', 'Test', 4, '2025-12-05 08:00:00', 1),
('D5_0805', 'Test', 5, '2025-12-05 08:05:00', 2),
('D7_0810', 'Test', 7, '2025-12-05 08:10:00', 1),
('D8_0815', 'Test', 8, '2025-12-05 08:15:00', 1),

-- test regroupement même hôtel avant départ (attente 30 min)
('D2_1048', 'Test', 2, '2025-12-05 10:48:00', 2),
('D2_1050', 'Test', 2, '2025-12-05 10:50:00', 2),

-- test “gros groupe” (prend le 15 places)
('D13_1100', 'Test', 13, '2025-12-05 11:00:00', 1);
