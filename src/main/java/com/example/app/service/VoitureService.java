package com.example.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.app.entity.Voiture;

public class VoitureService {

    public static Voiture plusPetitePlace(List<Voiture> voitures) {
        Voiture voitureMin = voitures.get(0);
        for (Voiture voiture : voitures) {
            if (voiture.getNbPlaces() < voitureMin.getNbPlaces()) {
                voitureMin = voiture;
            }
        }
        return voitureMin;
    }

    public static Voiture traiterVoiture(List<Voiture> listesVoiture, int nombrePersonnes, LocalDateTime dateHeureArrivee) {
        List<Voiture> voiturePossible = getVoiturePossible(listesVoiture, nombrePersonnes, dateHeureArrivee);
        return choisirVoitureAvecPriorites(voiturePossible);
    }

    /**
     * Signature conservee pour compatibilite: isUnique n'est plus utilise.
     * La logique de tie-break est maintenant appliquee dans tous les cas.
     */
    public static Voiture traiterVoiture(List<Voiture> listesVoiture, int nombrePersonnes, LocalDateTime dateHeureArrivee, boolean isUnique) {
        List<Voiture> voiturePossible = getVoiturePossible(listesVoiture, nombrePersonnes, dateHeureArrivee);
        return choisirVoitureAvecPriorites(voiturePossible);
    }

    /**
     * Priorites:
     * 1) voitures non utilisees (state == null)
     * 2) plus petite capacite suffisante
     * 3) a capacite egale, priorite carburant (table type_carburant)
     */
    private static Voiture choisirVoitureAvecPriorites(List<Voiture> voiturePossible) {
        if (voiturePossible == null || voiturePossible.isEmpty()) {
            return null;
        }

        List<Voiture> voituresNonUtilisees = filterVoituresNonUtilisees(voiturePossible);
        List<Voiture> pool = voituresNonUtilisees.isEmpty() ? voiturePossible : voituresNonUtilisees;

        Voiture meilleure = null;
        for (Voiture candidate : pool) {
            if (meilleure == null) {
                meilleure = candidate;
                continue;
            }

            int comparePlaces = Integer.compare(candidate.getNbPlaces(), meilleure.getNbPlaces());
            if (comparePlaces < 0) {
                meilleure = candidate;
                continue;
            }
            if (comparePlaces == 0
                    && candidate.getPrioriteCarburant() < meilleure.getPrioriteCarburant()) {
                meilleure = candidate;
            }

        }

        return meilleure;
    }

    public static List<Voiture> filterVoituresNonUtilisees(List<Voiture> voitures) {
        List<Voiture> voituresNonUtilisees = new ArrayList<>();
        for (Voiture v : voitures) {
            if (v.getState() == null) {
                voituresNonUtilisees.add(v);
            }
        }
        return voituresNonUtilisees;
    }

    public static List<Voiture> getVoiturePossible(List<Voiture> listesVoiture, int nombrePersonnes, LocalDateTime dateHeureArrivee) {
        List<Voiture> voiturePossible = new ArrayList<>();
        for (Voiture v : listesVoiture) {
            if (v.getNbPlaces() >= nombrePersonnes) {
                if (v.getState() != null && v.getState().estDisponible(dateHeureArrivee)) {
                    voiturePossible.add(v);
                } else if (v.getState() == null) {
                    voiturePossible.add(v);
                }
            }
        }
        return voiturePossible;
    }

    public static Voiture getBonneVoiture(List<Voiture> listes, int nombrePersonnes) {
        for (Voiture v : listes) {
            if (v.getNbPlaces() >= nombrePersonnes) {
                return v;
            }
        }
        return null;
    }
}
