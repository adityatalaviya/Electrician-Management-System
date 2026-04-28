package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Admin;
import com.electrician.electrician_management.Repository.AdminRepository;
import com.electrician.electrician_management.service.Adminservice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements Adminservice {
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder; ;



    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }


    @Override
    public Admin createAdmin(Admin admin) {

        //encrypt the password for admin db
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        return adminRepository.save(admin);
    }
}
