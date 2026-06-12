package com.example.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.app.entity.Planning;
import com.example.app.entity.Reservation;
import com.example.app.entity.Voiture;
import com.example.app.entity.VoitureState;
import com.example.app.repository.ReservationRepository;
import com.example.app.repository.VoitureRepository;
import com.example.app.utils.ListFunction;

@Service
public class ReservationServiceV2 {

    @Autowired
    @Qualifier("reservationRepositoryImpl")
    private ReservationRepository reservationRepo;

    @Autowired
    @Qualifier("voitureImpl")
    private VoitureRepository voitureRepo;

    public List<Reservation> optimize(LocalDate date) {
        List<Reservation> reservationsFiltrees = reservationRepo.findReservationFiltered(date);
        
        // Réinitialiser les flags pour une nouvelle simulation
        for (Reservation r : reservationsFiltrees) {
            r.setTreated(false);
            r.setTreatedForVoiture(false);
            reservationRepo.update(r);
        }
        
        return reservationsFiltrees;
    }

    public List<Planning> MettreVoitureMemeTemps(List<Reservation> reservations) {
        List<Planning> plannings = new ArrayList<>();
        try {
            List<Voiture> voituresListes = voitureRepo.findAll();

            // Vérifier si c'est une réservation unique dans le groupe
            boolean isUnique = reservations.size() == 1;

            for (int i = 0; i < reservations.size(); i++) {
                if (!reservations.get(i).isTreatedForVoiture()) {

                    Voiture v = VoitureService.traiterVoiture(voituresListes, reservations.get(i).getNombrePersonnes(),reservations.get(i).getDateHeureArrivee(), isUnique);

                    Planning p = new Planning(reservations.get(i), v);
                    reservations.get(i).setTreatedForVoiture(true);
                    int reste = v.getNbPlaces() - reservations.get(i).getNombrePersonnes();
                    plannings.add(p);
                    List<Planning> assignedPlannings = new ArrayList<>();
                    assignedPlannings.add(p);

                    LocalDateTime latestDepartureTime = reservations.get(i).getDateHeureArrivee();

                    if (reste != 0) {

                        for (int j = i + 1; j < reservations.size(); j++) {

                            if (reservations.get(j).getNombrePersonnes() < reste  && !reservations.get(j).isTreatedForVoiture()) {
                                Planning p2 = new Planning(reservations.get(j), v);
                                plannings.add(p2);
                                assignedPlannings.add(p2);
                                reste -= reservations.get(j).getNombrePersonnes();
                                reservations.get(j).setTreatedForVoiture(true);

                                if (reservations.get(j).getDateHeureArrivee().isAfter(latestDepartureTime)) {
                                    latestDepartureTime = reservations.get(j).getDateHeureArrivee();
                                }
                            }
                        }
                    }

                    VoitureState voitureState = new VoitureState(v);
                    voitureState.setHeureDepart(latestDepartureTime);
                    LocalDateTime heureLiberee = latestDepartureTime.plusHours(1);
                    long dureeMinutes = 60;
                    for (Planning planning : assignedPlannings) {
                        planning.setHeureDepart(latestDepartureTime);
                        planning.setHeureLiberee(heureLiberee);
                        planning.setDureeMinutes(dureeMinutes);
                    }
                    voitureState.setHeureLiberee(heureLiberee);
                    v.setState(voitureState);
                    voitureRepo.update(v);
                }

            }
            return plannings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plannings;
    }

    public List<List<Reservation>> grouperReservationsMemeTempsAttente(List<Reservation> reservations,
            int tempsAttente) {
        try {
            List<List<Reservation>> reservationsGroupees = new ArrayList<>();
            for (int i = 0; i < reservations.size(); i++) {
                List<Reservation> groupe = new ArrayList<>();
                if (reservations.get(i).isTreated()) {
                    continue;
                }
                reservations.get(i).setTreated(true);
                groupe.add(reservations.get(i));
                for (int j = 1; j < reservations.size(); j++) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Voiture> checkTrajet(List<Voiture> voitures) {
        List<Voiture> voituresTries = ListFunction.trierDecroissantList(voitures);
        return voituresTries;
    }
}
