package com.example.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "voiture")
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voiture")
    private Long idVoiture;

    @Column(name = "marque")
    private String marque;

    @Column(name = "nb_places")
    private int nbPlaces;

    @Column(name = "type_carburant")
    private String typeCarburantLegacy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type_carburant")
    @JsonIgnore
    private TypeCarburant carburant;

    @Transient
    private String typeCarburantInput;

    @Column(name = "nb_trajet")
    private int nbTrajet;

    @Transient
    private VoitureState state;

    public Voiture() {
    }

    public Voiture(String marque, int nbPlaces, String typeCarburant) {
        this.marque = marque;
        this.nbPlaces = nbPlaces;
        setTypeCarburant(typeCarburant);
    }

    public Long getIdVoiture() {
        return idVoiture;
    }

    public void setIdVoiture(Long idVoiture) {
        this.idVoiture = idVoiture;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getTypeCarburant() {
        if (carburant != null && carburant.getLibelle() != null) {
            return carburant.getLibelle();
        }
        if (typeCarburantLegacy != null && !typeCarburantLegacy.trim().isEmpty()) {
            return typeCarburantLegacy;
        }
        return typeCarburantInput;
    }

    public void setTypeCarburant(String typeCarburant) {
        this.typeCarburantInput = typeCarburant;
        this.typeCarburantLegacy = typeCarburant;
    }

    public String getTypeCarburantLegacy() {
        return typeCarburantLegacy;
    }

    public void setTypeCarburantLegacy(String typeCarburantLegacy) {
        this.typeCarburantLegacy = typeCarburantLegacy;
    }

    public TypeCarburant getCarburant() {
        return carburant;
    }

    public void setCarburant(TypeCarburant carburant) {
        this.carburant = carburant;
    }

    public int getPrioriteCarburant() {
        if (carburant != null) {
            return carburant.getPrioriteAllocation();
        }
        String fuel = getTypeCarburant();
        if (fuel != null && fuel.equalsIgnoreCase("Diesel")) {
            return 1;
        }
        return 100;
    }

    public int getNbTrajet() {
        return nbTrajet;
    }

    public void setNbTrajet(int nbTrajet) {
        this.nbTrajet = nbTrajet;
    }

    public VoitureState getState() {
        return state;
    }

    public void setState(VoitureState state) {
        this.state = state;
    }
}
