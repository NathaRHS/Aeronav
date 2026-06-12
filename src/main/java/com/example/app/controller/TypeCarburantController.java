package com.example.app.controller;

import com.example.app.entity.TypeCarburant;
import com.example.app.repository.TypeCarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/type-carburants")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TypeCarburantController {

    @Autowired
    private TypeCarburantRepository typeCarburantRepository;

    @GetMapping
    public ResponseEntity<List<String>> getAllTypeCarburants() {
        try {
            List<String> types = typeCarburantRepository.findAll()
                    .stream()
                    .map(TypeCarburant::getLibelle)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(types);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
