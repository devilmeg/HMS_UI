package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import com.galgotias.hostpitalmanagementsystemfrontend.dto.DoctorPatientHistoryDto;
import com.galgotias.hostpitalmanagementsystemfrontend.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestParam(required = false, defaultValue = "PATIENT") String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size,
            @RequestParam(required = false) String date,
            Model model) {

        if ("APPOINTMENT".equals(category)) {
            Map<String, Object> pageData = doctorService.getAppointment(id, date, page, size);
            // FLATTEN: If the data is inside 'content', pull it out
            model.addAttribute("results", pageData.get("content"));
            // Handle the nested metadata
            model.addAttribute("pageMetadata", pageData.containsKey("page") ? pageData.get("page") : pageData);
        }
        else if ("TRAINING".equals(category)) {
            Map<String, Object> pageData = doctorService.getProcedures(id, page, size);
            model.addAttribute("results", pageData.get("content"));
            model.addAttribute("pageMetadata", pageData.containsKey("page") ? pageData.get("page") : pageData);
        } else {
            // PATIENT (Already working)
            Map<String, Object> patientPage = doctorService.getPatients(id, page, size);
            model.addAttribute("results", patientPage.get("content"));
            model.addAttribute("pageMetadata", patientPage);
        }


        model.addAttribute("doctorId", id);
        model.addAttribute("category", category);
        return "doctor/dashboard";
    }

    @GetMapping("/patient/{ssn}/history")
    public String showPatientHistory(@PathVariable("ssn") Integer ssn, Model model) {
        // Fetch the unified timeline (Appointments + Procedures)
        List<DoctorPatientHistoryDto> history = doctorService.getDoctorPatientHistory(ssn);

        model.addAttribute("history", history);
        model.addAttribute("ssn", ssn);
        model.addAttribute("currentViewTitle", "ASTRAL_CHRONOLOGY // SECURE_ACCESS");

        return "doctor/patient-history"; // Your new Astral History HTML file
    }

}