package hu.csongor.demo.controller;

import hu.csongor.demo.dto.response.DashboardResponse;
import hu.csongor.demo.repository.*;
import hu.csongor.demo.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboard(){

        return dashboardService.getDashboard();
    }
}