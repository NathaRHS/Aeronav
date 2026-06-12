package com.example.app.repository;

import com.example.app.entity.Reservation;
import java.util.List;
import java.time.LocalDate;

public interface ReservationRepository {
    void save(Reservation reservation);

    void update(Reservation reservation);

    void delete(Long id);

    Reservation findById(Long id);

    List<Reservation> findAll();

    List<Reservation> findByClientId(Long clientId);

    List<Reservation> findByHotelId(Long hotelId);

    List<Reservation> findByDateArriveeAndDateDepart(LocalDate dateArrivee, LocalDate dateDepart);

    List<Reservation> findByVolId(Long volId);

    List<Reservation> findByClientIdAndHotelId(Long clientId, Long hotelId);

    Long countByHotelId(Long hotelId);

    List<Reservation> findReservationFiltered(LocalDate date);

}
