package com.electrician.electrician_management.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class OwnerPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDate paymentDate;

    private String paymentMode;   // CASH / UPI / BANK / CHEQUE

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "work_id", nullable = false)
    private WorkDetails workDetails;
}
