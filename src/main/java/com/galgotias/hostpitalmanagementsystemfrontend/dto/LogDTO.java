package com.galgotias.hostpitalmanagementsystemfrontend.dto;

import lombok.Data;

@Data
public class LogDTO {
    private String time;
    private String level;
    private String message;
}