package com.example.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.entity.Planning;
import com.example.app.entity.Reservation;
import com.example.app.service.ReservationService;

@Controller
@RequestMapping("/planning")
public class PlanningController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String planning(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "30") int wait,
            Model model) {
        if (date == null) {
            date = LocalDate.now();
        }

        ReservationService.PlanningViewData viewData = reservationService.buildPlanningViewData(date, wait);
        List<List<Reservation>> groupedReservations = viewData.getGroupedReservations();
        List<List<Planning>> planningsByGroup = viewData.getPlanningsByGroup();

        List<GroupePlanningDTO> groupes = new ArrayList<>();
        int idx = 1;
        for (int i = 0; i < groupedReservations.size(); i++) {
            groupes.add(new GroupePlanningDTO(idx++, groupedReservations.get(i), planningsByGroup.get(i)));
        }

        model.addAttribute("date", date);
        model.addAttribute("wait", wait);
        model.addAttribute("groupes", groupes);

        return "planning";
    }

    public static class GroupePlanningDTO {
        public int numeroGroupe;
        public List<Reservation> reservations;
        public List<Planning> plannings;

        public GroupePlanningDTO(int numeroGroupe, List<Reservation> reservations, List<Planning> plannings) {
            this.numeroGroupe = numeroGroupe;
            this.reservations = reservations;
            this.plannings = plannings;
        }
    }
}
