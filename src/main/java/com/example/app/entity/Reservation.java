package com.example.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long idReservation;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "nombre_personnes")
    private int nombrePersonnes;
    
    @Column(name = "date_heure_arrivee")
    private LocalDateTime dateHeureArrivee;
    
    @Column(name = "is_treated", columnDefinition = "boolean default false")
    private boolean isTreated;
    
    @Column(name = "is_treated_for_voiture", columnDefinition = "boolean default false")
    private boolean isTreatedForVoiture;
    
    @ManyToOne
    @JsonIgnoreProperties({"reservations"})
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    public Reservation() {
    }

    public Reservation(String nom, String prenom, int nombrePersonnes, LocalDateTime dateHeureArrivee, Hotel hotel) {
        this.nom = nom;
        this.prenom = prenom;
        this.nombrePersonnes = nombrePersonnes;
        this.dateHeureArrivee = dateHeureArrivee;
        this.hotel = hotel;
    }

    public Long getIdReservation() {
        return idReservation;
    }
    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public int getNombrePersonnes() {
        return nombrePersonnes;
    }
    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }
    public LocalDateTime getDateHeureArrivee() {
        return dateHeureArrivee;
    }
    public void setDateHeureArrivee(LocalDateTime dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public boolean isTreated() {
        return isTreated;
    }

    public void setTreated(boolean isTreated) {
        this.isTreated = isTreated;
    }

    public boolean isTreatedForVoiture() {
        return isTreatedForVoiture;
    }

    public void setTreatedForVoiture(boolean isTreatedForVoiture) {
        this.isTreatedForVoiture = isTreatedForVoiture;
    }

    public Long getIdHotelDepart() {
        return hotel != null ? hotel.getIdHotel() : null;
    }
}
