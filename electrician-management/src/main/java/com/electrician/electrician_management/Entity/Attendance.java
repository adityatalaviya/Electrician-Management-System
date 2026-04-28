package com.electrician.electrician_management.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate attendanceDate;

    private String status;   // PRESENT / ABSENT

    @Enumerated(EnumType.STRING)
    private AttendanceSession session;


    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private WorkDetails workDetails;
}
