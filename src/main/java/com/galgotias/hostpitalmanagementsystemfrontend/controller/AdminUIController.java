package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import com.galgotias.hostpitalmanagementsystemfrontend.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui/admin")
public class AdminUIController {

    private final AdminService adminService;

    public AdminUIController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/revenue")
    public String revenue(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size,
                          Model model) {

        var response = adminService.getRevenue(page, size);

        if (response == null || response.getData() == null) {
            model.addAttribute("error", "Unable to fetch revenue data");
        }

        model.addAttribute("response", response);
        model.addAttribute("baseUrl", "/ui/admin/revenue");

        return "admin/revenue";
    }
    @GetMapping("/status")
    public String status() {
        return "redirect:/ui/admin/dashboard";
    }
    @GetMapping("/staff")
    public String staff(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "6") int size,
                        Model model) {

        var response = adminService.getStaff(page, size);

        if (response == null || response.getData() == null) {
            model.addAttribute("error", "Unable to fetch staff data");
        }

        model.addAttribute("response", response);
        model.addAttribute("baseUrl", "/ui/admin/staff");

        return "admin/staff";
    }

    @GetMapping("/logs")
    public String logs(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String level,
                       Model model) {

        var response = adminService.getLogs(page, size, level);

        if (response == null || response.getData() == null) {
            model.addAttribute("error", "Unable to fetch logs");
        }

        model.addAttribute("response", response);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("baseUrl", "/ui/admin/logs");

        return "admin/logs";
    }
}