package com.electrician.electrician_management.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;        // original file name
    private String filePath;        // stored path
    private String fileType;        // PDF / IMAGE
    private LocalDate createdDate;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private WorkDetails workDetails;
}
