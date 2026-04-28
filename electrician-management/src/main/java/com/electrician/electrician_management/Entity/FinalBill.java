package com.electrician.electrician_management.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class FinalBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double finalAmount;

    private LocalDate billDate;

    private String remarks;

    private String billFileName;
    private String billFilePath;
    private String billFileType;

    @OneToOne
    @JoinColumn(name = "work_id", nullable = false)
    private WorkDetails workDetails;
}
