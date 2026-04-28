package com.electrician.electrician_management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private double dailyWage;

    @ManyToOne
    @JoinColumn(name = "electrician_id")
    private Electrician electrician;
}
