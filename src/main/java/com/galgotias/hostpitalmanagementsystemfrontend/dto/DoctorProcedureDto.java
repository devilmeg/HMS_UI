package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProcedureDto {
    private Integer id;
    private String procedureName;
    private String name;
    private Double cost;
    private String status;
}
