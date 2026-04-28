package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.ElectricianOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectricianOtpRepo extends JpaRepository<ElectricianOtp,Long> {

    Optional<ElectricianOtp> findByPhone(String phone);
}
