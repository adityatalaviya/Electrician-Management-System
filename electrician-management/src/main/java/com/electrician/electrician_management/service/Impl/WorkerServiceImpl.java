package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Electrician;
import com.electrician.electrician_management.Entity.Worker;
import com.electrician.electrician_management.Repository.ElectricianRepository;
import com.electrician.electrician_management.Repository.WorkerRepository;
import com.electrician.electrician_management.service.WorkerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final ElectricianRepository electricianRepository;

    public WorkerServiceImpl(WorkerRepository workerRepository, ElectricianRepository electricianRepository) {
        this.workerRepository = workerRepository;
        this.electricianRepository = electricianRepository;
    }

    @Override
    public Worker getWorkerByPhone(String phone) {
        return workerRepository.findByPhone(phone)
                .orElse(null);
    }


    @Override
    public Worker addWorker(Worker worker, Long electricianId) {

        Electrician electrician = electricianRepository.findById(electricianId)
                .orElseThrow(() -> new RuntimeException("Electrician not found"));

        worker.setElectrician(electrician);
        return workerRepository.save(worker);
    }

    @Override
    public List<Worker> getWorkersByElectrician(Long electricianId) {
        return workerRepository.findByElectricianId(electricianId);
    }

    @Override
    public Worker getWorkerById(Long workerId) {
        return workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
    }

    @Override
    public Worker updateWorker(Long workerId, Worker worker) {
        Worker existing = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        existing.setName(worker.getName());
        existing.setPhone(worker.getPhone());
        existing.setDailyWage(worker.getDailyWage());

        return workerRepository.save(existing);
    }

    @Override
    public void deleteWorker(Long workerId) {
        workerRepository.deleteById(workerId);
    }


}
