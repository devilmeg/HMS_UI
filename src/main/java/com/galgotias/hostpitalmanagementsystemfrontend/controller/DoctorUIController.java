package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import com.galgotias.hostpitalmanagementsystemfrontend.dto.DoctorPatientHistoryDto;
import com.galgotias.hostpitalmanagementsystemfrontend.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui/physician")
public class DoctorUIController {
    private final DoctorService doctorService;

    public DoctorUIController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}/dashboard")
    public String showDashboard(
            @PathVariable("id") Integer id, // Fixed: Added "id"
            @RequestParam(value = "category", required = false, defaultValue = "PATIENT") String category, // Fixed: Added "category"
            @RequestParam(value = "page", defaultValue = "0") int page, // Fixed: Added "page"
            @RequestParam(value = "size", defaultValue = "2") int size, // Fixed: Added "size"
            @RequestParam(value = "date", required = false) String date, // Fixed: Added "date"
            Model model) {

        try {
            if ("APPOINTMENT".equals(category)) {
                Map<String, Object> pageData = doctorService.getAppointment(id, date, page, size);
                model.addAttribute("results", pageData.get("content"));
                model.addAttribute("pageMetadata", pageData.containsKey("page") ? pageData.get("page") : pageData);
            }
            else if ("TRAINING".equals(category)) {
                Map<String, Object> pageData = doctorService.getProcedures(id, page, size);
                model.addAttribute("results", pageData.get("content"));
                model.addAttribute("pageMetadata", pageData.containsKey("page") ? pageData.get("page") : pageData);
            } else {
                Map<String, Object> patientPage = doctorService.getPatients(id, page, size);
                model.addAttribute("results", patientPage.get("content"));
                model.addAttribute("pageMetadata", patientPage);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Backend Service Unavailable");
        }

        model.addAttribute("doctorId", id);
        model.addAttribute("category", category);
        model.addAttribute("size", size);
        return "doctor/dashboard";
    }

    @GetMapping("/patient/{ssn}/history")
    public String showPatientHistory(@PathVariable("ssn") Integer ssn, Model model) {
        List<DoctorPatientHistoryDto> history = doctorService.getDoctorPatientHistory(ssn);
        model.addAttribute("history", history);
        model.addAttribute("ssn", ssn);
        model.addAttribute("currentViewTitle", "ASTRAL_CHRONOLOGY // SECURE_ACCESS");
        return "doctor/patient-history";
    }
}