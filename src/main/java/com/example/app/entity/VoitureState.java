package com.example.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe wrapper pour tracker l'état d'une voiture durant l'allocation
 * Contient la voiture + ses informations de disponibilité/occupation
 */
public class VoitureState {
    
    private Voiture voiture;
    private LocalDateTime heureLiberee;      // Quand la voiture revient disponible
    private LocalDateTime heureDepart;        // Heure de départ (arrivée + attente)
    private int placesRestantes;              // Nombre de places non utilisées
    private List<Reservation> clients;        // Clients assignés à cette voiture
    
    /**
     * Constructeur : initialiser l'état d'une voiture
     */
    public VoitureState(Voiture voiture) {
        this.voiture = voiture;
        this.heureLiberee = LocalDateTime.now().minusYears(10);  // Très ancien = libre maintenant
        this.heureDepart = null; // pas encore de départ planifié
        this.placesRestantes = voiture.getNbPlaces();
        this.clients = new ArrayList<>();
    }
    
    /**
     * Vérifier si la voiture est disponible à une heure donnée
     * @param heure l'heure à vérifier
     * @return true si la voiture sera libérée avant OU À cette heure
     */
    public boolean estDisponible(LocalDateTime heure) {
        return this.heureLiberee.isBefore(heure) || this.heureLiberee.isEqual(heure);
    }
    
    /**
     * Réduire le nombre de places restantes
     * @param nbPersonnes nombre de personnes à embarquer
     */
    public void retirePlace(int nbPersonnes) {
        this.placesRestantes -= nbPersonnes;
    }
    
    /**
     * Ajouter un client à cette voiture
     * @param res la réservation du client
     */
    public void ajoutClient(Reservation res) {
        this.clients.add(res);
    }
    
    /**
     * Mettre à jour l'heure de libération
     * @param heure la nouvelle heure
     */
    public void setHeureLiberee(LocalDateTime heure) {
        this.heureLiberee = heure;
    }
    
    /**
     * Mettre à jour l'heure de départ
     * @param heure nouvelle heure de départ
     */
    public void setHeureDepart(LocalDateTime heure) {
        this.heureDepart = heure;
    }
    
    // GETTERS
    public Voiture getVoiture() {
        return voiture;
    }
    
    public LocalDateTime getHeureLiberee() {
        return heureLiberee;
    }
    
    public LocalDateTime getHeureDepart() {
        return heureDepart;
    }
    
    public int getPlacesRestantes() {
        return placesRestantes;
    }
    
    public List<Reservation> getClients() {
        return clients;
    }
    
    @Override
    public String toString() {
        return "VoitureState{" +
                "voiture=" + voiture.getMarque() +
                ", heureLiberee=" + heureLiberee +
                ", heureDepart=" + heureDepart +
                ", placesRestantes=" + placesRestantes +
                ", clients=" + clients.size() +
                '}';
    }
}
