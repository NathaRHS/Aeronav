package com.example.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parametre_systeme")
public class ParametreSysteme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametre")
    private Long idParametre;

    @Column(name = "cle_parametre", nullable = false, unique = true)
    private String cleParametre;

    @Column(name = "valeur_parametre", nullable = false)
    private String valeurParametre;

    public ParametreSysteme() {
    }

    public ParametreSysteme(String cleParametre, String valeurParametre) {
        this.cleParametre = cleParametre;
        this.valeurParametre = valeurParametre;
    }

    public Long getIdParametre() {
        return idParametre;
    }

    public void setIdParametre(Long idParametre) {
        this.idParametre = idParametre;
    }

    public String getCleParametre() {
        return cleParametre;
    }

    public void setCleParametre(String cleParametre) {
        this.cleParametre = cleParametre;
    }

    public String getValeurParametre() {
        return valeurParametre;
    }

    public void setValeurParametre(String valeurParametre) {
        this.valeurParametre = valeurParametre;
    }
}
