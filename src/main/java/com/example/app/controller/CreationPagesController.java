package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreationPagesController {

    @GetMapping("/reservations")
    public String reservationsListPage() {
        return "reservations-list";
    }

    @GetMapping("/reservations/creation")
    public String reservationCreationPage() {
        return "creation-reservation";
    }

    @GetMapping("/voitures/creation")
    public String voitureCreationPage() {
        return "creation-voiture";
    }

    @GetMapping("/hotels")
    public String hotelsPage() {
        return "hotels";
    }

    @GetMapping("/voitures")
    public String voituresPage() {
        return "voitures";
    }

    @GetMapping("/distance-hotels")
    public String distanceHotelsPage() {
        return "distance-hotels";
    }
}
