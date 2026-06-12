package com.example.app.controller;

import com.example.app.entity.DistanceHotel;
import com.example.app.repository.DistanceHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/distance-hotels")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DistanceHotelController {

    @Autowired
    private DistanceHotelRepository distanceHotelRepository;

    @GetMapping
    public ResponseEntity<List<DistanceHotel>> getAll() {
        try {
            return ResponseEntity.ok(distanceHotelRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistanceHotel> getById(@PathVariable Long id) {
        try {
            DistanceHotel distanceHotel = distanceHotelRepository.findById(id);
            if (distanceHotel == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(distanceHotel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<DistanceHotel> create(@RequestBody DistanceHotel distanceHotel) {
        try {
            distanceHotelRepository.save(distanceHotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(distanceHotel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistanceHotel> update(@PathVariable Long id, @RequestBody DistanceHotel distanceHotel) {
        try {
            DistanceHotel existing = distanceHotelRepository.findById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            distanceHotel.setIdDistance(id);
            distanceHotelRepository.update(distanceHotel);
            return ResponseEntity.ok(distanceHotel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            distanceHotelRepository.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
