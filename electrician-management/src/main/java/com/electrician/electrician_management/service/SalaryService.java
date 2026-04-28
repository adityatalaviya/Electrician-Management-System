package com.electrician.electrician_management.service;

import com.electrician.electrician_management.dto.SalarySummary;

public interface SalaryService {

    SalarySummary calculateSalary(Long workerId);
}
