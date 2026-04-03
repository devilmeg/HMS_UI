package com.galgotias.hostpitalmanagementsystemfrontend.service;

import com.galgotias.hostpitalmanagementsystemfrontend.dto.LogDTO;
import com.galgotias.hostpitalmanagementsystemfrontend.dto.RevenueDTO;
import com.galgotias.hostpitalmanagementsystemfrontend.dto.StaffDTO;
import com.galgotias.hostpitalmanagementsystemfrontend.response.PaginatedResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AdminService {

    private final RestClient restClient;

    @Value("${hms.backend.url}")
    private String backend;

    public AdminService(RestClient restClient) {
        this.restClient = restClient;
    }

    // REVENUE
    public PaginatedResponse<List<RevenueDTO>> getRevenue(int page, int size) {
        try {
            String url = backend + "/api/v1/admin/reports/revenue?page=" + page + "&size=" + size;

            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<PaginatedResponse<List<RevenueDTO>>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return empty("Revenue API Failed");
        }
    }

    // STAFF
    public PaginatedResponse<List<StaffDTO>> getStaff(int page, int size) {
        try {
            String url = backend + "/api/v1/admin/staff/all?page=" + page + "&size=" + size;

            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<PaginatedResponse<List<StaffDTO>>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return empty("Staff API Failed");
        }
    }

    // LOGS
    public PaginatedResponse<List<LogDTO>> getLogs(int page, int size, String level) {
        try {
            String url = backend + "/api/v1/admin/logs?page=" + page + "&size=" + size;

            if (level != null && !level.isEmpty()) {
                url += "&level=" + level;
            }

            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<PaginatedResponse<List<LogDTO>>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return empty("Logs API Failed");
        }
    }

    // COMMON EMPTY RESPONSE
    private <T> PaginatedResponse<T> empty(String msg) {
        return PaginatedResponse.<T>builder()
                .status("FAILED")
                .message(msg)
                .currentPage(0)
                .totalPages(0)
                .totalRecords(0)
                .data(null)
                .build();
    }
}