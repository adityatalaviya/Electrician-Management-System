package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.WorkDetails;

import java.util.List;

public interface WorkDetailsService {

    WorkDetails addWork(WorkDetails workDetails, Long electricianId);
    List<WorkDetails> getWorksByElectrician(Long electricianId);
    WorkDetails markWorkCompleted(Long workId);

}
