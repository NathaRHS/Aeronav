package com.example.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.app.entity.Planning;
import com.example.app.utils.ListFunction;
import com.example.app.entity.Reservation;
import com.example.app.entity.Voiture;
import com.example.app.repository.HotelRepository;
import com.example.app.repository.ParametreSystemeRepository;
import com.example.app.repository.ReservationRepository;
import com.example.app.repository.VoitureRepository;
import com.example.app.entity.VoitureState;
import com.example.app.repository.DistanceHotelRepository;

@Service
public class ReservationService {
    private static final long DEFAULT_AIRPORT_HOTEL_ID = 1L;
    private static final double DEFAULT_VITESSE_MOYENNE_KMH = 50.0;
    private static final int DEFAULT_TEMPS_ATTENTE_MINUTES = 30;
    private static final String PARAM_VITESSE_MOYENNE_KMH = "VITESSE_MOYENNE_KMH";
    private static final String PARAM_TEMPS_ATTENTE_MINUTES = "TEMPS_ATTENTE_MINUTES";

    @Autowired
    @Qualifier("reservationRepositoryImpl")
    private ReservationRepository reservationRepo;

    @Autowired
    @Qualifier("voitureImpl")
    private VoitureRepository voitureRepo;

    @Autowired
    private DistanceHotelRepository distanceHotelRepo;

    @Autowired
    @Qualifier("hotelRepositoryImpl")
    private HotelRepository hotelRepo;

    @Autowired
    private ParametreSystemeRepository parametreSystemeRepository;

    public List<Reservation> optimize(LocalDate date) {
        List<Reservation> reservationsFiltrees = reservationRepo.findReservationFiltered(date);
        // Reinitialiser les flags pour une nouvelle simulation
        for (Reservation r : reservationsFiltrees) {
            r.setTreated(false);
            r.setTreatedForVoiture(false);
            reservationRepo.update(r);
        }
        return reservationsFiltrees;
    }

    public List<Planning> MettreVoitureMemeTemps(List<Reservation> reservations, int tempsAttenteMinutes) {
        List<Planning> plannings = new ArrayList<>();
        List<Voiture> voituresListes = voitureRepo.findAll();
        double vitesseMoyenneKmh = parametreSystemeRepository.findDoubleValueByKey(PARAM_VITESSE_MOYENNE_KMH,
                DEFAULT_VITESSE_MOYENNE_KMH);
        if (vitesseMoyenneKmh <= 0) {
            vitesseMoyenneKmh = DEFAULT_VITESSE_MOYENNE_KMH;
        }
        long airportHotelId = resolveAirportHotelId();
        // Trier les reservations par nombre de personnes DESC (plus grandes places en
        // premier)
        reservations.sort((r1, r2) -> Integer.compare(r2.getNombrePersonnes(), r1.getNombrePersonnes()));

        // Verifier si c'est une reservation unique dans le groupe
        boolean isUnique = reservations.size() == 1;

        for (int i = 0; i < reservations.size(); i++) {
            if (!reservations.get(i).isTreatedForVoiture()) {

                Voiture v = VoitureService.traiterVoiture(voituresListes, reservations.get(i).getNombrePersonnes(),
                        reservations.get(i).getDateHeureArrivee(), isUnique);

                // Si aucune voiture disponible, creer un Planning sans voiture
                if (v == null) {
                    Planning p = new Planning(reservations.get(i), null);
                    plannings.add(p);
                    reservations.get(i).setTreatedForVoiture(true);
                    continue;
                }

                Planning p = new Planning(reservations.get(i), v);
                reservations.get(i).setTreatedForVoiture(true);
                int reste = v.getNbPlaces() - reservations.get(i).getNombrePersonnes();
                plannings.add(p);
                List<Planning> assignedPlannings = new ArrayList<>();
                assignedPlannings.add(p);

                List<Reservation> assignedReservations = new ArrayList<>();
                assignedReservations.add(reservations.get(i));

                LocalDateTime latestDepartureTime = reservations.get(i).getDateHeureArrivee();

                if (reste != 0) {
                    for (int j = i + 1; j < reservations.size(); j++) {
                        if (reservations.get(j).getNombrePersonnes() <= reste
                                && !reservations.get(j).isTreatedForVoiture()) {
                            Planning p2 = new Planning(reservations.get(j), v);
                            plannings.add(p2);
                            assignedPlannings.add(p2);
                            reste -= reservations.get(j).getNombrePersonnes();
                            reservations.get(j).setTreatedForVoiture(true);
                            assignedReservations.add(reservations.get(j));

                            if (reservations.get(j).getDateHeureArrivee().isAfter(latestDepartureTime)) {
                                latestDepartureTime = reservations.get(j).getDateHeureArrivee();
                            }
                        }
                    }
                }

                // Regle metier: depart a l'heure du dernier client arrive, sans attente.
                LocalDateTime heureDepart = latestDepartureTime;
                VoitureState voitureState = new VoitureState(v);
                voitureState.setHeureDepart(heureDepart);

                int distanceTotale = calculerDistanceTotaleAvecRetourAeroport(assignedReservations, airportHotelId);
                double dureeHeures = distanceTotale / vitesseMoyenneKmh;
                long dureeMinutes = Math.round(dureeHeures * 60);
                LocalDateTime heureLiberee = heureDepart.plusMinutes(dureeMinutes);

                for (Planning planning : assignedPlannings) {
                    planning.setHeureDepart(heureDepart);
                    planning.setHeureLiberee(heureLiberee);
                    planning.setDureeMinutes(dureeMinutes);
                }

                voitureState.setHeureLiberee(heureLiberee);
                v.setState(voitureState);
                voitureRepo.update(v);
            }
        }
        return plannings;
    }

    public List<Planning> MettreVoitureMemeTemps(List<Reservation> reservations) {
        int tempsAttenteMinutes = resolveTempsAttenteMinutes(DEFAULT_TEMPS_ATTENTE_MINUTES);
        return MettreVoitureMemeTemps(reservations, tempsAttenteMinutes);
    }

    public List<List<Reservation>> grouperReservationsMemeTempsAttente(List<Reservation> reservations,
            int tempsAttente) {
        List<List<Reservation>> reservationsGroupees = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++) {
            List<Reservation> groupe = new ArrayList<>();
            if (reservations.get(i).isTreated()) {
                continue;
            }
            reservations.get(i).setTreated(true);
            groupe.add(reservations.get(i));
            for (int j = i + 1; j < reservations.size(); j++) {
                if (reservations.get(j).getDateHeureArrivee()
                        .isBefore(reservations.get(i).getDateHeureArrivee().plusMinutes(tempsAttente))
                        && reservations.get(j).getDateHeureArrivee()
                                .isAfter(reservations.get(i).getDateHeureArrivee())) {
                    reservations.get(j).setTreated(true);
                    groupe.add(reservations.get(j));
                }
            }
            reservationsGroupees.add(groupe);
        }
        return reservationsGroupees;
    }

    public List<Voiture> checkTrajet(List<Voiture> voitures) {
        List<Voiture> voituresTries = ListFunction.trierDecroissantList(voitures);
        return voituresTries;
    }

    public PlanningViewData buildPlanningViewData(LocalDate date, int tempsAttente) {
        int tempsAttenteMinutes = resolveTempsAttenteMinutes(tempsAttente);
        List<Reservation> listeReservation = optimize(date);
        List<List<Reservation>> groupedReservations = grouperReservationsMemeTempsAttente(listeReservation,
                tempsAttenteMinutes);
        List<List<Planning>> planningsByGroup = new ArrayList<>();
        for (List<Reservation> group : groupedReservations) {
            planningsByGroup.add(MettreVoitureMemeTemps(group, tempsAttenteMinutes));
        }
        return new PlanningViewData(groupedReservations, planningsByGroup);
    }

    private int resolveTempsAttenteMinutes(int requestWaitMinutes) {
        double dbWait = parametreSystemeRepository.findDoubleValueByKey(
                PARAM_TEMPS_ATTENTE_MINUTES,
                requestWaitMinutes > 0 ? requestWaitMinutes : DEFAULT_TEMPS_ATTENTE_MINUTES);
        int resolved = (int) Math.round(dbWait);
        if (resolved <= 0) {
            return requestWaitMinutes > 0 ? requestWaitMinutes : DEFAULT_TEMPS_ATTENTE_MINUTES;
        }
        return resolved;
    }

    private long resolveAirportHotelId() {
        Long airportId = hotelRepo.findAirportId();
        return airportId != null ? airportId : DEFAULT_AIRPORT_HOTEL_ID;
    }

    private int calculerDistanceTotaleAvecRetourAeroport(List<Reservation> assignedReservations, long airportHotelId) {
        Set<Long> hotelIdsUniques = new LinkedHashSet<>();
        for (Reservation reservation : assignedReservations) {
            if (reservation.getHotel() != null && reservation.getHotel().getIdHotel() != null) {
                Long idHotel = reservation.getHotel().getIdHotel();
                if (!idHotel.equals(airportHotelId)) {
                    hotelIdsUniques.add(idHotel);
                }
            }
        }

        if (hotelIdsUniques.isEmpty()) {
            return 0;
        }

        // Regle demandee: l'hotel le plus proche de l'aeroport est servi en premier.
        List<Long> ordreHotels = new ArrayList<>(hotelIdsUniques);
        ordreHotels.sort(Comparator
                .comparingInt(hotelId -> distanceHotelRepo.getDistanceBetweenHotels(airportHotelId, hotelId)));

        int distanceTotale = 0;
        Long precedent = airportHotelId;

        for (Long hotelId : ordreHotels) {
            distanceTotale += distanceHotelRepo.getDistanceBetweenHotels(precedent, hotelId);
            precedent = hotelId;
        }

        // Retour final a l'aeroport
        distanceTotale += distanceHotelRepo.getDistanceBetweenHotels(precedent, airportHotelId);

        return distanceTotale;
    }

    public static class PlanningViewData {
        private final List<List<Reservation>> groupedReservations;
        private final List<List<Planning>> planningsByGroup;

        public PlanningViewData(List<List<Reservation>> groupedReservations, List<List<Planning>> planningsByGroup) {
            this.groupedReservations = groupedReservations;
            this.planningsByGroup = planningsByGroup;
        }

        public List<List<Reservation>> getGroupedReservations() {
            return groupedReservations;
        }

        public List<List<Planning>> getPlanningsByGroup() {
            return planningsByGroup;
        }
    }
}
