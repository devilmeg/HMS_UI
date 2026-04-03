package com.galgotias.hostpitalmanagementsystemfrontend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private String status;
    private String message;
    private int currentPage;
    private int totalPages;
    private long totalRecords;
    private T data;
}