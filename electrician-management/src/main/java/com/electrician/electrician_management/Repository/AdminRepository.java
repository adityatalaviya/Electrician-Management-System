package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository <Admin , Long>{
    Optional<Admin> findByEmail(String email);

}
