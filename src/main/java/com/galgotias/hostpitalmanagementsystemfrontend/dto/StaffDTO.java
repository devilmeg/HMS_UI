package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.Data;

@Data
public class StaffDTO {

    private String name;
    private String role;
    private String position;
    private String departmentName;

    private Boolean primaryAffiliation;   // ✅ ADD THIS
    private Boolean isDepartmentHead;     // already exists
}