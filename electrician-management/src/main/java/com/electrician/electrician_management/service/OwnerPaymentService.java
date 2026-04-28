package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.OwnerPayment;

import java.time.LocalDate;
import java.util.List;

public interface OwnerPaymentService {

    OwnerPayment addPayment(Long workId,
                            Double amount,
                            LocalDate date,
                            String mode,
                            String remarks);

    List<OwnerPayment> getPaymentsByWork(Long workId);
}
