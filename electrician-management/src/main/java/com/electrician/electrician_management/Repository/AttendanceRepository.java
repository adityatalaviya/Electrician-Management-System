package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.Attendance;
import com.electrician.electrician_management.Entity.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByWorkerId(Long workerId);

    List<Attendance> findByWorkDetailsId(Long workId);

    boolean existsByWorkerIdAndAttendanceDateAndSession(
            Long workerId,
            LocalDate attendanceDate,
            AttendanceSession session
    );

}
