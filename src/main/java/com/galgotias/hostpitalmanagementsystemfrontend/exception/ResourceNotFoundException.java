package com.galgotias.hostpitalmanagementsystemfrontend.exception;


public class ResourceNotFoundException extends RuntimeException {
    public java.util.Map<String, Object> errorData;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
