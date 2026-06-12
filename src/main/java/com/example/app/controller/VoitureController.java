package com.example.app.controller;

import com.example.app.entity.TypeCarburant;
import com.example.app.entity.Voiture;
import com.example.app.repository.TypeCarburantRepository;
import com.example.app.repository.VoitureRepository;
import com.example.app.service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/api/voitures")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VoitureController {

    @Autowired
    @Qualifier("voitureImpl")
    private VoitureRepository voitureRepo;

    @Autowired
    private TypeCarburantRepository typeCarburantRepository;

    @GetMapping
    public ResponseEntity<List<Voiture>> getAllVoitures() {
        try {
            return ResponseEntity.ok(voitureRepo.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voiture> getVoitureById(@PathVariable Long id) {
        try {
            Voiture voiture = voitureRepo.findById(id);
            if (voiture != null) {
                return ResponseEntity.ok(voiture);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Voiture> createVoiture(@RequestBody Voiture voiture) {
        try {
            TypeCarburant typeCarburant = typeCarburantRepository.findByLibelle(voiture.getTypeCarburant());
            if (typeCarburant == null) {
                return ResponseEntity.badRequest().build();
            }
            voiture.setCarburant(typeCarburant);
            voiture.setTypeCarburantLegacy(typeCarburant.getLibelle());
            voitureRepo.save(voiture);
            return ResponseEntity.status(HttpStatus.CREATED).body(voiture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voiture> updateVoiture(@PathVariable Long id, @RequestBody Voiture voiture) {
        try {
            Voiture existing = voitureRepo.findById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }

            TypeCarburant typeCarburant = typeCarburantRepository.findByLibelle(voiture.getTypeCarburant());
            if (typeCarburant == null) {
                return ResponseEntity.badRequest().build();
            }

            voiture.setIdVoiture(id);
            voiture.setCarburant(typeCarburant);
            voiture.setTypeCarburantLegacy(typeCarburant.getLibelle());
            voitureRepo.update(voiture);
            return ResponseEntity.ok(voiture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiture(@PathVariable Long id) {
        try {
            voitureRepo.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/plus-petite-place")
    public ResponseEntity<Voiture> getPlusPetitePlace() {
        try {
            List<Voiture> voitures = voitureRepo.findAll();
            if (voitures.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Voiture result = VoitureService.plusPetitePlace(voitures);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
