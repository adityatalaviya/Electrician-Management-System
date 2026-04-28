package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    Attendance markAttendance(Long workerId,
                              Long workId,
                              LocalDate date,
                              String status,String session);

    List<Attendance> getAttendanceByWorker(Long workerId);

    List<Attendance> getAttendanceByWork(Long workId);
}
