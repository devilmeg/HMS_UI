package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.Data;

@Data
public class DoctorFilterRequest {
    private String filterDate; // yyyy-MM-dd
    private String category;   // APPOINTMENT, PATIENT, or TRAINING
    private Integer physicianId;
}
