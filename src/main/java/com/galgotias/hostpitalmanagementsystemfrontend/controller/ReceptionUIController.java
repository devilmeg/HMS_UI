package com.galgotias.hostpitalmanagementsystemfrontend.controller;

import com.galgotias.hostpitalmanagementsystemfrontend.service.ReceptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/ui/reception")
public class ReceptionUIController {

    private final ReceptionService receptionService;

    public ReceptionUIController(ReceptionService receptionService) {
        this.receptionService = receptionService;
    }

    @GetMapping("/{id}/dashboard")
    public String showDashboard(
//            @PathVariable Integer id,
            @PathVariable("id") Integer id,@RequestParam(name = "category", defaultValue = "rooms") String category,
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order,
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

        // SORTING LOGIC
//        if (sortBy != null && !sortBy.isEmpty()) {
//            results.sort((a, b) -> {
//                Object valA = a.get(sortBy);
//                Object valB = b.get(sortBy);
//
//                if (valA == null || valB == null) return 0;
//
//                int cmp = valA.toString().compareToIgnoreCase(valB.toString());
//                return "desc".equalsIgnoreCase(order) ? -cmp : cmp;
//            });
//        }

        // SORTING LOGIC (FIXED)
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
                    m -> m.get("nurseName").toString().toLowerCase()   // ✅ FIX HERE
            );
        }

// APPLY ASC / DESC
        if (comparator != null) {
            if ("desc".equalsIgnoreCase(order)) {
                results.sort(comparator.reversed());
            } else {
                results.sort(comparator);
            }
        }

        model.addAttribute("results", results);
        model.addAttribute("receptionId", id);
        model.addAttribute("category", category);
        model.addAttribute("defaultDate", date);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);

        return "reception/reception-dashboard";
    }
}