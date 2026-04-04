package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GatewayController {

    /**
     * Displays the Physician login/gateway screen.
     */
    @GetMapping("/physician-login")
    public String showLogin() {
        return "doctor/physician-gateway";
    }

    /**
     * Processes the login ID and redirects to the specific physician's dashboard.
     * Fixed: Added explicit name "physicianId" to @RequestParam.
     */
    @PostMapping("/physician-login")
    public String processLogin(@RequestParam("physicianId") Integer physicianId,
                               RedirectAttributes redirectAttributes) {
        if (physicianId == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid ID");
            return "redirect:/physician-login";
        }
        return "redirect:/ui/physician/" + physicianId + "/dashboard?category=PATIENT";
    }
}