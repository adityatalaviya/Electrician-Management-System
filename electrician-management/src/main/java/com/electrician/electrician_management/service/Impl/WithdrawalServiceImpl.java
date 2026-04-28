package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Withdrawal;
import com.electrician.electrician_management.Entity.WorkDetails;
import com.electrician.electrician_management.Entity.Worker;
import com.electrician.electrician_management.Repository.ElectricianRepository;
import com.electrician.electrician_management.Repository.WithdrawalRepository;
import com.electrician.electrician_management.Repository.WorkDetailsRepository;
import com.electrician.electrician_management.Repository.WorkerRepository;
import com.electrician.electrician_management.service.WithdrawalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;
    private final WorkDetailsRepository workDetailsRepository;
    private final WorkerRepository workerRepository;

    public WithdrawalServiceImpl(WithdrawalRepository withdrawalRepository, WorkDetailsRepository workDetailsRepository, WorkerRepository workerRepository) {
        this.withdrawalRepository = withdrawalRepository;
        this.workDetailsRepository = workDetailsRepository;
        this.workerRepository = workerRepository;
    }


    @Override
    public Withdrawal addWithdrawal(Long workerId,
                                    Long workId,
                                    Double amount,
                                    LocalDate date,
                                    String remarks) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        WorkDetails work = workDetailsRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        if (amount <= 0) {
            throw new RuntimeException("Withdrawal amount must be greater than 0");
        }

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setWorker(worker);
        withdrawal.setWorkDetails(work);
        withdrawal.setAmount(amount);
        withdrawal.setWithdrawalDate(date);
        withdrawal.setRemarks(remarks);

        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getWithdrawalsByWorker(Long workerId) {
        return withdrawalRepository.findByWorkerId(workerId);
    }

    @Override
    public List<Withdrawal> getWithdrawalsByWork(Long workId) {
        return withdrawalRepository.findByWorkDetailsId(workId);
    }
}
