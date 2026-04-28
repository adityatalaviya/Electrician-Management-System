package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.WorkDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkDetailsRepository extends JpaRepository<WorkDetails, Long> {
    List<WorkDetails> findByElectricianId(Long electricianId);
}
