package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.Worker;

import java.util.List;

public interface WorkerService {

    Worker getWorkerByPhone(String phone);

    Worker addWorker(Worker worker,Long electricianId);

    Worker getWorkerById(Long workerId);

    List<Worker> getWorkersByElectrician(Long electricianId);

    Worker updateWorker(Long workerId ,Worker worker);
    void deleteWorker(Long workerId);
}
