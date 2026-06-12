package com.example.app.entity;

import java.time.LocalDateTime;

public class Planning {
    private Reservation reservation;
    private Voiture voiture;
    private LocalDateTime heureDepart;
    private LocalDateTime heureLiberee;
    private long dureeMinutes;

    public Planning(Reservation reservation, Voiture voiture) {
        this.reservation = reservation;
        this.voiture = voiture;
    }

    public Planning(Reservation reservation, Voiture voiture, LocalDateTime heureDepart, LocalDateTime heureLiberee) {
        this.reservation = reservation;
        this.voiture = voiture;
        this.heureDepart = heureDepart;
        this.heureLiberee = heureLiberee;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public LocalDateTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalDateTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public LocalDateTime getHeureLiberee() {
        return heureLiberee;
    }

    public void setHeureLiberee(LocalDateTime heureLiberee) {
        this.heureLiberee = heureLiberee;
    }

    public long getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(long dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }
}
