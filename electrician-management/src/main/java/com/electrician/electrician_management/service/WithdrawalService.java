package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.Withdrawal;

import java.time.LocalDate;
import java.util.List;

public interface WithdrawalService {

    Withdrawal addWithdrawal(Long workerId,
                             Long workId,
                             Double amount,
                             LocalDate date,
                             String remarks);

    List<Withdrawal> getWithdrawalsByWorker(Long workerId);

    List<Withdrawal> getWithdrawalsByWork(Long workId);
}
