package com.example.app.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.example.app.entity.Reservation;
import com.example.app.entity.Planning;
import com.example.app.repository.ReservationRepository;
import com.example.app.service.ReservationService;

@Controller
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReservationController {

    @Autowired
    @Qualifier("reservationRepositoryImpl")
    private ReservationRepository reservationRepo;

    @Autowired
    private ReservationService reservationService;

    /**
     * Route simple pour accéder à la page de planning
     */
    @GetMapping("/planning")
    public String getPlanningPage(Model model, @RequestParam(name = "date", required = false) String date) {
        LocalDate dateToUse = (date != null) ? LocalDate.parse(date) : LocalDate.now();
        ReservationService.PlanningViewData viewData = reservationService.buildPlanningViewData(dateToUse, 30);
        model.addAttribute("groupedReservations", viewData.getGroupedReservations());
        model.addAttribute("planningsByGroup", viewData.getPlanningsByGroup());
        return "reservations";
    }

    /**
     * Récupère toutes les réservations
     */
    
    @GetMapping("/groupes")
    public String getGroupedReservations(Model model, @RequestParam(name = "date", required = false) String date) {
        LocalDate dateToUse = (date != null) ? LocalDate.parse(date) : LocalDate.now();
        ReservationService.PlanningViewData viewData = reservationService.buildPlanningViewData(dateToUse, 30);
        model.addAttribute("groupedReservations", viewData.getGroupedReservations());
        model.addAttribute("planningsByGroup", viewData.getPlanningsByGroup());
        return "reservations";
    }

    /**
     * Récupère toutes les réservations (liste complète)
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationRepo.findAll();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère les réservations filtrées par date
     */
    @GetMapping("/filtered")
    public ResponseEntity<List<Reservation>> getReservationsFiltered(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Reservation> reservations = reservationService.optimize(date);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère une réservation par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        try {
            
            Reservation reservation = reservationRepo.findById(id);
            if (reservation != null) {
                return ResponseEntity.ok(reservation);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crée une nouvelle réservation
     */
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            
            reservationRepo.save(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Met à jour une réservation existante
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        try {
            
            Reservation existing = reservationRepo.findById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            reservation.setIdReservation(id);
            reservationRepo.update(reservation);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Supprime une réservation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            
            reservationRepo.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Optimise les voitures pour les réservations d'une date donnée
     */
    @PostMapping("/optimize/{date}")
    public ResponseEntity<List<Planning>> optimizeReservations(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Reservation> reservations = reservationService.optimize(date);
            List<Planning> plannings = reservationService.MettreVoitureMemeTemps(reservations);
            return ResponseEntity.ok(plannings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

