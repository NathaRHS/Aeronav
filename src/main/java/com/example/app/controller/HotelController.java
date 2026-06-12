package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.app.entity.Hotel;
import com.example.app.repository.HotelRepository;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HotelController {

    @Autowired
    @Qualifier("hotelRepositoryImpl") // Spécifie le bean à injecter
    private HotelRepository hotelRepo;

    /**
     * Récupère tous les hôtels
     */
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        try {
            List<Hotel> hotels = hotelRepo.findAll();
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère un hôtel par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        try {
            Hotel hotel = hotelRepo.findById(id);
            if (hotel != null) {
                return ResponseEntity.ok(hotel);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crée un nouvel hôtel
     */
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        try {
            hotelRepo.save(hotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Met à jour un hôtel existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        try {
            Hotel existing = hotelRepo.findById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            hotel.setIdHotel(id);
            hotelRepo.update(hotel);
            return ResponseEntity.ok(hotel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Supprime un hôtel
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        try {
            hotelRepo.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère les réservations d'un hôtel
     */
    @GetMapping("/{id}/reservations")
    public ResponseEntity<?> getHotelReservations(@PathVariable Long id) {
        try {
            Hotel hotel = hotelRepo.findById(id);
            if (hotel != null) {
                return ResponseEntity.ok(hotel.getReservations());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
