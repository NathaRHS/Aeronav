package com.example.app.repository;

import com.example.app.entity.Hotel;
import java.util.List;

public interface HotelRepository {
    void save(Hotel hotel);

    void update(Hotel hotel);

    void delete(Long id);

    Hotel findById(Long id);

    List<Hotel> findAll();

    Long findAirportId();
}
