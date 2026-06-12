package com.example.app;

import com.example.app.entity.Planning;
import com.example.app.entity.Reservation;
import com.example.app.service.ReservationService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("/logging.properties"));
        } catch (Exception e) {
            // Ignore logging bootstrap errors for local run.
        }

        ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
        try {
            ReservationService reservationService = context.getBean(ReservationService.class);
            List<Reservation> listeReservation = reservationService.optimize(LocalDate.parse("2025-12-05"));

            List<List<Reservation>> groupedReservations =
                    reservationService.grouperReservationsMemeTempsAttente(listeReservation, 30);

            for (List<Reservation> reservations : groupedReservations) {
                System.out.println("\n TRAITEMENT D'UN GROUPE ");
                System.out.println("Nombre de reservations dans le groupe : " + reservations.size());

                for (Reservation r : reservations) {
                    System.out.println("  - ID: " + r.getIdReservation() + " | " + r.getDateHeureArrivee() + " | "
                            + r.getNombrePersonnes() + "p");
                }

                List<Planning> plannings = reservationService.MettreVoitureMemeTemps(reservations);
                System.out.println("Plannings attribues : " + plannings.size());

                for (Planning planning : plannings) {
                    String voiture = planning.getVoiture() != null ? planning.getVoiture().getMarque() : "Aucune";
                    System.out.println("  - Reservation ID: " + planning.getReservation().getIdReservation()
                            + " | Voiture: " + voiture
                            + " | Depart: " + planning.getHeureDepart()
                            + " | Liberee: " + planning.getHeureLiberee());
                }
            }
        } finally {
            context.close();
        }
    }
}
