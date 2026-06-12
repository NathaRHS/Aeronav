package com.example.app.repository;

import com.example.app.entity.Voiture;
import java.util.List;

public interface VoitureRepository {
    void save(Voiture voiture);

    void update(Voiture voiture);

    void delete(Long id);

    Voiture findById(Long id);

    List<Voiture> findAll();
}
