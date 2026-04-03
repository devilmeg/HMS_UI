package com.galgotias.hostpitalmanagementsystemfrontend.controller;


import com.galgotias.hostpitalmanagementsystemfrontend.dto.*;
import com.galgotias.hostpitalmanagementsystemfrontend.response.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@Controller
@RequestMapping("/ui/patient")
public class PatientUIController {

    private final RestClient restClient;

    @Value("${hms.backend.url}")
    private String backend;

    public PatientUIController(RestClient restClient) {
        this.restClient = restClient;
    }

    // ===============================
    // ENTER SSN PAGE
    // ===============================
    @GetMapping("/enter")
    public String enterPatientPage() {
        return "patient/enter";
    }

    // ===============================
    // DASHBOARD (AFTER ENTERING SSN)
    // ===============================
    @GetMapping("/dashboard")
    public String patientDashboard(@RequestParam String id, Model model) {

        String url = backend + "/api/v1/patients/" + id;

        try {
            ApiResponse<PatientProfileDTO> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<PatientProfileDTO>>() {});

            // ❌ invalid SSN case
            if (response == null || response.getData() == null) {
                model.addAttribute("error", "Invalid SSN. Patient not found.");
                return "patient/enter";
            }

            // ✅ valid SSN
            model.addAttribute("patientId", id);
            return "patient/dashboard";

        } catch (Exception e) {
            model.addAttribute("error", "Invalid SSN. Patient not found.");
            return "patient/enter";
        }
    }

    // ===============================
    // APPOINTMENTS / HISTORY
    // ===============================
    @GetMapping("/history")
    public String getHistory(
            @RequestParam String id,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String doctor,
            Model model) {

        String url = backend + "/api/v1/patients/" + id + "/appointments";

        ApiResponse<List<PatientAppointmentDTO>> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<List<PatientAppointmentDTO>>>() {});

        List<PatientAppointmentDTO> data = response.getData();

        // ✅ FILTER BY DATE
        if (date != null && !date.isEmpty()) {
            data = data.stream()
                    .filter(r -> r.getStartTime() != null &&
                            r.getStartTime().toString().startsWith(date))
                    .toList();
        }

        // ✅ FILTER BY DOCTOR
        if (doctor != null && !doctor.isEmpty()) {
            data = data.stream()
                    .filter(r -> r.getPhysicianName() != null &&
                            r.getPhysicianName().toLowerCase().contains(doctor.toLowerCase()))
                    .toList();
        }

        model.addAttribute("history", data);
        model.addAttribute("patientId", id);
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedDoctor", doctor);

        return "patient/history";
    }
    // ===============================
    // PROFILE
    // ===============================
    @GetMapping("/profile")
    public String getProfile(@RequestParam String id, Model model) {

        String url = backend + "/api/v1/patients/" + id;

        ApiResponse<PatientProfileDTO> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<PatientProfileDTO>>() {});

        model.addAttribute("patient", response.getData());
        model.addAttribute("patientId", id);

        return "patient/profile";
    }

    // ===============================
    // PRESCRIPTIONS
    // ===============================
    @GetMapping("/prescriptions")
    public String getPrescriptions(@RequestParam String id, Model model) {

        String url = backend + "/api/v1/patients/" + id + "/prescriptions";

        ApiResponse<List<PatientPrescriptionDTO>> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<List<PatientPrescriptionDTO>>>() {});

        model.addAttribute("prescriptions", response.getData());
        model.addAttribute("patientId", id);

        return "patient/prescriptions";
    }

    // ===============================
    // STAY HISTORY
    // ===============================
    @GetMapping("/stay-history")
    public String getStayHistory(@RequestParam String id, Model model) {

        String url = backend + "/api/v1/patients/" + id + "/stay-history";

        ApiResponse<List<PatientStayHistoryDTO>> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<List<PatientStayHistoryDTO>>>() {});

        model.addAttribute("stayHistory", response.getData());
        model.addAttribute("patientId", id);

        return "patient/stay-history";
    }

}
