package com.example.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type_carburant")
public class TypeCarburant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_carburant")
    private Long idTypeCarburant;

    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;

    @Column(name = "priorite_allocation", nullable = false)
    private int prioriteAllocation = 100;

    public TypeCarburant() {
    }

    public TypeCarburant(String libelle, int prioriteAllocation) {
        this.libelle = libelle;
        this.prioriteAllocation = prioriteAllocation;
    }

    public Long getIdTypeCarburant() {
        return idTypeCarburant;
    }

    public void setIdTypeCarburant(Long idTypeCarburant) {
        this.idTypeCarburant = idTypeCarburant;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getPrioriteAllocation() {
        return prioriteAllocation;
    }

    public void setPrioriteAllocation(int prioriteAllocation) {
        this.prioriteAllocation = prioriteAllocation;
    }
}
