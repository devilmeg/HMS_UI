package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import com.galgotias.hostpitalmanagementsystemfrontend.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui/physician")
public class DoctorUIController {
    private final DoctorService doctorService;

    public DoctorUIController(DoctorService doctorServiceService) {
        this.doctorService = doctorServiceService;
    }

    @GetMapping("/{id}/dashboard")
    public String showDashboard(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = "APPOINTMENT") String category,
            @RequestParam(required = false) String date,
            Model model) {

        model.addAttribute("results", doctorService.getPatients(id));
        model.addAttribute("currentViewTitle", "My Assigned Patients");

        model.addAttribute("doctorId", id);
        model.addAttribute("category", category);
        model.addAttribute("defaultDate", date);
        return "doctor/dashboard";
    }
}