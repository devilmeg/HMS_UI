package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import com.galgotias.hostpitalmanagementsystemfrontend.dto.PatientAppointmentDTO;
import com.galgotias.hostpitalmanagementsystemfrontend.dto.PatientPrescriptionDTO;
import com.galgotias.hostpitalmanagementsystemfrontend.dto.PatientProfileDTO;
import com.galgotias.hostpitalmanagementsystemfrontend.dto.PatientStayHistoryDTO;
import com.galgotias.hostpitalmanagementsystemfrontend.response.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/enter")
    public String enterPatientPage() {
        return "patient/enter";
    }

    @GetMapping("/dashboard")
    public String patientDashboard(@RequestParam("id") String id, Model model) { // Added "id"
        String url = backend + "/api/v1/patients/" + id;
        try {
            ApiResponse<PatientProfileDTO> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<PatientProfileDTO>>() {});

            if (response == null || response.getData() == null) {
                model.addAttribute("error", "Invalid SSN. Patient not found.");
                return "patient/enter";
            }

            model.addAttribute("patientId", id);
            return "patient/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid SSN. Patient not found.");
            return "patient/enter";
        }
    }

    @GetMapping("/history")
    public String getHistory(
            @RequestParam("id") String id, // Added "id"
            @RequestParam(value = "date", required = false) String date, // Added "date"
            @RequestParam(value = "doctor", required = false) String doctor, // Added "doctor"
            Model model) {

        String url = backend + "/api/v1/patients/" + id + "/appointments";
        ApiResponse<List<PatientAppointmentDTO>> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<List<PatientAppointmentDTO>>>() {});

        List<PatientAppointmentDTO> data = response.getData();

        if (date != null && !date.isEmpty()) {
            data = data.stream()
                    .filter(r -> r.getStartTime() != null && r.getStartTime().toString().startsWith(date))
                    .toList();
        }

        if (doctor != null && !doctor.isEmpty()) {
            data = data.stream()
                    .filter(r -> r.getPhysicianName() != null && r.getPhysicianName().toLowerCase().contains(doctor.toLowerCase()))
                    .toList();
        }

        model.addAttribute("history", data);
        model.addAttribute("patientId", id);
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedDoctor", doctor);
        return "patient/history";
    }

    @GetMapping("/profile")
    public String getProfile(@RequestParam("id") String id, Model model) { // Added "id"
        String url = backend + "/api/v1/patients/" + id;
        ApiResponse<PatientProfileDTO> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<PatientProfileDTO>>() {});

        model.addAttribute("patient", response.getData());
        model.addAttribute("patientId", id);
        return "patient/profile";
    }

    @GetMapping("/prescriptions")
    public String getPrescriptions(@RequestParam("id") String id, Model model) { // Added "id"
        String url = backend + "/api/v1/patients/" + id + "/prescriptions";
        ApiResponse<List<PatientPrescriptionDTO>> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<List<PatientPrescriptionDTO>>>() {});

        model.addAttribute("prescriptions", response.getData());
        model.addAttribute("patientId", id);
        return "patient/prescriptions";
    }

    @GetMapping("/stay-history")
    public String getStayHistory(@RequestParam("id") String id, Model model) { // Added "id"
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