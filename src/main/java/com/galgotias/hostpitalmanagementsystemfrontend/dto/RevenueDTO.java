package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.Data;

@Data
public class RevenueDTO {
    private String source;
    private String identifier;
    private Long count;
    private Double revenue;
    private String category;
    private Double unitCost;   // Added to match backend enrichment
    private Double totalDays;  // Added for Room Stay records
}