package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientAppointmentDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String physicianName;
    private String status;


}