package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.Worker;
import com.electrician.electrician_management.service.WorkerService;
import com.electrician.electrician_management.config.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/worker")
public class WorkerAuthController {

    private final WorkerService workerService;
    private final JWTUtil jwtUtil;

    private String otp;
    private String tempPhone;

    public WorkerAuthController(WorkerService workerService,
                                JWTUtil jwtUtil) {
        this.workerService = workerService;
        this.jwtUtil = jwtUtil;
    }

    // 1️⃣ REQUEST OTP
    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestParam String phone) {

        Worker worker = workerService.getWorkerByPhone(phone);

        // ❌ if worker not found
        if (worker == null) {
            return ResponseEntity.badRequest()
                    .body("You are not registered under any electrician.");
        }

        // ✅ generate OTP ONLY if worker exists
        otp = String.valueOf(new Random().nextInt(9000) + 1000);
        tempPhone = phone;

        System.out.println("Worker OTP: " + otp);

        return ResponseEntity.ok("OTP Sent");
    }

    // 2️⃣ VERIFY OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String phone,
                            @RequestParam String enteredOtp) {

        if (!phone.equals(tempPhone) || !enteredOtp.equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        Worker worker = workerService.getWorkerByPhone(phone);

        return jwtUtil.generateToken(
                worker.getId(),
                worker.getPhone(),
                "WORKER"
        );
    }

    @GetMapping("/profile")
    public Worker getProfile(Authentication auth) {

        Long workerId = (Long) auth.getPrincipal();

        return workerService.getWorkerById(workerId);
    }
}