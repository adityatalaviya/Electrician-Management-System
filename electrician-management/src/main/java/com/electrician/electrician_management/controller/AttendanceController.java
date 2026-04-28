package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.Attendance;
import com.electrician.electrician_management.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/electrician/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    public Attendance markAttendance(@RequestParam Long workerId,
                                     @RequestParam Long workId,
                                     @RequestParam String date,
                                     @RequestParam String status,
                                     @RequestParam(required = true) String session) {

        if (session == null || session.isBlank()) {
            throw new RuntimeException("Session is required (MORNING / AFTERNOON / FULL_DAY)");
        }

        LocalDate attendanceDate = LocalDate.parse(date);
        return attendanceService.markAttendance(workerId, workId, attendanceDate, status.toUpperCase(),session.toUpperCase());
    }

    @GetMapping("/worker/{workerId}")
    public List<Attendance> getByWorker(@PathVariable Long workerId) {
        return attendanceService.getAttendanceByWorker(workerId);
    }

    @GetMapping("/work/{workId}")
    public List<Attendance> getByWork(@PathVariable Long workId) {
        return attendanceService.getAttendanceByWork(workId);
    }
}
