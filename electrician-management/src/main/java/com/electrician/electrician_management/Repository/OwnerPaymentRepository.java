package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.OwnerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerPaymentRepository extends JpaRepository<OwnerPayment,Long> {

    List<OwnerPayment> findByWorkDetailsId(Long workId);
}
