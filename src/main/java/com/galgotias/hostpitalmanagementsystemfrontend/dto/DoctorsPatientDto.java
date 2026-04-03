package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorsPatientDto {
    private Integer patientSSN;
    private String patientName;
}
