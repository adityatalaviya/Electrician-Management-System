package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    List<Withdrawal> findByWorkerId(Long workerId);

    List<Withdrawal> findByWorkDetailsId(Long workId);

}
