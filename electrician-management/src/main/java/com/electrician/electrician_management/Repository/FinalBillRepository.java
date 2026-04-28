package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.FinalBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinalBillRepository extends JpaRepository<FinalBill, Long> {

    Optional<FinalBill> findByWorkDetailsId(Long workId);
}
