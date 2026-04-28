package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.dto.SalarySummary;
import com.electrician.electrician_management.service.SalaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/electrician/salary")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/worker/{workerId}")
    public SalarySummary getSalary(@PathVariable Long workerId) {
        return salaryService.calculateSalary(workerId);
    }
}
