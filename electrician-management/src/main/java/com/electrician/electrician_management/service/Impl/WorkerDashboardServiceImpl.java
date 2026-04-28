package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Attendance;
import com.electrician.electrician_management.Entity.Withdrawal;
import com.electrician.electrician_management.Repository.AttendanceRepository;
import com.electrician.electrician_management.Repository.WithdrawalRepository;
import com.electrician.electrician_management.dto.SalarySummary;
import com.electrician.electrician_management.service.WorkerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerDashboardServiceImpl implements WorkerDashboardService {

    private final AttendanceRepository attendanceRepo;
    private final WithdrawalRepository withdrawalRepo;

    @Override
    public List<Attendance> getAttendance(Long workerId) {
        return attendanceRepo.findByWorkerId(workerId);
    }

    @Override
    public SalarySummary getSalary(Long workerId) {

        var attendanceList = attendanceRepo.findByWorkerId(workerId);

        long workedDays = attendanceList.stream()
                .filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus()))
                .count();

        double dailyWage = 0;

        if (!attendanceList.isEmpty()) {
            dailyWage = attendanceList.get(0).getWorker().getDailyWage();
        }

        double totalEarned = workedDays * dailyWage;

        double totalWithdraw = withdrawalRepo.findByWorkerId(workerId)
                .stream()
                .mapToDouble(w -> w.getAmount())
                .sum();

        double remaining = totalEarned - totalWithdraw;

        SalarySummary dto = new SalarySummary();
        dto.setDailyWage(dailyWage);
        dto.setTotalWorkedDays((double) workedDays);
        dto.setTotalEarnedAmount(totalEarned);
        dto.setTotalWithdrawal(totalWithdraw);
        dto.setRemainingSalary(remaining);

        return dto;
    }

    @Override
    public List<Withdrawal> getWithdrawals(Long workerId) {
        return withdrawalRepo.findByWorkerId(workerId);
    }

}
