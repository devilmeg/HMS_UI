package com.galgotias.hostpitalmanagementsystemfrontend.service;

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

@Service
public class DoctorService {
   @Autowired
    private RestClient restClient;

    @Value("${hms.backend.url}")
    String backend;

   public List<DoctorsPatientDto> getPatients(Integer id) {
       String url = backend + "/api/v1/doctors/" + id + "/patients";
       ApiResponse<List<DoctorsPatientDto>> response = restClient.get()
               .uri(url)
               .retrieve()
               .body(new ParameterizedTypeReference<ApiResponse<List<DoctorsPatientDto>>>() {});

       return response != null ? response.getData() : Collections.emptyList();
   }

}