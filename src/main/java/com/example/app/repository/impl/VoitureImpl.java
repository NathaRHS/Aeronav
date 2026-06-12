package com.example.app.repository.impl;

import com.example.app.entity.Reservation;
import com.example.app.entity.Voiture;
import com.example.app.repository.ReservationRepository;
import com.example.app.repository.VoitureRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VoitureImpl implements VoitureRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Voiture voiture) {
        em.persist(voiture);
    }

    public List<Voiture> findAll() {
        return em.createQuery("SELECT v FROM Voiture v", Voiture.class).getResultList();
    }

    @Transactional
    public void update(Voiture voiture) {
        em.merge(voiture);
    }

    @Transactional
    public void delete(Long id) {
        Voiture voiture = em.find(Voiture.class, id);
        if (voiture != null) {
            em.remove(voiture);
        }
    }

    public Voiture findById(Long id) {
        return em.find(Voiture.class, id);
    }

}
