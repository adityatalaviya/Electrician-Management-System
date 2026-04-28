package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.Electrician;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElectricianRepository extends JpaRepository<Electrician,Long> {

    Optional<Electrician> findByPhone(String phone);
    List<Electrician> findByStatus(String status);  //active / expired
}
