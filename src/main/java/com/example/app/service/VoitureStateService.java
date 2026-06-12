package com.example.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.app.entity.Voiture;
import com.example.app.entity.VoitureState;

/**
 * Service pour gérer l'état des voitures durant l'allocation
 */
public class VoitureStateService {
    
    /**
     * Créer un état pour chaque voiture (initialement toutes libres)
     * @param voitures la liste de toutes les voitures
     * @return liste des états des voitures
     */
    public static List<VoitureState> creerEtatVoitures(List<Voiture> voitures) {
        List<VoitureState> etats = new ArrayList<>();
        for (Voiture v : voitures) {
            etats.add(new VoitureState(v));
        }
        return etats;
    }
    
    /**
     * Trouver les voitures disponibles à une heure donnée
     * @param etats liste de tous les états de voitures
     * @param heure heure à vérifier
     * @return voitures disponibles
     */
    public static List<VoitureState> trouvVoituresDisponibles(List<VoitureState> etats, LocalDateTime heure) {
        List<VoitureState> disponibles = new ArrayList<>();
        for (VoitureState etat : etats) {
            if (etat.estDisponible(heure)) {
                disponibles.add(etat);
            }
        }
        return disponibles;
    }
    
    /**
     * Filtrer les voitures par capacité
     * @param etats voitures à filtrer
     * @param nbPlacesRequises nombre de places minimum
     * @return voitures avec assez de places
     */
    public static List<VoitureState> filtrerParCapacite(List<VoitureState> etats, int nbPlacesRequises) {
        List<VoitureState> aptes = new ArrayList<>();
        for (VoitureState etat : etats) {
            if (etat.getPlacesRestantes() >= nbPlacesRequises) {
                aptes.add(etat);
            }
        }
        return aptes;
    }
    
    /**
     * Trouver les voitures qui reviennent bientôt (pas encore dispo mais bientôt)
     * @param etats liste de tous les états de voitures
     * @param heure heure actuelle
     * @param delaiMaxMinutes délai maximum acceptable
     * @return voitures qui reviennent dans le délai
     */
    public static List<VoitureState> trouvVoituresQuiReviennent(List<VoitureState> etats, LocalDateTime heure, int delaiMaxMinutes) {
        List<VoitureState> reviennent = new ArrayList<>();
        LocalDateTime delaiMax = heure.plusMinutes(delaiMaxMinutes);
        
        for (VoitureState etat : etats) {
            // Voiture pas dispo maintenant, mais revient avant le délai
            if (!etat.estDisponible(heure) && etat.getHeureLiberee().isBefore(delaiMax)) {
                reviennent.add(etat);
            }
        }
        return reviennent;
    }
    
    /**
     * Sélectionner la meilleure voiture selon les critères
     * Critères dans l'ordre:
     * 1) Plus petite CAPACITÉ TOTALE (pas les restantes !)
     * 2) Déjà roulée (nbKmParcourus max) - TODO à adapter
     * 3) Type carburant - TODO à adapter
     * 4) Random
     * 
     * @param aptes voitures aptes
     * @return meilleure voiture selon critères
     */
    public static VoitureState selectionnerVoiture(List<VoitureState> aptes) {
        if (aptes.isEmpty()) {
            return null;
        }
        
        if (aptes.size() == 1) {
            return aptes.get(0);
        }
        
        // Critère 1: Plus petite CAPACITÉ TOTALE (getNbPlaces, pas placesRestantes!)
        // Critère 2: À capacité égale, préférer Diesel ("gasoil")
        // Critère 3: À égalité, ordre stable par marque (déterministe)
        aptes.sort((a, b) -> {
            int byCapacite = Integer.compare(a.getVoiture().getNbPlaces(), b.getVoiture().getNbPlaces());
            if (byCapacite != 0) {
                return byCapacite;
            }

            int prioriteA = a.getVoiture().getPrioriteCarburant();
            int prioriteB = b.getVoiture().getPrioriteCarburant();
            if (prioriteA != prioriteB) {
                return Integer.compare(prioriteA, prioriteB);
            }

            String marqueA = a.getVoiture().getMarque() == null ? "" : a.getVoiture().getMarque();
            String marqueB = b.getVoiture().getMarque() == null ? "" : b.getVoiture().getMarque();
            return marqueA.compareToIgnoreCase(marqueB);
        });
        
        // DEBUG: afficher les voitures classées
        System.out.println("    DEBUG selectionnerVoiture:");
        for (VoitureState etat : aptes) {
            System.out.println("      - " + etat.getVoiture().getMarque() + ": " + etat.getVoiture().getNbPlaces() + " places (" + etat.getVoiture().getTypeCarburant() + ")");
        }
        
        // Retourner la première (plus petite capacité)
        VoitureState meilleure = aptes.get(0);
        System.out.println("    → Choisie: " + meilleure.getVoiture().getMarque() + " (" + meilleure.getVoiture().getNbPlaces() + " places)");
        
        // Critère 2: TODO - Si égalité, celle qui a déjà roulé (nbKmParcourus)
        // Nécessite d'ajouter un attribut nbKmParcourus à la classe Voiture
        
        // Critère 3: TODO - Type carburant (à définir les préférences)
        
        return meilleure;
    }
    
    /**
     * Assigner une réservation à une voiture et mettre à jour son état
     * Calcul: heureLiberee = heureArrivee + tempsAttente + tempsTrajet
     * tempsTrajet = (2 * distanceAeroport) / vitesse
     * 
     * @param etat état de la voiture
     * @param reservation la réservation à assigner
     * @param tempsAttenteMin minutes d'attente
     * @param vitesseKmH vitesse en km/h
     * @return l'état mis à jour
     */
    public static VoitureState assignerVoiture(VoitureState etat, com.example.app.entity.Reservation reservation, 
                                               int tempsAttenteMin, int vitesseKmH) {
        // Ajouter le client et ajuster les places
        etat.ajoutClient(reservation);
        etat.retirePlace(reservation.getNombrePersonnes());

        // Heure de départ = arrivée du 1er client + attente
        // Heure de libération = départ + A/R jusqu'au plus loin des hôtels du groupe.
        if (etat.getHeureDepart() == null) {
            etat.setHeureDepart(reservation.getDateHeureArrivee().plusMinutes(tempsAttenteMin));
        }

        int distanceMax = 0;
        for (com.example.app.entity.Reservation r : etat.getClients()) {
            if (r.getHotel() != null) {
                distanceMax = Math.max(distanceMax, r.getHotel().getDistanceFromAeroport());
            }
        }

        double distanceTotalKm = 2.0 * distanceMax; // on passe par les plus proches en allant au plus loin
        double tempsTrajetHeures = distanceTotalKm / vitesseKmH;
        long tempsTrajetMinutes = Math.round(tempsTrajetHeures * 60);
        LocalDateTime heureLiberee = etat.getHeureDepart().plusMinutes(tempsTrajetMinutes);
        etat.setHeureLiberee(heureLiberee);

        return etat;
    }
    
    /**
     * Afficher l'état de toutes les voitures (pour DEBUG)
     * @param etats liste des états
     */
    public static void afficherEtatVoitures(List<VoitureState> etats) {
        System.out.println("\n========== ÉTAT ACTUEL DES VOITURES ==========");
        for (VoitureState etat : etats) {
            System.out.println("Voiture: " + etat.getVoiture().getMarque());
            System.out.println("  Places restantes: " + etat.getPlacesRestantes() + "/" + etat.getVoiture().getNbPlaces());
            System.out.println("  Libérée à: " + etat.getHeureLiberee());
            System.out.println("  Clients: " + etat.getClients().size());
            System.out.println();
        }
        System.out.println("============================================\n");
    }
}
