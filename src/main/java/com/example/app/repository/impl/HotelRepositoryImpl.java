package com.example.app.repository.impl;

import com.example.app.entity.Hotel;
import com.example.app.repository.HotelRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class HotelRepositoryImpl implements HotelRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Hotel hotel) {
        em.persist(hotel);
    }

    @Override
    @Transactional
    public void update(Hotel hotel) {
        em.merge(hotel);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Hotel hotel = em.find(Hotel.class, id);
        if (hotel != null) {
            em.remove(hotel);
        }
    }

    @Override
    public Hotel findById(Long id) {
        return em.find(Hotel.class, id);
    }

    @Override
    public List<Hotel> findAll() {
        return em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
    }

    @Override
    public Long findAirportId() {
        List<Long> ids = em.createQuery(
                "SELECT h.idHotel FROM Hotel h WHERE h.isAeroport = true ORDER BY h.idHotel ASC",
                Long.class)
                .setMaxResults(1)
                .getResultList();
        return ids.isEmpty() ? null : ids.get(0);
    }

}
