package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GatewayController {

    // 1. Show the ID Entry Screen
    @GetMapping("/physician-login")
    public String showLogin() {
        return "doctor/physician-gateway"; // New HTML file
    }

    // 2. Handle the ID Submission
    @PostMapping("/physician-login")
    public String processLogin(@RequestParam Integer physicianId) {
        // Redirect to your existing dashboard path
        return "redirect:/ui/physician/" + physicianId + "/dashboard?category=PATIENT";
    }
}
