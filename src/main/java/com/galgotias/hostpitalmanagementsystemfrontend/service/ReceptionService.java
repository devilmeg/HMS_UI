package com.galgotias.hostpitalmanagementsystemfrontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReceptionService {

    private final RestClient restClient;

    @Value("${hms.backend.url}")
    private String backendUrl; // Renamed for clarity

    public ReceptionService(RestClient restClient) {
        this.restClient = restClient;
    }

    // ROOMS
    public List<Map<String, Object>> getRooms() {
        try {
            // Ensure the /api/v1/ prefix is present to avoid 404/500 errors
            String url = backendUrl + "/api/v1/rooms/status";

            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});

            if (response != null && response.get("data") != null) {
                return (List<Map<String, Object>>) response.get("data");
            }

        } catch (Exception e) {
            System.err.println("Rooms API error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // NURSES
    public List<Map<String, Object>> getNurses() {
        try {
            String url = backendUrl + "/api/v1/nurses/on-call";

            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});

            if (response != null && response.get("data") != null) {
                return (List<Map<String, Object>>) response.get("data");
            }

        } catch (Exception e) {
            System.err.println("Nurses API error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // APPOINTMENTS
    public List<Map<String, Object>> getAppointments(String date) {
        try {
            String url = backendUrl + "/api/v1/appointments/date/" + date;

            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});

            if (response != null && response.get("data") != null) {
                return (List<Map<String, Object>>) response.get("data");
            }

        } catch (Exception e) {
            System.err.println("Appointments API error: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}