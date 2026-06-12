package com.example.app.repository.impl;

import com.example.app.entity.Reservation;
import com.example.app.repository.ReservationRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Reservation reservation) {
        em.persist(reservation);
    }

    @Override
    @Transactional
    public void update(Reservation reservation) {
        em.merge(reservation);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Reservation reservation = em.find(Reservation.class, id);
        if (reservation != null) {
            em.remove(reservation);
        }
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> findByClientId(Long clientId) {
        
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> findByHotelId(Long hotelId) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.hotel.idHotel = :hotelId", Reservation.class)
                .setParameter("hotelId", hotelId)
                .getResultList();
    }

    @Override
    public List<Reservation> findByDateArriveeAndDateDepart(java.time.LocalDate dateArrivee, java.time.LocalDate dateDepart) {
        
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> findByVolId(Long volId) {
        
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> findByClientIdAndHotelId(Long clientId, Long hotelId) {
        
        return em.createQuery("SELECT r FROM Reservation r WHERE r.hotel.idHotel = :hotelId", Reservation.class)
                .setParameter("hotelId", hotelId)
                .getResultList();
    }
    
    @Override
    public List<Reservation> findReservationFiltered(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return em.createQuery(
                "SELECT r FROM Reservation r WHERE r.dateHeureArrivee >= :start AND r.dateHeureArrivee < :end ORDER BY r.dateHeureArrivee",
                Reservation.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    @Override
    public Long countByHotelId(Long hotelId) {
        return em.createQuery("SELECT COUNT(r) FROM Reservation r WHERE r.hotel.idHotel = :hotelId", Long.class)
                .setParameter("hotelId", hotelId)
                .getSingleResult();
    }
}
