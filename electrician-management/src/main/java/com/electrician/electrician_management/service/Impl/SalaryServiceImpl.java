package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.*;
import com.electrician.electrician_management.Repository.AttendanceRepository;
import com.electrician.electrician_management.Repository.WithdrawalRepository;
import com.electrician.electrician_management.Repository.WorkerRepository;
import com.electrician.electrician_management.dto.SalarySummary;
import com.electrician.electrician_management.service.SalaryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final AttendanceRepository attendanceRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final WorkerRepository workerRepository;

    public SalaryServiceImpl(AttendanceRepository attendanceRepository,
                             WithdrawalRepository withdrawalRepository,
                             WorkerRepository workerRepository) {
        this.attendanceRepository = attendanceRepository;
        this.withdrawalRepository = withdrawalRepository;
        this.workerRepository = workerRepository;
    }

    @Override
    public SalarySummary calculateSalary(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        List<Attendance> attendanceList =
                attendanceRepository.findByWorkerId(workerId);

        double workedDays = 0;

        for (Attendance attendance : attendanceList) {
            switch (attendance.getSession()) {
                case FULL_DAY -> workedDays += 1;
                case MORNING, AFTERNOON -> workedDays += 0.5;
            }
        }

        double totalEarned = workedDays * worker.getDailyWage();

        List<Withdrawal> withdrawals =
                withdrawalRepository.findByWorkerId(workerId);

        double totalWithdrawal =
                withdrawals.stream()
                        .mapToDouble(Withdrawal::getAmount)
                        .sum();

        double remainingSalary = totalEarned - totalWithdrawal;

        SalarySummary summary = new SalarySummary();
        summary.setWorkerId(worker.getId());
        summary.setWorkerName(worker.getName());
        summary.setDailyWage(worker.getDailyWage());
        summary.setTotalWorkedDays(workedDays);
        summary.setTotalEarnedAmount(totalEarned);
        summary.setTotalWithdrawal(totalWithdrawal);
        summary.setRemainingSalary(remainingSalary);

        return summary;
    }
}
