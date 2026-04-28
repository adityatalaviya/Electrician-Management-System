package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Admin;
import com.electrician.electrician_management.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomeUserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository ;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Admin> byEmail = adminRepository.findByEmail(email);
        if (byEmail.isPresent()){
            Admin admin = byEmail.get();
            return User.builder().username(admin.getEmail()).password(admin.getPassword()).roles("ADMIN").build();
        }
        throw new UsernameNotFoundException("Admin not found"+email);

    }
}
