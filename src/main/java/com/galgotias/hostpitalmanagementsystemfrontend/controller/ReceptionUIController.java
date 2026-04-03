

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
            @PathVariable Integer id,
            @RequestParam(defaultValue = "rooms") String category,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        List<Map<String, Object>> results;

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

        results = new ArrayList<>(results);

        // SORTING
        Comparator<Map<String, Object>> comparator = null;

        if ("rooms".equalsIgnoreCase(category)) {
            comparator = Comparator.comparing(
                    m -> Integer.parseInt(m.get("roomNumber").toString())
            );
        } else if ("appointments".equalsIgnoreCase(category)) {
            comparator = Comparator.comparing(
                    m -> m.get("patientName").toString().toLowerCase()
            );
        } else if ("nurses".equalsIgnoreCase(category)) {
            comparator = Comparator.comparing(
                    m -> m.get("nurseName").toString().toLowerCase()
            );
        }

        if (comparator != null) {
            if ("desc".equalsIgnoreCase(order)) {
                results.sort(comparator.reversed());
            } else {
                results.sort(comparator);
            }
        }

        // Paging
        int totalItems = results.size();

        int start = page * size;
        int end = Math.min(start + size, totalItems);

        // SAFETY FIX
        if (start >= totalItems) {
            start = 0;
            end = Math.min(size, totalItems);
        }

        List<Map<String, Object>> paginatedList =
                new ArrayList<>(results.subList(start, end));


        model.addAttribute("results", paginatedList);

        model.addAttribute("receptionId", id);
        model.addAttribute("category", category);
        model.addAttribute("defaultDate", date);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalItems / size));
        model.addAttribute("size", size);

        return "reception/reception-dashboard";
    }
}