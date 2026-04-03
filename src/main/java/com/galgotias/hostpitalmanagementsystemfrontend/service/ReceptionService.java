package com.galgotias.hostpitalmanagementsystemfrontend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReceptionService {

    private final RestClient restClient;
    private final String BASE_URL = "http://localhost:8080/api/v1";

    public ReceptionService(RestClient restClient) {
        this.restClient = restClient;
    }

    // ROOMS
    public List<Map<String, Object>> getRooms() {
        try {
            Map response = restClient.get()
                    .uri(BASE_URL + "/rooms/status")
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.get("data") != null) {
                return (List<Map<String, Object>>) response.get("data");
            }

        } catch (Exception e) {
            System.out.println("Rooms API error: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // NURSES
    public List<Map<String, Object>> getNurses() {
        try {
            Map response = restClient.get()
                    .uri(BASE_URL + "/nurses/on-call")
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.get("data") != null) {
                return (List<Map<String, Object>>) response.get("data");
            }

        } catch (Exception e) {
            System.out.println("Nurses API error: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // APPOINTMENTS
    public List<Map<String, Object>> getAppointments(String date) {
        try {
            Map response = restClient.get()
                    .uri(BASE_URL + "/appointments/date/" + date)
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.get("data") != null) {
                return (List<Map<String, Object>>) response.get("data");
            }

        } catch (Exception e) {
            System.out.println("Appointments API error: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}