package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    List<Worker> findByElectricianId(Long electricianId);

    Optional<Worker> findByPhone(String phone);

}
