package com.electrician.electrician_management.controller;


import com.electrician.electrician_management.Entity.Withdrawal;
import com.electrician.electrician_management.service.WithdrawalService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/electrician/withdrawal")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    public WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    // Electrician adds withdrawal
    @PostMapping("/add")
    public Withdrawal addWithdrawal(@RequestParam Long workerId,
                                    @RequestParam Long workId,
                                    @RequestParam Double amount,
                                    @RequestParam String date,
                                    @RequestParam(required = false) String remarks) {

        LocalDate withdrawalDate = LocalDate.parse(date);

        return withdrawalService.addWithdrawal(
                workerId,
                workId,
                amount,
                withdrawalDate,
                remarks
        );
    }

    // Worker sees his/her withdrawals
    @GetMapping("/worker/{workerId}")
    public List<Withdrawal> getWorkerWithdrawals(@PathVariable Long workerId) {
        return withdrawalService.getWithdrawalsByWorker(workerId);
    }

    // Site-wise withdrawals
    @GetMapping("/work/{workId}")
    public List<Withdrawal> getWorkWithdrawals(@PathVariable Long workId) {
        return withdrawalService.getWithdrawalsByWork(workId);
    }


}
