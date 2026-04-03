package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorPatientHistoryDto {
    private String date;
    private String eventType;
    private String description;
    private String physician;
    private String results;
}
