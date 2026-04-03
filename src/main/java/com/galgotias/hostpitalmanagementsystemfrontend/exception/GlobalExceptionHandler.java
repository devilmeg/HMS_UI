package com.galgotias.hostpitalmanagementsystemfrontend.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Catch 404 Errors (Resource Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Data Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorStatus", "404");
        return "ErrorUI";
    }

    // Catch 500 Errors (Backend API Crash)
    @ExceptionHandler({HttpServerErrorException.class, HttpClientErrorException.class})
    public String handleBackendError(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Service Unavailable");
        model.addAttribute("errorMessage", "The Hospital API is currently not responding. Please try again later.");
        model.addAttribute("errorStatus", "503");
        return "ErrorUI";
    }

    // Catch Template/Logic Errors (The "Whitelabel" Killer)
    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex, Model model) {
        ex.printStackTrace(); // Still print to console for YOU to debug
        model.addAttribute("errorTitle", "Internal System Error");
        model.addAttribute("errorMessage", "Something went wrong while rendering the dashboard.");
        model.addAttribute("errorStatus", "500");
        return "ErrorUI";
    }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public void handleFavicon() { } // Stops the fake errors from triggering the handler
}