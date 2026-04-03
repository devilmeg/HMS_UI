package com.galgotias.hostpitalmanagementsystemfrontend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    String status;
    String message;
    T data;
}
