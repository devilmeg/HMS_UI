package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientProfileDTO {

    private Integer ssn;
    private String name;
    private String address;
    private String phone;
    private String physicianName;
}