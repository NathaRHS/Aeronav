package com.example.app.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.app.entity.Voiture;

public class ListFunction {
    
    public static List<Voiture> trierDecroissantList(List<Voiture>listes){

        Collections.sort(listes, new Comparator<Voiture>() {
            @Override
            public int compare(Voiture v1, Voiture v2) {
                return Integer.compare(v1.getNbTrajet(), v2.getNbTrajet());
            }
        });
        return listes;
    }
    
}
