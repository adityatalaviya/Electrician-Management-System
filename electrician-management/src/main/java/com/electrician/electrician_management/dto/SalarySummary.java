package com.electrician.electrician_management.dto;

import lombok.Data;

@Data
public class SalarySummary {

    private Long workerId;
    private String workerName;
    private Double dailyWage;

    private Double totalWorkedDays;
    private Double totalEarnedAmount;

    private Double totalWithdrawal;
    private Double remainingSalary;


}
