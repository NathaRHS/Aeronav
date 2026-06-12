package com.example.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hotel")
    private Long idHotel;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "adresse", columnDefinition = "TEXT")
    private String adresse;
    
    @Column(name = "distance_from_aeroport")
    private int distanceFromAeroport;

    @Column(name = "is_aeroport")
    private boolean isAeroport;
    
    @JsonIgnore
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    public Hotel() {
    }

    public Hotel(String nom, String adresse, int distanceFromAeroport) {
        this.nom = nom;
        this.adresse = adresse;
        this.distanceFromAeroport = distanceFromAeroport;
        this.isAeroport = false;
    }

    public Long getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Long idHotel) {
        this.idHotel = idHotel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getDistanceFromAeroport() {
        return distanceFromAeroport;
    }

    public void setDistanceFromAeroport(int distanceFromAeroport) {
        this.distanceFromAeroport = distanceFromAeroport;
    }

    public boolean isAeroport() {
        return isAeroport;
    }

    public void setAeroport(boolean aeroport) {
        isAeroport = aeroport;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
