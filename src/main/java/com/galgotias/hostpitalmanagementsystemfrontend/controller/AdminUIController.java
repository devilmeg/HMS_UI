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

    // FIX 1: Add this to handle the "Administration" card link from index.html
    @GetMapping("/status")
    public String adminStatusEntry() {
        return "admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/revenue")
    public String revenue(
            @RequestParam(value = "page", defaultValue = "0") int page, // FIX 2: Explicit name
            @RequestParam(value = "size", defaultValue = "5") int size, // FIX 2: Explicit name
            Model model) {

        var response = adminService.getRevenue(page, size);
        model.addAttribute("response", response);
        model.addAttribute("size", size);
        model.addAttribute("baseUrl", "/ui/admin/revenue");
        return "admin/revenue";
    }

    @GetMapping("/staff")
    public String staff(
            @RequestParam(value = "page", defaultValue = "0") int page, // FIX 2: Explicit name
            @RequestParam(value = "size", defaultValue = "6") int size, // FIX 2: Explicit name
            Model model) {

        var response = adminService.getStaff(page, size);
        model.addAttribute("response", response);
        model.addAttribute("size", size);
        model.addAttribute("baseUrl", "/ui/admin/staff");
        return "admin/staff";
    }

    @GetMapping("/logs")
    public String logs(
            @RequestParam(value = "page", defaultValue = "0") int page, // FIX 2: Explicit name
            @RequestParam(value = "size", defaultValue = "10") int size, // FIX 2: Explicit name
            @RequestParam(value = "level", required = false) String level, // FIX 2: Explicit name
            Model model) {

        var response = adminService.getLogs(page, size, level);
        model.addAttribute("response", response);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("size", size);
        model.addAttribute("baseUrl", "/ui/admin/logs");
        return "admin/logs";
    }
}