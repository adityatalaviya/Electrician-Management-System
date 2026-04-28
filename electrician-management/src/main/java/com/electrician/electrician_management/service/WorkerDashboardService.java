package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.Attendance;
import com.electrician.electrician_management.Entity.Withdrawal;
import com.electrician.electrician_management.dto.SalarySummary;

import java.util.List;

public interface WorkerDashboardService {

    List<Attendance> getAttendance(Long workerId);

    SalarySummary getSalary(Long workerId);

    List<Withdrawal> getWithdrawals(Long workerId);

}
