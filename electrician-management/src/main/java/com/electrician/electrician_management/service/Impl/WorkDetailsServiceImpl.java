package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Electrician;
import com.electrician.electrician_management.Entity.WorkDetails;
import com.electrician.electrician_management.Repository.ElectricianRepository;
import com.electrician.electrician_management.Repository.WorkDetailsRepository;
import com.electrician.electrician_management.service.WorkDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkDetailsServiceImpl implements WorkDetailsService {
    private final WorkDetailsRepository workRepository;
    private final ElectricianRepository electricianRepository;

    public WorkDetailsServiceImpl(WorkDetailsRepository workRepository,
                                  ElectricianRepository electricianRepository) {
        this.workRepository = workRepository;
        this.electricianRepository = electricianRepository;
    }

    @Override
    public WorkDetails addWork(WorkDetails workDetails, Long electricianId) {

        Electrician electrician = electricianRepository.findById(electricianId)
                .orElseThrow(() -> new RuntimeException("Electrician not found"));

        workDetails.setElectrician(electrician);
        workDetails.setStatus("ONGOING");

        return workRepository.save(workDetails);
    }

    @Override
    public List<WorkDetails> getWorksByElectrician(Long electricianId) {
        return workRepository.findByElectricianId(electricianId);
    }

    @Override
    public WorkDetails markWorkCompleted(Long workId) {
        WorkDetails work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        work.setStatus("COMPLETED");
        return workRepository.save(work);
    }
}
