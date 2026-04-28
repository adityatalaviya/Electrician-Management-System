package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.FinalBill;
import com.electrician.electrician_management.Entity.OwnerPayment;
import com.electrician.electrician_management.Entity.WorkDetails;
import com.electrician.electrician_management.Repository.FinalBillRepository;
import com.electrician.electrician_management.Repository.OwnerPaymentRepository;
import com.electrician.electrician_management.Repository.WorkDetailsRepository;
import com.electrician.electrician_management.service.OwnerPaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OwnerPaymentServiceImpl implements OwnerPaymentService {

    private final OwnerPaymentRepository ownerPaymentRepository;
    private final WorkDetailsRepository workRepository;
    private final FinalBillRepository finalBillRepository;

    public OwnerPaymentServiceImpl(OwnerPaymentRepository ownerPaymentRepository,
                                   WorkDetailsRepository workRepository,
                                   FinalBillRepository finalBillRepository) {

        this.ownerPaymentRepository = ownerPaymentRepository;
        this.workRepository = workRepository;
        this.finalBillRepository = finalBillRepository;
    }

    @Override
    public OwnerPayment addPayment(Long workId,
                                   Double amount,
                                   LocalDate date,
                                   String mode,
                                   String remarks) {

        WorkDetails work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        if (amount == null || amount <= 0)
            throw new RuntimeException("Amount must be greater than 0");

        FinalBill bill = finalBillRepository.findByWorkDetailsId(workId).orElse(null);

        if (bill != null) {

            List<OwnerPayment> payments =
                    ownerPaymentRepository.findByWorkDetailsId(workId);

            double paid = payments.stream()
                    .mapToDouble(OwnerPayment::getAmount)
                    .sum();

            if (paid + amount > bill.getFinalAmount()) {
                throw new RuntimeException("Payment exceeds final bill");
            }
        }

        OwnerPayment payment = new OwnerPayment();
        payment.setWorkDetails(work);
        payment.setAmount(amount);
        payment.setPaymentDate(date);
        payment.setPaymentMode(mode);
        payment.setRemarks(remarks);

        return ownerPaymentRepository.save(payment);
    }

    @Override
    public List<OwnerPayment> getPaymentsByWork(Long workId) {
        return ownerPaymentRepository.findByWorkDetailsId(workId);
    }
}