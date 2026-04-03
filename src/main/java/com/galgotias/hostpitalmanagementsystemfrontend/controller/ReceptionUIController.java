package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import com.galgotias.hostpitalmanagementsystemfrontend.service.ReceptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui/reception")
public class ReceptionUIController {

    private final ReceptionService receptionService;

    public ReceptionUIController(ReceptionService receptionService) {
        this.receptionService = receptionService;
    }

    @GetMapping("/{id}/dashboard")
    public String showDashboard(
            @PathVariable("id") Integer id, // Fixed
            @RequestParam(value = "category", defaultValue = "rooms") String category, // Fixed
            @RequestParam(value = "date", required = false) String date, // Fixed
            @RequestParam(value = "sortBy", required = false) String sortBy, // Fixed
            @RequestParam(value = "order", defaultValue = "asc") String order, // Fixed
            @RequestParam(value = "page", defaultValue = "0") int page, // Fixed
            @RequestParam(value = "size", defaultValue = "5") int size, // Fixed
            Model model) {

        List<Map<String, Object>> results;

        // 1. Fetch Data
        if ("appointments".equalsIgnoreCase(category)) {
            if (date == null || date.isEmpty()) {
                results = new ArrayList<>();
                model.addAttribute("currentViewTitle", "Select Date to View Appointments");
            } else {
                results = receptionService.getAppointments(date);
                model.addAttribute("currentViewTitle", "Appointments on " + date);
            }
        } else if ("nurses".equalsIgnoreCase(category)) {
            results = receptionService.getNurses();
            model.addAttribute("currentViewTitle", "Nurses On Call");
        } else {
            results = receptionService.getRooms();
            model.addAttribute("currentViewTitle", "Room Status");
        }

        // Safety: Ensure results is never null
        if (results == null) results = new ArrayList<>();
        results = new ArrayList<>(results);

        // 2. Sorting Logic (Already well-implemented by you)
        Comparator<Map<String, Object>> comparator = null;
        if ("rooms".equalsIgnoreCase(category)) {
            comparator = Comparator.comparing(m -> Integer.parseInt(m.getOrDefault("roomNumber", "0").toString()));
        } else if ("appointments".equalsIgnoreCase(category)) {
            comparator = Comparator.comparing(m -> m.getOrDefault("patientName", "").toString().toLowerCase());
        } else if ("nurses".equalsIgnoreCase(category)) {
            comparator = Comparator.comparing(m -> m.getOrDefault("nurseName", "").toString().toLowerCase());
        }

        if (comparator != null) {
            if ("desc".equalsIgnoreCase(order)) results.sort(comparator.reversed());
            else results.sort(comparator);
        }

        // 3. Manual Pagination
        int totalItems = results.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int start = page * size;
        // Safety check for empty lists or out-of-bounds pages
        if (start >= totalItems) start = 0;
        int end = Math.min(start + size, totalItems);

        List<Map<String, Object>> paginatedList = (totalItems > 0)
                ? results.subList(start, end)
                : new ArrayList<>();

        // 4. Model Attributes
        model.addAttribute("results", paginatedList);
        model.addAttribute("receptionId", id);
        model.addAttribute("category", category);
        model.addAttribute("defaultDate", date);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        model.addAttribute("currentPage", (totalItems == 0) ? 0 : page);
        model.addAttribute("totalPages", Math.max(1, totalPages));
        model.addAttribute("size", size);

        return "reception/reception-dashboard";
    }
}