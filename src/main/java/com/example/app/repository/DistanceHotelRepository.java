package com.example.app.repository;

import com.example.app.entity.DistanceHotel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DistanceHotelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<DistanceHotel> findAll() {
        return entityManager.createQuery(
                "SELECT d FROM DistanceHotel d ORDER BY d.idDistance DESC",
                DistanceHotel.class)
                .getResultList();
    }

    public DistanceHotel findById(Long id) {
        return entityManager.find(DistanceHotel.class, id);
    }

    @Transactional
    public void save(DistanceHotel distanceHotel) {
        entityManager.persist(distanceHotel);
    }

    @Transactional
    public void update(DistanceHotel distanceHotel) {
        entityManager.merge(distanceHotel);
    }

    @Transactional
    public void delete(Long id) {
        DistanceHotel existing = findById(id);
        if (existing != null) {
            entityManager.remove(existing);
        }
    }

    public int getDistanceBetweenHotels(long idHotelDepart, long idHotelArrivee) {
        if (idHotelDepart == idHotelArrivee) {
            return 0;
        }

        try {
            List<Integer> distances = entityManager.createQuery(
                "SELECT d.distanceEntreHotel FROM DistanceHotel d " +
                "WHERE (d.idHotelDepart = :depart AND d.idHotelArrivee = :arrivee) " +
                "   OR (d.idHotelDepart = :arrivee AND d.idHotelArrivee = :depart)",
                Integer.class)
                .setParameter("depart", idHotelDepart)
                .setParameter("arrivee", idHotelArrivee)
                .setMaxResults(1)
                .getResultList();

            return distances.isEmpty() ? 0 : distances.get(0);
        } catch (Exception e) {
            return 0;
        }
    }
}
