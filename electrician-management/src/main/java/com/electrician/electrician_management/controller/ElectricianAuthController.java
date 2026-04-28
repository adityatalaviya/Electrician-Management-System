package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.Electrician;
import com.electrician.electrician_management.config.JWTUtil;
import com.electrician.electrician_management.service.ElectricianService;
import com.electrician.electrician_management.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/electrician")
@RequiredArgsConstructor
public class ElectricianAuthController {

    private final ElectricianService electricianService;
    private final JWTUtil jwtUtil;
    private final OtpService otpService;

    // STEP 1
    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestParam String phone) {

        electricianService.loginByPhone(phone);

        otpService.generateOtp(phone);

        return ResponseEntity.ok("OTP Sent");
    }

    // STEP 2
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestParam String phone,
            @RequestParam String otp) {

        boolean valid = otpService.verifyOtp(phone, otp);

        if (!valid) {
            return ResponseEntity.badRequest()
                    .body("Invalid OTP");
        }

        Electrician electrician =
                electricianService.loginByPhone(phone);

        String token =
                jwtUtil.generateToken(
                        electrician.getId(),
                        electrician.getPhone(),
                        "ELECTRICIAN"
                );

        return ResponseEntity.ok(token);
    }


    @GetMapping("/profile")
    public Electrician getProfile() {

        String phone =
                (String) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return electricianService.loginByPhone(phone);
    }
}