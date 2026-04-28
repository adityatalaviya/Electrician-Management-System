package com.electrician.electrician_management.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WorkDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerName;
    private String location;
    private String category;   // SITE / BUNGLOW / OFFICE / FACTORY
    private String status;     // ONGOING / COMPLETED

    @ManyToOne
    @JoinColumn(name = "electrician_id")
    private Electrician electrician;
}
