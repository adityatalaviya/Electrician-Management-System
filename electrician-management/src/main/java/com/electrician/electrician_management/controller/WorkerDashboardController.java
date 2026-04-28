package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.Attendance;
import com.electrician.electrician_management.Entity.Withdrawal;
import com.electrician.electrician_management.dto.SalarySummary;
import com.electrician.electrician_management.service.WorkerDashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worker/dashboard")
public class WorkerDashboardController {

    private final WorkerDashboardService service;

    public WorkerDashboardController(WorkerDashboardService service) {
        this.service = service;
    }

    // ✅ Attendance
    @GetMapping("/attendance")
    public List<Attendance> getAttendance(Authentication auth) {

        Long workerId = (Long) auth.getPrincipal();

        return service.getAttendance(workerId);
    }

    // ✅ Salary
    @GetMapping("/salary")
    public SalarySummary getSalary(Authentication auth) {

        Long workerId = (Long) auth.getPrincipal();

        return service.getSalary(workerId);
    }

    // ✅ Withdrawals
    @GetMapping("/withdraw")
    public List<Withdrawal> getWithdrawals(Authentication auth) {

        Long workerId = (Long) auth.getPrincipal();

        return service.getWithdrawals(workerId);
    }
}