package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.Admin;
import com.electrician.electrician_management.config.JWTUtil;
import com.electrician.electrician_management.config.ResponseToken;
import com.electrician.electrician_management.dto.AuthRequest;
import com.electrician.electrician_management.service.Adminservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final Adminservice adminservice;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager manager ;
    private final JWTUtil jwtUtil ;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest ) {

        manager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        String token = jwtUtil.generateToken(authRequest.getId(),authRequest.getEmail(),"ADMIN");
        return ResponseEntity.ok(new ResponseToken(token));

    }


    @PostMapping("/create")
    public Admin createAdmin(@RequestBody Admin admin){
        return adminservice.createAdmin(admin);
    }

}
