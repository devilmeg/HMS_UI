package com.galgotias.hostpitalmanagementsystemfrontend.service;

import com.galgotias.hostpitalmanagementsystemfrontend.dto.DoctorAppointmentDto;
import com.galgotias.hostpitalmanagementsystemfrontend.dto.DoctorPatientHistoryDto;
import com.galgotias.hostpitalmanagementsystemfrontend.dto.DoctorProcedureDto;
import com.galgotias.hostpitalmanagementsystemfrontend.dto.DoctorsPatientDto;
import com.galgotias.hostpitalmanagementsystemfrontend.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {
   @Autowired
    private RestClient restClient;

    @Value("${hms.backend.url}")
    String backend;

    public Map<String, Object> getPatients(Integer id, Integer page, Integer size) {
        String url = backend + "/api/v1/doctors/" + id + "/patients?page=" + page + "&size=" + size;

        String rawJson = restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);

        System.out.println("DEBUG - RAW BACKEND JSON: " + rawJson);

        ApiResponse<Map<String, Object>> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<Map<String, Object>>>() {});
        return response.getData();
    }

    public  Map<String , Object> getAppointment(Integer  id , String date, Integer page, Integer size) {
        String url = backend + "/api/v1/doctors/" + id + "/appointments/today?page=" + page + "&size=" + size;
        ApiResponse<Map<String, Object>> result = restClient.get()
                .uri(url)
                .retrieve()
                .body(
                        new ParameterizedTypeReference<ApiResponse<Map<String , Object>>>() {}
                );

        return (result != null && result.getData() != null)
                ? result.getData()
                : Collections.emptyMap();
    }


    public Map<String, Object> getProcedures(Integer id, Integer page, Integer size) {
        // Force size=2 here to match your Backend test
        String url = backend + "/api/v1/doctors/" + id + "/procedures/trained?page=" + page + "&size=" + size;

        System.out.println("FRONTEND CALLING URL: " + url); // Debug: Check this in your console!

        ApiResponse<Map<String, Object>> result = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<Map<String, Object>>>() {});

        return (result != null && result.getData() != null) ? result.getData() : Collections.emptyMap();
    }

    public List<DoctorPatientHistoryDto> getDoctorPatientHistory(Integer ssn) {
        String url = backend + "/api/v1/doctors/patients/" + ssn + "/history";

        ApiResponse<List<DoctorPatientHistoryDto>> result = restClient.get()
                .uri(url)
                .retrieve()
                .body(
                        new ParameterizedTypeReference<ApiResponse<List<DoctorPatientHistoryDto>>>() {
                        }
                );
        return  (result != null && result.getData()!=null)
                ? result.getData()
                : Collections.emptyList();
    }

}