package com.electrician.electrician_management.controller;


import com.electrician.electrician_management.Entity.OwnerPayment;
import com.electrician.electrician_management.service.OwnerPaymentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/electrician/owner-payment")
public class OwnerPaymentController {

    private final OwnerPaymentService ownerPaymentService;

    public OwnerPaymentController(OwnerPaymentService ownerPaymentService) {
        this.ownerPaymentService = ownerPaymentService;
    }

    @PostMapping("/add")
    public OwnerPayment addPayment(@RequestParam Long workId,
                                   @RequestParam Double amount,
                                   @RequestParam String date,
                                   @RequestParam String mode,
                                   @RequestParam(required = false) String remarks) {

        LocalDate paymentDate = LocalDate.parse(date);

        return ownerPaymentService.addPayment(
                workId,
                amount,
                paymentDate,
                mode.toUpperCase(),
                remarks
        );
    }

    @GetMapping("/work/{workId}")
    public List<OwnerPayment> getPayments(@PathVariable Long workId) {
        return ownerPaymentService.getPaymentsByWork(workId);
    }
}
