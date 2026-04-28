package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Attendance;
import com.electrician.electrician_management.Entity.AttendanceSession;
import com.electrician.electrician_management.Entity.WorkDetails;
import com.electrician.electrician_management.Entity.Worker;
import com.electrician.electrician_management.Repository.AttendanceRepository;
import com.electrician.electrician_management.Repository.WorkDetailsRepository;
import com.electrician.electrician_management.Repository.WorkerRepository;
import com.electrician.electrician_management.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final WorkerRepository workerRepository;
    private final WorkDetailsRepository workRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 WorkerRepository workerRepository,
                                 WorkDetailsRepository workRepository) {
        this.attendanceRepository = attendanceRepository;
        this.workerRepository = workerRepository;
        this.workRepository = workRepository;
    }

    @Override
    public Attendance markAttendance(Long workerId,
                                     Long workId,
                                     LocalDate date,
                                     String status,
                                     String session) {

        if (session == null || session.isBlank()) {
            throw new RuntimeException("Session cannot be null");
        }

        AttendanceSession attendanceSession =
                AttendanceSession.valueOf(session.toUpperCase());

        if (attendanceRepository
                .existsByWorkerIdAndAttendanceDateAndSession(
                        workerId, date, attendanceSession)) {
            throw new RuntimeException("Attendance already marked for this session");
        }

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        WorkDetails work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        Attendance attendance = new Attendance();
        attendance.setWorker(worker);
        attendance.setWorkDetails(work);
        attendance.setAttendanceDate(date);
        attendance.setStatus(status.toUpperCase());
        attendance.setSession(attendanceSession);

        return attendanceRepository.save(attendance);
    }


    @Override
    public List<Attendance> getAttendanceByWorker(Long workerId) {
        return attendanceRepository.findByWorkerId(workerId);
    }

    @Override
    public List<Attendance> getAttendanceByWork(Long workId) {
        return attendanceRepository.findByWorkDetailsId(workId);
    }
}
